package com.nukkitx.protocol.bedrock.v361;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.ResourcePackType;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityDataMap;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlags;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.structure.StructureMirror;
import com.nukkitx.protocol.bedrock.data.structure.StructureRotation;
import com.nukkitx.protocol.bedrock.data.structure.StructureSettings;
import com.nukkitx.protocol.bedrock.v354.BedrockPacketHelper_v354;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v361 extends BedrockPacketHelper_v354 {

    public static final BedrockPacketHelper INSTANCE = new BedrockPacketHelper_v361();

    @Override
    protected void registerEntityData() {
        super.registerEntityData();

        this.addEntityData(40, EntityData.NPC_DATA);

        this.addEntityData(103, EntityData.SKIN_ID);
        this.addEntityData(104, EntityData.SPAWNING_FRAMES);
        this.addEntityData(105, EntityData.COMMAND_BLOCK_TICK_DELAY);
        this.addEntityData(106, EntityData.COMMAND_BLOCK_EXECUTE_ON_FIRST_TICK);
    }

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();

        this.addEntityFlag(87, EntityFlag.HIDDEN_WHEN_INVISIBLE);
    }

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        this.addLevelEvent(23 + 2000, LevelEventType.PARTICLE_TELEPORT_TRAIL);

        int legacy = 0x4000;
        this.addLevelEvent(1 + legacy, LevelEventType.PARTICLE_BUBBLE);
        this.addLevelEvent(2 + legacy, LevelEventType.PARTICLE_BUBBLE_MANUAL);
        this.addLevelEvent(3 + legacy, LevelEventType.PARTICLE_CRITICAL);
        this.addLevelEvent(4 + legacy, LevelEventType.PARTICLE_BLOCK_FORCE_FIELD);
        this.addLevelEvent(5 + legacy, LevelEventType.PARTICLE_SMOKE);
        this.addLevelEvent(6 + legacy, LevelEventType.PARTICLE_EXPLODE);
        this.addLevelEvent(7 + legacy, LevelEventType.PARTICLE_EVAPORATION);
        this.addLevelEvent(8 + legacy, LevelEventType.PARTICLE_FLAME);
        this.addLevelEvent(9 + legacy, LevelEventType.PARTICLE_LAVA);
        this.addLevelEvent(10 + legacy, LevelEventType.PARTICLE_LARGE_SMOKE);
        this.addLevelEvent(11 + legacy, LevelEventType.PARTICLE_REDSTONE);
        this.addLevelEvent(12 + legacy, LevelEventType.PARTICLE_RISING_RED_DUST);
        this.addLevelEvent(13 + legacy, LevelEventType.PARTICLE_ITEM_BREAK);
        this.addLevelEvent(14 + legacy, LevelEventType.PARTICLE_SNOWBALL_POOF);
        this.addLevelEvent(15 + legacy, LevelEventType.PARTICLE_HUGE_EXPLODE);
        this.addLevelEvent(16 + legacy, LevelEventType.PARTICLE_HUGE_EXPLODE_SEED);
        this.addLevelEvent(17 + legacy, LevelEventType.PARTICLE_MOB_FLAME);
        this.addLevelEvent(18 + legacy, LevelEventType.PARTICLE_HEART);
        this.addLevelEvent(19 + legacy, LevelEventType.PARTICLE_TERRAIN);
        this.addLevelEvent(20 + legacy, LevelEventType.PARTICLE_TOWN_AURA);
        this.addLevelEvent(21 + legacy, LevelEventType.PARTICLE_PORTAL);
        this.addLevelEvent(22 + legacy, LevelEventType.PARTICLE_MOB_PORTAL);
        this.addLevelEvent(23 + legacy, LevelEventType.PARTICLE_SPLASH);
        this.addLevelEvent(24 + legacy, LevelEventType.PARTICLE_SPLASH_MANUAL);
        this.addLevelEvent(25 + legacy, LevelEventType.PARTICLE_WATER_WAKE);
        this.addLevelEvent(26 + legacy, LevelEventType.PARTICLE_DRIP_WATER);
        this.addLevelEvent(27 + legacy, LevelEventType.PARTICLE_DRIP_LAVA);
        this.addLevelEvent(28 + legacy, LevelEventType.PARTICLE_FALLING_DUST);
        this.addLevelEvent(29 + legacy, LevelEventType.PARTICLE_MOB_SPELL);
        this.addLevelEvent(30 + legacy, LevelEventType.PARTICLE_MOB_SPELL_AMBIENT);
        this.addLevelEvent(31 + legacy, LevelEventType.PARTICLE_MOB_SPELL_INSTANTANEOUS);
        this.addLevelEvent(32 + legacy, LevelEventType.PARTICLE_INK);
        this.addLevelEvent(33 + legacy, LevelEventType.PARTICLE_SLIME);
        this.addLevelEvent(34 + legacy, LevelEventType.PARTICLE_RAIN_SPLASH);
        this.addLevelEvent(35 + legacy, LevelEventType.PARTICLE_VILLAGER_ANGRY);
        this.addLevelEvent(36 + legacy, LevelEventType.PARTICLE_VILLAGER_HAPPY);
        this.addLevelEvent(37 + legacy, LevelEventType.PARTICLE_ENCHANTMENT_TABLE);
        this.addLevelEvent(38 + legacy, LevelEventType.PARTICLE_TRACKING_EMITTER);
        this.addLevelEvent(39 + legacy, LevelEventType.PARTICLE_NOTE);
        this.addLevelEvent(40 + legacy, LevelEventType.PARTICLE_WITCH_SPELL);
        this.addLevelEvent(41 + legacy, LevelEventType.PARTICLE_CARROT);
        this.addLevelEvent(42 + legacy, LevelEventType.PARTICLE_MOB_APPEARANCE);
        this.addLevelEvent(43 + legacy, LevelEventType.PARTICLE_END_ROD);
        this.addLevelEvent(44 + legacy, LevelEventType.PARTICLE_DRAGONS_BREATH);
        this.addLevelEvent(45 + legacy, LevelEventType.PARTICLE_SPIT);
        this.addLevelEvent(46 + legacy, LevelEventType.PARTICLE_TOTEM);
        this.addLevelEvent(47 + legacy, LevelEventType.PARTICLE_FOOD);
        this.addLevelEvent(48 + legacy, LevelEventType.PARTICLE_FIREWORKS_STARTER);
        this.addLevelEvent(49 + legacy, LevelEventType.PARTICLE_FIREWORKS_SPARK);
        this.addLevelEvent(50 + legacy, LevelEventType.PARTICLE_FIREWORKS_OVERLAY);
        this.addLevelEvent(51 + legacy, LevelEventType.PARTICLE_BALLOON_GAS);
        this.addLevelEvent(52 + legacy, LevelEventType.PARTICLE_COLORED_FLAME);
        this.addLevelEvent(53 + legacy, LevelEventType.PARTICLE_SPARKLER);
        this.addLevelEvent(54 + legacy, LevelEventType.PARTICLE_CONDUIT);
        this.addLevelEvent(55 + legacy, LevelEventType.PARTICLE_BUBBLE_COLUMN_UP);
        this.addLevelEvent(56 + legacy, LevelEventType.PARTICLE_BUBBLE_COLUMN_DOWN);
        this.addLevelEvent(57 + legacy, LevelEventType.PARTICLE_SNEEZE);
    }

    @Override
    protected void registerResourcePackTypes() {
        addResourcePackType(0, ResourcePackType.INVALID);
        addResourcePackType(1, ResourcePackType.RESOURCE);
        addResourcePackType(2, ResourcePackType.BEHAVIOR);
        addResourcePackType(3, ResourcePackType.WORLD_TEMPLATE);
        addResourcePackType(4, ResourcePackType.ADDON);
        addResourcePackType(5, ResourcePackType.SKINS);
        addResourcePackType(6, ResourcePackType.CACHED);
        addResourcePackType(7, ResourcePackType.COPY_PROTECTED);
    }

    @Override
    public void readEntityData(ByteBuf buffer, EntityDataMap entityDataMap) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(entityDataMap, "entityDataDictionary");

        int length = VarInts.readUnsignedInt(buffer);

        for (int i = 0; i < length; i++) {
            int metadataInt = VarInts.readUnsignedInt(buffer);
            EntityData entityData = this.entityData.get(metadataInt);
            EntityData.Type type = this.entityDataTypes.get(VarInts.readUnsignedInt(buffer));
            if (entityData != null && entityData.isFlags()) {
                if (type != EntityData.Type.LONG) {
                    throw new IllegalArgumentException("Expected long value for flags, got " + type.name());
                }
                type = EntityData.Type.FLAGS;
            }

            Object object;
            switch (type) {
                case BYTE:
                    object = buffer.readByte();
                    break;
                case SHORT:
                    object = buffer.readShortLE();
                    break;
                case INT:
                    object = VarInts.readInt(buffer);
                    break;
                case FLOAT:
                    object = buffer.readFloatLE();
                    break;
                case STRING:
                    object = readString(buffer);
                    break;
                case NBT:
                    object = this.readTag(buffer);
                    break;
                case VECTOR3I:
                    object = readVector3i(buffer);
                    break;
                case FLAGS:
                    int index = entityData == EntityData.FLAGS_2 ? 1 : 0;
                    entityDataMap.getOrCreateFlags().set(VarInts.readLong(buffer), index, this.entityFlags);
                    continue;
                case LONG:
                    object = VarInts.readLong(buffer);
                    break;
                case VECTOR3F:
                    object = readVector3f(buffer);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown entity data type received");
            }
            if (entityData != null) {
                entityDataMap.put(entityData, object);
            } else {
                log.debug("Unknown entity data: {} type {} value {}", metadataInt, type, object);
            }
        }
    }

    @Override
    public void writeEntityData(ByteBuf buffer, EntityDataMap entityDataMap) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(entityDataMap, "entityDataDictionary");

        VarInts.writeUnsignedInt(buffer, entityDataMap.size());

        for (Map.Entry<EntityData, Object> entry : entityDataMap.entrySet()) {
            int index = buffer.writerIndex();
            VarInts.writeUnsignedInt(buffer, this.entityData.get(entry.getKey()));
            Object object = entry.getValue();
            EntityData.Type type = EntityData.Type.from(object);
            VarInts.writeUnsignedInt(buffer, this.entityDataTypes.get(type));

            switch (type) {
                case BYTE:
                    buffer.writeByte((byte) object);
                    break;
                case SHORT:
                    buffer.writeShortLE((short) object);
                    break;
                case INT:
                    VarInts.writeInt(buffer, (int) object);
                    break;
                case FLOAT:
                    buffer.writeFloatLE((float) object);
                    break;
                case STRING:
                    writeString(buffer, (String) object);
                    break;
                case NBT:
                    NbtMap tag;
                    if (object instanceof NbtMap) {
                        tag = (NbtMap) object;
                    } else {
                        ItemData item = (ItemData) object;
                        tag = item.getTag();
                        if (tag == null) {
                            tag = NbtMap.EMPTY;
                        }
                    }
                    this.writeTag(buffer, tag);
                    break;
                case VECTOR3I:
                    writeVector3i(buffer, (Vector3i) object);
                    break;
                case FLAGS:
                    int flagsIndex = entry.getKey() == EntityData.FLAGS_2 ? 1 : 0;
                    object = ((EntityFlags) object).get(flagsIndex, this.entityFlags);
                case LONG:
                    VarInts.writeLong(buffer, (long) object);
                    break;
                case VECTOR3F:
                    writeVector3f(buffer, (Vector3f) object);
                    break;
                default:
                    buffer.writerIndex(index);
                    break;
            }
        }
    }

    @Override
    public StructureSettings readStructureSettings(ByteBuf buffer) {
        String paletteName = this.readString(buffer);
        boolean ignoringEntities = buffer.readBoolean();
        boolean ignoringBlocks = buffer.readBoolean();
        Vector3i size = this.readBlockPosition(buffer);
        Vector3i offset = this.readBlockPosition(buffer);
        long lastEditedByEntityId = VarInts.readLong(buffer);
        StructureRotation rotation = StructureRotation.from(buffer.readByte());
        StructureMirror mirror = StructureMirror.from(buffer.readByte());
        float integrityValue = buffer.readFloatLE();
        int integritySeed = buffer.readIntLE();

        return new StructureSettings(paletteName, ignoringEntities, ignoringBlocks, size, offset, lastEditedByEntityId,
                rotation, mirror, integrityValue, integritySeed);
    }

    @Override
    public void writeStructureSettings(ByteBuf buffer, StructureSettings settings) {

    }
}
