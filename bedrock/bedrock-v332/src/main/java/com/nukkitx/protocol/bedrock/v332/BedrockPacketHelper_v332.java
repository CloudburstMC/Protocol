package com.nukkitx.protocol.bedrock.v332;

import com.nukkitx.nbt.NBTInputStream;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.v313.BedrockPacketHelper_v313;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

import static com.nukkitx.protocol.bedrock.data.command.CommandParamType.*;
import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v332 extends BedrockPacketHelper_v313 {

    public static final BedrockPacketHelper INSTANCE = new BedrockPacketHelper_v332();

    @Override
    protected void registerCommandParams() {
        this.addCommandParam(1, INT);
        this.addCommandParam(2, FLOAT);
        this.addCommandParam(3, VALUE);
        this.addCommandParam(4, WILDCARD_INT);
        this.addCommandParam(5, OPERATOR);
        this.addCommandParam(6, TARGET);
        this.addCommandParam(7, WILDCARD_TARGET);
        this.addCommandParam(15, FILE_PATH);
        this.addCommandParam(19, INT_RANGE);
        this.addCommandParam(28, STRING);
        this.addCommandParam(30, POSITION);
        this.addCommandParam(33, MESSAGE);
        this.addCommandParam(35, TEXT);
        this.addCommandParam(38, JSON);
        this.addCommandParam(45, COMMAND);
    }

    @Override
    protected void registerEntityData() {
        super.registerEntityData();

        this.addEntityData(94, EntityData.AREA_EFFECT_CLOUD_DURATION);
        this.addEntityData(95, EntityData.AREA_EFFECT_CLOUD_SPAWN_TIME);
        this.addEntityData(96, EntityData.AREA_EFFECT_CLOUD_CHANGE_RATE);
        this.addEntityData(97, EntityData.AREA_EFFECT_CLOUD_CHANGE_ON_PICKUP);
        this.addEntityData(98, EntityData.AREA_EFFECT_CLOUD_COUNT);
    }

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        int block = 3500;
        this.addLevelEvent(12 + block, LevelEventType.CAULDRON_FILL_LAVA);
        this.addLevelEvent(13 + block, LevelEventType.CAULDRON_TAKE_LAVA);
    }

    @Override
    public ItemData readItem(ByteBuf buffer, BedrockSession session) {
        int id = VarInts.readInt(buffer);
        if (id == 0) {
            // We don't need to read anything extra.
            return ItemData.AIR;
        }
        int aux = VarInts.readInt(buffer);
        short damage = (short) (aux >> 8);
        if (damage == Short.MAX_VALUE) damage = -1;
        int count = aux & 0xff;
        int nbtSize = buffer.readShortLE();
        NbtMap compoundTag = null;
        if (nbtSize > 0) {
            try (NBTInputStream reader = NbtUtils.createReaderLE(new ByteBufInputStream(buffer.readSlice(nbtSize)))) {
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


        return ItemData.of(id, damage, count, compoundTag, canPlace, canBreak);
    }

    @Override
    public void writeItem(ByteBuf buffer, ItemData item, BedrockSession session) {
        requireNonNull(item, "item is null!");

        // Write id
        int id = item.getId();
        if (id == 0) {
            // We don't need to write anything extra.
            buffer.writeByte(0);
            return;
        }
        VarInts.writeInt(buffer, id);
        // Write damage and count
        short damage = item.getDamage();
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
