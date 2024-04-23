package org.cloudburstmc.protocol.bedrock.codec.v340;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import org.cloudburstmc.nbt.NBTInputStream;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtUtils;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v332.BedrockCodecHelper_v332;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.packet.InventoryTransactionPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.io.IOException;

public class BedrockCodecHelper_v340 extends BedrockCodecHelper_v332 {

    protected static final String BLOCKING_ID = "minecraft:shield";

    public BedrockCodecHelper_v340(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes) {
        super(entityData, gameRulesTypes);
    }

    @Override
    public ItemData readItem(ByteBuf buffer) {
        int runtimeId = VarInts.readInt(buffer);
        if (runtimeId == 0) {
            // We don't need to read anything extra.
            return ItemData.AIR;
        }
        ItemDefinition definition = this.itemDefinitions.getDefinition(runtimeId);
        int aux = VarInts.readInt(buffer);
        int damage = (short) (aux >> 8);
        if (damage == Short.MAX_VALUE) damage = -1;
        int count = aux & 0xff;
        int nbtSize = buffer.readShortLE();
        NbtMap compoundTag = null;
        if (nbtSize > 0) {
            try (NBTInputStream reader = NbtUtils.createReaderLE(new ByteBufInputStream(buffer.readSlice(nbtSize), this.encodingSettings.maxItemNBTSize()))) {
                compoundTag = (NbtMap) reader.readTag();
            } catch (IOException e) {
                throw new IllegalStateException("Unable to load NBT data", e);
            }
        } else if (nbtSize == -1) {
            int tagCount = buffer.readUnsignedByte();
            if (tagCount != 1) throw new IllegalArgumentException("Expected 1 tag but got " + tagCount);
            try (NBTInputStream reader = NbtUtils.createNetworkReader(new ByteBufInputStream(buffer))) {
                compoundTag = (NbtMap) reader.readTag();
            } catch (IOException e) {
                throw new IllegalStateException("Unable to load NBT data", e);
            }
        }

        String[] canPlace = new String[VarInts.readInt(buffer)];
        for (int i = 0; i < canPlace.length; i++) {
            canPlace[i] = this.readString(buffer);
        }

        String[] canBreak = new String[VarInts.readInt(buffer)];
        for (int i = 0; i < canBreak.length; i++) {
            canBreak[i] = this.readString(buffer);
        }

        long blockingTicks = 0;

        if (definition != null && BLOCKING_ID.equals(definition.getIdentifier())) {
            blockingTicks = VarInts.readLong(buffer);
        }
        return ItemData.builder()
                .definition(definition)
                .damage(damage)
                .count(count)
                .tag(compoundTag)
                .canPlace(canPlace)
                .canBreak(canBreak)
                .blockingTicks(blockingTicks)
                .build();
    }

    @Override
    public void writeItem(ByteBuf buffer, ItemData item) {
        super.writeItem(buffer, item);

        ItemDefinition definition = item.getDefinition();
        if (definition != null && BLOCKING_ID.equals(definition.getIdentifier())) {
            VarInts.writeLong(buffer, item.getBlockingTicks());
        }
    }

    @Override
    public void readItemUse(ByteBuf buffer, InventoryTransactionPacket packet) {
        super.readItemUse(buffer, packet);

        packet.setBlockDefinition(this.blockDefinitions.getDefinition(VarInts.readUnsignedInt(buffer)));
    }

    @Override
    public void writeItemUse(ByteBuf buffer, InventoryTransactionPacket packet) {
        super.writeItemUse(buffer, packet);

        VarInts.writeUnsignedInt(buffer, packet.getBlockDefinition().getRuntimeId());
    }
}
