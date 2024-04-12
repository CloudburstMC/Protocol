package org.cloudburstmc.protocol.bedrock.codec.v431;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import org.cloudburstmc.nbt.NBTInputStream;
import org.cloudburstmc.nbt.NBTOutputStream;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v428.BedrockCodecHelper_v428;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.CraftResultsDeprecatedAction;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestAction;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventoryActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventorySource;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.cloudburstmc.protocol.common.util.stream.LittleEndianByteBufInputStream;
import org.cloudburstmc.protocol.common.util.stream.LittleEndianByteBufOutputStream;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class BedrockCodecHelper_v431 extends BedrockCodecHelper_v428 {

    public BedrockCodecHelper_v431(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes,
                                   TypeMap<ItemStackRequestActionType> stackRequestActionTypes, TypeMap<ContainerSlotType> containerSlotTypes) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes);
    }

    @Override
    public ItemData readItemInstance(ByteBuf buffer) {
        int runtimeId = VarInts.readInt(buffer);
        if (runtimeId == 0) {
            // We don't need to read anything extra.
            return ItemData.AIR;
        }
        ItemDefinition definition = this.itemDefinitions.getDefinition(runtimeId);
        int count = buffer.readUnsignedShortLE();
        int damage = VarInts.readUnsignedInt(buffer);
        int blockRuntimeId = VarInts.readInt(buffer);

        NbtMap compoundTag = null;
        long blockingTicks = 0;
        String[] canPlace;
        String[] canBreak;

        ByteBuf buf = buffer.readSlice(VarInts.readUnsignedInt(buffer));
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

            canPlace = new String[stream.readInt()];
            for (int i = 0; i < canPlace.length; i++) {
                canPlace[i] = stream.readUTF();
            }
            canBreak = new String[stream.readInt()];
            for (int i = 0; i < canBreak.length; i++) {
                canBreak[i] = stream.readUTF();
            }

            if (definition != null && BLOCKING_ID.equals(definition.getIdentifier())) {
                blockingTicks = stream.readLong();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read item user data", e);
        }

        if (buf.isReadable()) {
            log.info("Item user data has {} readable bytes left", buf.readableBytes());
            if (log.isDebugEnabled()) {
                log.debug("Item data:\n{}", ByteBufUtil.prettyHexDump(buf.readerIndex(0)));
            }
        }

        return ItemData.builder()
                .definition(definition)
                .damage(damage)
                .count(count)
                .tag(compoundTag)
                .canPlace(canPlace)
                .canBreak(canBreak)
                .blockingTicks(blockingTicks)
                .blockDefinition(this.blockDefinitions.getDefinition(blockRuntimeId))
                .build();
    }

    @Override
    public ItemData readItem(ByteBuf buffer) {
        int runtimeId = VarInts.readInt(buffer);
        if (runtimeId == 0) {
            // We don't need to read anything extra.
            return ItemData.AIR;
        }
        ItemDefinition definition = this.itemDefinitions.getDefinition(runtimeId);
        int count = buffer.readUnsignedShortLE();
        int damage = VarInts.readUnsignedInt(buffer);

        boolean hasNetId = buffer.readBoolean();
        int netId = 0;
        if (hasNetId) {
            netId = VarInts.readInt(buffer);
        }

        int blockRuntimeId = VarInts.readInt(buffer);

        NbtMap compoundTag = null;
        long blockingTicks = 0;
        String[] canPlace;
        String[] canBreak;

        ByteBuf buf = buffer.readSlice(VarInts.readUnsignedInt(buffer));
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

            canPlace = new String[stream.readInt()];
            for (int i = 0; i < canPlace.length; i++) {
                canPlace[i] = stream.readUTF();
            }
            canBreak = new String[stream.readInt()];
            for (int i = 0; i < canBreak.length; i++) {
                canBreak[i] = stream.readUTF();
            }

            if (definition != null && BLOCKING_ID.equals(definition.getIdentifier())) {
                blockingTicks = stream.readLong();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read item user data", e);
        }

        if (buf.isReadable()) {
            log.info("Item user data has {} readable bytes left", buf.readableBytes());
            if (log.isDebugEnabled()) {
                log.debug("Item data:\n{}", ByteBufUtil.prettyHexDump(buf.readerIndex(0)));
            }
        }

        return ItemData.builder()
                .definition(definition)
                .damage(damage)
                .count(count)
                .tag(compoundTag)
                .canPlace(canPlace)
                .canBreak(canBreak)
                .blockingTicks(blockingTicks)
                .blockDefinition(this.blockDefinitions.getDefinition(blockRuntimeId))
                .usingNetId(hasNetId)
                .netId(netId)
                .build();
    }

    @Override
    public ItemData readNetItem(ByteBuf buffer) {
        return this.readItem(buffer);
    }

    @Override
    public void writeItemInstance(ByteBuf buffer, ItemData item) {
        requireNonNull(item, "item is null!");

        // Write id
        ItemDefinition definition = item.getDefinition();
        if (isAir(definition)) {
            // We don't need to write anything extra.
            buffer.writeByte(0);
            return;
        }
        VarInts.writeInt(buffer, definition.getRuntimeId());

        // Write damage and count
        buffer.writeShortLE(item.getCount());
        VarInts.writeUnsignedInt(buffer, item.getDamage());
        VarInts.writeInt(buffer, item.getBlockDefinition() == null ? 0 : item.getBlockDefinition().getRuntimeId());

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

    @Override
    public void writeItem(ByteBuf buffer, ItemData item) {
        requireNonNull(item, "item is null!");

        // Write id
        ItemDefinition definition = item.getDefinition();
        if (isAir(definition)) {
            // We don't need to write anything extra.
            buffer.writeByte(0);
            return;
        }
        VarInts.writeInt(buffer, definition.getRuntimeId());

        // Write damage and count
        buffer.writeShortLE(item.getCount());
        VarInts.writeUnsignedInt(buffer, item.getDamage());

        buffer.writeBoolean(item.isUsingNetId());
        if (item.isUsingNetId()) {
            VarInts.writeInt(buffer, item.getNetId());
        }

        VarInts.writeInt(buffer, item.getBlockDefinition() == null ? 0 : item.getBlockDefinition().getRuntimeId());

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

    @Override
    public void writeNetItem(ByteBuf buffer, ItemData item) {
        this.writeItem(buffer, item);
    }

    @Override
    public boolean readInventoryActions(ByteBuf buffer, List<InventoryActionData> actions) {
        this.readArray(buffer, actions, (buf, helper) -> {
            InventorySource source = readSource(buf);
            int slot = VarInts.readUnsignedInt(buf);
            ItemData fromItem = helper.readItem(buf);
            ItemData toItem = helper.readItem(buf);

            return new InventoryActionData(source, slot, fromItem, toItem);
        }, 64); // 64 should be enough
        return false;
    }

    @Override
    public void writeInventoryActions(ByteBuf buffer, List<InventoryActionData> actions, boolean hasNetworkIds) {
        this.writeArray(buffer, actions, (buf, helper, action) -> {
            writeSource(buf, action.getSource());
            VarInts.writeUnsignedInt(buf, action.getSlot());
            helper.writeItem(buf, action.getFromItem());
            helper.writeItem(buf, action.getToItem());
        });
    }

    @Override
    protected ItemStackRequestAction readRequestActionData(ByteBuf byteBuf, ItemStackRequestActionType type) {
        if (type == ItemStackRequestActionType.CRAFT_RESULTS_DEPRECATED) {
            return new CraftResultsDeprecatedAction(
                    this.readArray(byteBuf, new ItemData[0], this::readItemInstance),
                    byteBuf.readUnsignedByte()
            );
        } else {
            return super.readRequestActionData(byteBuf, type);
        }
    }

    @Override
    protected void writeRequestActionData(ByteBuf byteBuf, ItemStackRequestAction action) {
        if (action.getType() == ItemStackRequestActionType.CRAFT_RESULTS_DEPRECATED) {
            this.writeArray(byteBuf, ((CraftResultsDeprecatedAction) action).getResultItems(), this::writeItemInstance);
            byteBuf.writeByte(((CraftResultsDeprecatedAction) action).getTimesCrafted());
        } else {
            super.writeRequestActionData(byteBuf, action);
        }
    }
}
