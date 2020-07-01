package com.nukkitx.protocol.bedrock.v361;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.nbt.tag.CompoundTag;
import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
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
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

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
                    CompoundTag tag;
                    try (NBTInputStream reader = NbtUtils.createNetworkReader(new ByteBufInputStream(buffer))) {
                        tag = (CompoundTag) reader.readTag();
                    } catch (IOException e) {
                        throw new IllegalStateException("Error whilst decoding NBT entity data");
                    }
                    object = tag;
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

        for (Map.Entry<EntityData, Object> entry : entityDataMap.entrySet().stream()
                .sorted(Comparator.comparingInt(o -> entityData.get(o.getKey())))
                .collect(Collectors.toList())) {
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
                    CompoundTag tag;
                    if (object instanceof CompoundTag) {
                        tag = (CompoundTag) object;
                    } else {
                        ItemData item = (ItemData) object;
                        tag = item.getTag();
                        if (tag == null) {
                            tag = CompoundTag.EMPTY;
                        }
                    }
                    try (NBTOutputStream writer = NbtUtils.createNetworkWriter(new ByteBufOutputStream(buffer))) {
                        writer.write(tag);
                    } catch (IOException e) {
                        throw new IllegalStateException("Error whilst decoding NBT entity data");
                    }
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
