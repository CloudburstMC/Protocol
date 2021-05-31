package org.cloudburstmc.protocol.bedrock.codec.v361;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v354.BedrockCodecHelper_v354;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.ResourcePackType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlags;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureMirror;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureRotation;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureSettings;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockCodecHelper_v361 extends BedrockCodecHelper_v354 {

    public static final BedrockCodecHelper INSTANCE = new BedrockCodecHelper_v361();

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

        this.addLevelEvent(23 + 2000, LevelEvent.PARTICLE_TELEPORT_TRAIL);

        int legacy = 0x4000;
        this.addLevelEvent(1 + legacy, LevelEvent.PARTICLE_BUBBLE);
        this.addLevelEvent(2 + legacy, LevelEvent.PARTICLE_BUBBLE_MANUAL);
        this.addLevelEvent(3 + legacy, LevelEvent.PARTICLE_CRITICAL);
        this.addLevelEvent(4 + legacy, LevelEvent.PARTICLE_BLOCK_FORCE_FIELD);
        this.addLevelEvent(5 + legacy, LevelEvent.PARTICLE_SMOKE);
        this.addLevelEvent(6 + legacy, LevelEvent.PARTICLE_EXPLODE);
        this.addLevelEvent(7 + legacy, LevelEvent.PARTICLE_EVAPORATION);
        this.addLevelEvent(8 + legacy, LevelEvent.PARTICLE_FLAME);
        this.addLevelEvent(9 + legacy, LevelEvent.PARTICLE_LAVA);
        this.addLevelEvent(10 + legacy, LevelEvent.PARTICLE_LARGE_SMOKE);
        this.addLevelEvent(11 + legacy, LevelEvent.PARTICLE_REDSTONE);
        this.addLevelEvent(12 + legacy, LevelEvent.PARTICLE_RISING_RED_DUST);
        this.addLevelEvent(13 + legacy, LevelEvent.PARTICLE_ITEM_BREAK);
        this.addLevelEvent(14 + legacy, LevelEvent.PARTICLE_SNOWBALL_POOF);
        this.addLevelEvent(15 + legacy, LevelEvent.PARTICLE_HUGE_EXPLODE);
        this.addLevelEvent(16 + legacy, LevelEvent.PARTICLE_HUGE_EXPLODE_SEED);
        this.addLevelEvent(17 + legacy, LevelEvent.PARTICLE_MOB_FLAME);
        this.addLevelEvent(18 + legacy, LevelEvent.PARTICLE_HEART);
        this.addLevelEvent(19 + legacy, LevelEvent.PARTICLE_TERRAIN);
        this.addLevelEvent(20 + legacy, LevelEvent.PARTICLE_TOWN_AURA);
        this.addLevelEvent(21 + legacy, LevelEvent.PARTICLE_PORTAL);
        this.addLevelEvent(22 + legacy, LevelEvent.PARTICLE_MOB_PORTAL);
        this.addLevelEvent(23 + legacy, LevelEvent.PARTICLE_SPLASH);
        this.addLevelEvent(24 + legacy, LevelEvent.PARTICLE_SPLASH_MANUAL);
        this.addLevelEvent(25 + legacy, LevelEvent.PARTICLE_WATER_WAKE);
        this.addLevelEvent(26 + legacy, LevelEvent.PARTICLE_DRIP_WATER);
        this.addLevelEvent(27 + legacy, LevelEvent.PARTICLE_DRIP_LAVA);
        this.addLevelEvent(28 + legacy, LevelEvent.PARTICLE_FALLING_DUST);
        this.addLevelEvent(29 + legacy, LevelEvent.PARTICLE_MOB_SPELL);
        this.addLevelEvent(30 + legacy, LevelEvent.PARTICLE_MOB_SPELL_AMBIENT);
        this.addLevelEvent(31 + legacy, LevelEvent.PARTICLE_MOB_SPELL_INSTANTANEOUS);
        this.addLevelEvent(32 + legacy, LevelEvent.PARTICLE_INK);
        this.addLevelEvent(33 + legacy, LevelEvent.PARTICLE_SLIME);
        this.addLevelEvent(34 + legacy, LevelEvent.PARTICLE_RAIN_SPLASH);
        this.addLevelEvent(35 + legacy, LevelEvent.PARTICLE_VILLAGER_ANGRY);
        this.addLevelEvent(36 + legacy, LevelEvent.PARTICLE_VILLAGER_HAPPY);
        this.addLevelEvent(37 + legacy, LevelEvent.PARTICLE_ENCHANTMENT_TABLE);
        this.addLevelEvent(38 + legacy, LevelEvent.PARTICLE_TRACKING_EMITTER);
        this.addLevelEvent(39 + legacy, LevelEvent.PARTICLE_NOTE);
        this.addLevelEvent(40 + legacy, LevelEvent.PARTICLE_WITCH_SPELL);
        this.addLevelEvent(41 + legacy, LevelEvent.PARTICLE_CARROT);
        this.addLevelEvent(42 + legacy, LevelEvent.PARTICLE_MOB_APPEARANCE);
        this.addLevelEvent(43 + legacy, LevelEvent.PARTICLE_END_ROD);
        this.addLevelEvent(44 + legacy, LevelEvent.PARTICLE_DRAGONS_BREATH);
        this.addLevelEvent(45 + legacy, LevelEvent.PARTICLE_SPIT);
        this.addLevelEvent(46 + legacy, LevelEvent.PARTICLE_TOTEM);
        this.addLevelEvent(47 + legacy, LevelEvent.PARTICLE_FOOD);
        this.addLevelEvent(48 + legacy, LevelEvent.PARTICLE_FIREWORKS_STARTER);
        this.addLevelEvent(49 + legacy, LevelEvent.PARTICLE_FIREWORKS_SPARK);
        this.addLevelEvent(50 + legacy, LevelEvent.PARTICLE_FIREWORKS_OVERLAY);
        this.addLevelEvent(51 + legacy, LevelEvent.PARTICLE_BALLOON_GAS);
        this.addLevelEvent(52 + legacy, LevelEvent.PARTICLE_COLORED_FLAME);
        this.addLevelEvent(53 + legacy, LevelEvent.PARTICLE_SPARKLER);
        this.addLevelEvent(54 + legacy, LevelEvent.PARTICLE_CONDUIT);
        this.addLevelEvent(55 + legacy, LevelEvent.PARTICLE_BUBBLE_COLUMN_UP);
        this.addLevelEvent(56 + legacy, LevelEvent.PARTICLE_BUBBLE_COLUMN_DOWN);
        this.addLevelEvent(57 + legacy, LevelEvent.PARTICLE_SNEEZE);
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
                case Type.BYTE:
                    object = buffer.readByte();
                    break;
                case Type.SHORT:
                    object = buffer.readShortLE();
                    break;
                case Type.INT:
                    object = VarInts.readInt(buffer);
                    break;
                case Type.FLOAT:
                    object = buffer.readFloatLE();
                    break;
                case Type.STRING:
                    object = readString(buffer);
                    break;
                case Type.NBT:
                    object = this.readTag(buffer);
                    break;
                case Type.VECTOR3I:
                    object = readVector3i(buffer);
                    break;
                case Type.FLAGS:
                    int index = entityData == EntityData.FLAGS_2 ? 1 : 0;
                    entityDataMap.getOrCreateFlags().set(VarInts.readLong(buffer), index, this.entityFlags);
                    continue;
                case Type.LONG:
                    object = VarInts.readLong(buffer);
                    break;
                case Type.VECTOR3F:
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
        Preconditions.checkNotNull(entityDataMap, "entityDataDictionary");

        VarInts.writeUnsignedInt(buffer, entityDataMap.size());

        for (Map.Entry<EntityData, Object> entry : entityDataMap.entrySet()) {
            int index = buffer.writerIndex();
            VarInts.writeUnsignedInt(buffer, this.entityData.get(entry.getKey()));
            Object object = entry.getValue();
            EntityData.Type type = EntityData.Type.from(object);
            VarInts.writeUnsignedInt(buffer, this.entityDataTypes.get(type));

            switch (type) {
                case Type.BYTE:
                    buffer.writeByte((byte) object);
                    break;
                case Type.SHORT:
                    buffer.writeShortLE((short) object);
                    break;
                case Type.INT:
                    VarInts.writeInt(buffer, (int) object);
                    break;
                case Type.FLOAT:
                    buffer.writeFloatLE((float) object);
                    break;
                case Type.STRING:
                    writeString(buffer, (String) object);
                    break;
                case Type.NBT:
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
                case Type.VECTOR3I:
                    writeVector3i(buffer, (Vector3i) object);
                    break;
                case Type.FLAGS:
                    int flagsIndex = entry.getKey() == EntityData.FLAGS_2 ? 1 : 0;
                    object = ((EntityFlags) object).get(flagsIndex, this.entityFlags);
                case Type.LONG:
                    VarInts.writeLong(buffer, (long) object);
                    break;
                case Type.VECTOR3F:
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
                rotation, mirror, integrityValue, integritySeed, Vector3f.ZERO);
    }

    @Override
    public void writeStructureSettings(ByteBuf buffer, StructureSettings settings) {
        this.writeString(buffer, settings.getPaletteName());
        buffer.writeBoolean(settings.isIgnoringBlocks());
        buffer.writeBoolean(settings.isIgnoringBlocks());
        this.writeBlockPosition(buffer, settings.getSize());
        this.writeBlockPosition(buffer, settings.getOffset());
        VarInts.writeLong(buffer, settings.getLastEditedByEntityId());
        buffer.writeByte(settings.getRotation().ordinal());
        buffer.writeByte(settings.getMirror().ordinal());
        buffer.writeFloatLE(settings.getIntegrityValue());
        buffer.writeIntLE(settings.getIntegritySeed());
    }
}
