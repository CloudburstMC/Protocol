package com.nukkitx.protocol.bedrock.v340;

import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.tag.CompoundTag;
import com.nukkitx.nbt.tag.Tag;
import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.structure.StructureSettings;
import com.nukkitx.protocol.bedrock.v332.BedrockPacketHelper_v332;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

import static com.nukkitx.protocol.bedrock.data.command.CommandParamType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v340 extends BedrockPacketHelper_v332 {

    public static final BedrockPacketHelper INSTANCE = new BedrockPacketHelper_v340();

    @Override
    protected void registerCommandParams() {
        this.addCommandParam(1, INT);
        this.addCommandParam(2, FLOAT);
        this.addCommandParam(3, VALUE);
        this.addCommandParam(4, WILDCARD_INT);
        this.addCommandParam(5, OPERATOR);
        this.addCommandParam(6, TARGET);
        this.addCommandParam(7, WILDCARD_TARGET);
        this.addCommandParam(14, FILE_PATH);
        this.addCommandParam(18, INT_RANGE);
        this.addCommandParam(27, STRING);
        this.addCommandParam(29, POSITION);
        this.addCommandParam(32, MESSAGE);
        this.addCommandParam(34, TEXT);
        this.addCommandParam(37, JSON);
        this.addCommandParam(44, COMMAND);
    }

    @Override
    protected void registerEntityData() {
        super.registerEntityData();

        this.addEntityData(39, EntityData.HAS_NPC_COMPONENT);

        this.addEntityData(99, EntityData.INTERACTIVE_TAG);
        this.addEntityData(100, EntityData.TRADE_TIER);
        this.addEntityData(101, EntityData.MAX_TRADE_TIER);
    }

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();

        this.addEntityFlag(71, EntityFlag.BLOCKING);
        this.addEntityFlag(72, EntityFlag.TRANSITION_BLOCKING);
        this.addEntityFlag(73, EntityFlag.BLOCKED_USING_SHIELD);
        this.addEntityFlag(74, EntityFlag.SLEEPING);
        this.addEntityFlag(75, EntityFlag.WANTS_TO_WAKE);
        this.addEntityFlag(76, EntityFlag.TRADE_INTEREST);
        this.addEntityFlag(77, EntityFlag.DOOR_BREAKER);
        this.addEntityFlag(78, EntityFlag.BREAKING_OBSTRUCTION);
        this.addEntityFlag(79, EntityFlag.DOOR_OPENER);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(255, SoundEvent.SHIELD_BLOCK);
        this.addSoundEvent(256, SoundEvent.LECTERN_BOOK_PLACE);
        this.addSoundEvent(257, SoundEvent.UNDEFINED);
    }

    @Override
    public ItemData readItem(ByteBuf buffer) {
        Preconditions.checkNotNull(buffer, "buffer");
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
        CompoundTag compoundTag = null;
        if (nbtSize > 0) {
            try (NBTInputStream reader = NbtUtils.createReaderLE(new ByteBufInputStream(buffer.readSlice(nbtSize)))) {
                Tag<?> tag = reader.readTag();
                if (tag instanceof CompoundTag) {
                    compoundTag = (CompoundTag) tag;
                }
            } catch (IOException e) {
                throw new IllegalStateException("Unable to load NBT data", e);
            }
        } else if (nbtSize == -1) {
            try (NBTInputStream reader = NbtUtils.createNetworkReader(new ByteBufInputStream(buffer))) {
                int nbtTagCount = VarInts.readUnsignedInt(buffer);
                if (nbtTagCount == 1) {
                    Tag<?> tag = reader.readTag();
                    if (tag instanceof CompoundTag) {
                        compoundTag = (CompoundTag) tag;
                    }
                } else {
                    throw new IllegalArgumentException("Expected 1 tag but got " + nbtTagCount);
                }
            } catch (IOException e) {
                throw new IllegalStateException("Unable to load NBT data", e);
            }
        }
        String[] canPlace = readArray(buffer, new String[0], this::readString);
        String[] canBreak = readArray(buffer, new String[0], this::readString);

        long blockingTicks = 0;
        if (id == 513) { // We shouldn't be hardcoding this but it's what Microjang have made us do
            blockingTicks = VarInts.readLong(buffer);
        }
        return ItemData.of(id, damage, count, compoundTag, canPlace, canBreak, blockingTicks);
    }

    @Override
    public void writeItem(ByteBuf buffer, ItemData item) {
        super.writeItem(buffer, item);

        if (item.getId() == 513) { // TODO: 20/03/2019 We shouldn't be hardcoding this but it's what Microjang have made us do
            VarInts.writeLong(buffer, item.getBlockingTicks());
        }
    }

    @Override
    public StructureSettings readStructureSettings(ByteBuf buffer) {
        return super.readStructureSettings(buffer);
    }
}
