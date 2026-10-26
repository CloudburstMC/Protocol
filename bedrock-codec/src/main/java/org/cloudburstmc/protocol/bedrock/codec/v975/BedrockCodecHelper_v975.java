package org.cloudburstmc.protocol.bedrock.codec.v975;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import org.cloudburstmc.nbt.NBTInputStream;
import org.cloudburstmc.nbt.NBTOutputStream;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v944.BedrockCodecHelper_v944;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.cloudburstmc.protocol.common.util.stream.LittleEndianByteBufInputStream;
import org.cloudburstmc.protocol.common.util.stream.LittleEndianByteBufOutputStream;

import java.io.IOException;

import static java.util.Objects.requireNonNull;
import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

public class BedrockCodecHelper_v975 extends BedrockCodecHelper_v944 {

    public BedrockCodecHelper_v975(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes, TypeMap<ItemStackRequestActionType> stackRequestActionTypes,
                                   TypeMap<ContainerSlotType> containerSlotTypes, TypeMap<Ability> abilities, TypeMap<TextProcessingEventOrigin> textProcessingEventOrigins) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes, abilities, textProcessingEventOrigins);
    }

    @Override
    public ItemData readNetworkItemStackDescriptor(ByteBuf buffer) {
        int runtimeId = buffer.readShortLE();

        ItemDefinition definition = runtimeId == 0 ? ItemDefinition.AIR : this.itemDefinitions.getDefinition(runtimeId);
        int count = buffer.readUnsignedShortLE();
        int aux = VarInts.readUnsignedInt(buffer);

        int netId = 0;
        boolean hasNetId = buffer.readBoolean();

        if (hasNetId) {
            int netIdVariant = VarInts.readUnsignedInt(buffer);

            switch (netIdVariant) {
                case 0: // ItemStackNetId
                case 1: // ItemStackRequestId
                case 2: // ItemStackLegacyRequestId
                    netId = VarInts.readInt(buffer);
                    break;
                default:
                    throw new IllegalArgumentException("Not oneOf<ItemStackNetId, ItemStackRequestId, ItemStackLegacyRequestId>");
            }
        }

        int blockRuntimeId = VarInts.readUnsignedInt(buffer);

        NbtMap compoundTag = null;
        long blockingTicks = 0;
        String[] canPlace = new String[0];
        String[] canBreak = new String[0];

        ByteBuf buf = buffer.readSlice(VarInts.readUnsignedInt(buffer));

        if (buf.isReadable()) {
            try (LittleEndianByteBufInputStream stream = new LittleEndianByteBufInputStream(buf);
                 NBTInputStream nbtStream = new NBTInputStream(stream, this.encodingSettings.maxItemNBTSize())) {
                int nbtSize = stream.readShort();

                if (nbtSize > 0) {
                    compoundTag = (NbtMap) nbtStream.readTag();
                } else if (nbtSize == -1) {
                    int tagCount = stream.readUnsignedByte();
                    if (tagCount != 1) throw new IllegalArgumentException("Expected 1 tag but got " + tagCount);
                    compoundTag = (NbtMap) nbtStream.readTag();
                }

                int maxLength = this.encodingSettings.maxListSize();
                int length = stream.readInt();
                checkArgument(maxLength <= 0 || length <= maxLength, "Tried to read %s can place entries, but maximum is %s", length, maxLength);
                canPlace = new String[length];
                for (int i = 0; i < canPlace.length; i++) {
                    canPlace[i] = stream.readUTFMaxLen(this.encodingSettings.maxItemStackTagLength());
                }

                length = stream.readInt();
                checkArgument(maxLength <= 0 || length <= maxLength, "Tried to read %s can break entries, but maximum is %s", length, maxLength);
                canBreak = new String[length];
                for (int i = 0; i < canBreak.length; i++) {
                    canBreak[i] = stream.readUTFMaxLen(this.encodingSettings.maxItemStackTagLength());
                }

                if (definition != null && BLOCKING_ID.equals(definition.getIdentifier())) {
                    blockingTicks = stream.readLong();
                }
            } catch (IOException e) {
                throw new IllegalStateException("Unable to read item user data", e);
            }
        }

        if (buf.isReadable()) {
            log.info("Item user data has {} readable bytes left", buf.readableBytes());

            if (log.isDebugEnabled()) {
                log.debug("Item data:\n{}", ByteBufUtil.prettyHexDump(buf.readerIndex(0)));
            }
        }

        return ItemData.builder()
                .definition(definition)
                .damage(aux)
                .count(count)
                .tag(compoundTag)
                .canPlace(canPlace)
                .canBreak(canBreak)
                .blockingTicks(blockingTicks)
                .blockDefinition(runtimeId == 0 ? ItemData.AIR.getBlockDefinition() : this.blockDefinitions.getDefinition(blockRuntimeId))
                .usingNetId(hasNetId)
                .netId(netId)
                .build();
    }

    @Override
    public void writeNetworkItemStackDescriptor(ByteBuf buffer, ItemData item) {
        requireNonNull(item, "item is null!");

        ItemDefinition definition = item.getDefinition();
        boolean air = isAir(definition);

        buffer.writeShortLE(air ? 0 : definition.getRuntimeId());
        buffer.writeShortLE(item.getCount());
        VarInts.writeUnsignedInt(buffer, item.getDamage());

        buffer.writeBoolean(item.isUsingNetId());
        if (item.isUsingNetId()) {
            VarInts.writeUnsignedInt(buffer, 0); // TODO: variant: oneOf<ItemStackNetId, ItemStackRequestId, ItemStackLegacyRequestId> (all read the same but is there difference in behavior?)
            VarInts.writeInt(buffer, item.getNetId());
        }

        BlockDefinition blockDefinition;
        VarInts.writeUnsignedInt(buffer, air || (blockDefinition = item.getBlockDefinition()) == null ? 0 : blockDefinition.getRuntimeId());

        if (air) {
            VarInts.writeUnsignedInt(buffer, 0);
        } else {
            ByteBuf userDataBuf = ByteBufAllocator.DEFAULT.ioBuffer();
            try (LittleEndianByteBufOutputStream stream = new LittleEndianByteBufOutputStream(userDataBuf);
                 NBTOutputStream nbtStream = new NBTOutputStream(stream)) {
                if (item.getTag() != null) {
                    stream.writeShort(-1);
                    stream.writeByte(1); // Hardcoded in current version
                    nbtStream.writeTag(item.getTag());
                } else {
                    userDataBuf.writeShortLE(0);
                }

                String[] canPlace = item.getCanPlace();
                stream.writeInt(canPlace.length);
                for (String aCanPlace : canPlace) {
                    stream.writeUTF(aCanPlace);
                }

                String[] canBreak = item.getCanBreak();
                stream.writeInt(canBreak.length);
                for (String aCanBreak : canBreak) {
                    stream.writeUTF(aCanBreak);
                }

                if (BLOCKING_ID.equals(definition.getIdentifier())) {
                    stream.writeLong(item.getBlockingTicks());
                }

                VarInts.writeUnsignedInt(buffer, userDataBuf.readableBytes());
                buffer.writeBytes(userDataBuf);
            } catch (IOException e) {
                throw new IllegalStateException("Unable to write item user data", e);
            } finally {
                userDataBuf.release();
            }
        }
    }
}
