package org.cloudburstmc.protocol.bedrock.codec.v332;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import org.cloudburstmc.nbt.NBTInputStream;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtUtils;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v313.BedrockCodecHelper_v313;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class BedrockCodecHelper_v332 extends BedrockCodecHelper_v313 {

    public BedrockCodecHelper_v332(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes) {
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
            try (NBTInputStream reader = NbtUtils.createReaderLE(new ByteBufInputStream(buffer.readSlice(nbtSize)), this.encodingSettings.maxItemNBTSize())) {
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


        return ItemData.builder()
                .definition(definition)
                .damage(damage)
                .count(count)
                .tag(compoundTag)
                .canPlace(canPlace)
                .canBreak(canBreak)
                .build();
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
        int damage = item.getDamage();
        if (damage == -1) damage = Short.MAX_VALUE;
        VarInts.writeInt(buffer, (damage << 8) | (item.getCount() & 0xff));
        if (item.getTag() != null) {
            buffer.writeShortLE(-1);
            buffer.writeByte(1); // Hardcoded in current version
            this.writeTag(buffer, item.getTag());
        } else {
            buffer.writeShortLE(0);
        }

        String[] canPlace = item.getCanPlace();
        VarInts.writeInt(buffer, canPlace.length);
        for (String aCanPlace : canPlace) {
            this.writeString(buffer, aCanPlace);
        }

        String[] canBreak = item.getCanBreak();
        VarInts.writeInt(buffer, canBreak.length);
        for (String aCanBreak : canBreak) {
            this.writeString(buffer, aCanBreak);
        }
    }
}
