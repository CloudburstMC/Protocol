package org.cloudburstmc.protocol.bedrock.codec;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.ExperimentData;
import org.cloudburstmc.protocol.bedrock.data.GameRuleData;
import org.cloudburstmc.protocol.bedrock.data.command.CommandEnumData;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOriginData;
import org.cloudburstmc.protocol.bedrock.data.defintions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.data.defintions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityLinkData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityProperties;
import org.cloudburstmc.protocol.bedrock.data.inventory.InventoryActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemStackRequest;
import org.cloudburstmc.protocol.bedrock.data.skin.SerializedSkin;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureSettings;
import org.cloudburstmc.protocol.bedrock.packet.InventoryTransactionPacket;
import org.cloudburstmc.protocol.common.DefinitionRegistry;
import org.cloudburstmc.protocol.common.util.TriConsumer;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.*;

public interface BedrockCodecHelper {

    void setItemDefinitions(DefinitionRegistry<ItemDefinition> registry);

    void setBlockDefinitions(DefinitionRegistry<BlockDefinition> registry);

    DefinitionRegistry<ItemDefinition> getItemDefinitions();

    DefinitionRegistry<BlockDefinition> getBlockDefinitions();

    // Array serialization (with helper)

    default <T> void readArray(ByteBuf buffer, Collection<T> array, BiFunction<ByteBuf, BedrockCodecHelper, T> function) {
        readArray(buffer, array, VarInts::readUnsignedInt, function);
    }

    <T> void readArray(ByteBuf buffer, Collection<T> array, ToLongFunction<ByteBuf> lengthReader,
                       BiFunction<ByteBuf, BedrockCodecHelper, T> function);

    default <T> void writeArray(ByteBuf buffer, Collection<T> array, TriConsumer<ByteBuf, BedrockCodecHelper, T> consumer) {
        writeArray(buffer, array, VarInts::writeUnsignedInt, consumer);
    }

    <T> void writeArray(ByteBuf buffer, Collection<T> array, ObjIntConsumer<ByteBuf> lengthWriter, TriConsumer<ByteBuf, BedrockCodecHelper, T> consumer);

    <T> T[] readArray(ByteBuf buffer, T[] array, BiFunction<ByteBuf, BedrockCodecHelper, T> function);

    <T> void writeArray(ByteBuf buffer, T[] array, TriConsumer<ByteBuf, BedrockCodecHelper, T> consumer);

    // Array serialization (without helper)

    <T> void readArray(ByteBuf buffer, Collection<T> array, Function<ByteBuf, T> function);

    <T> void writeArray(ByteBuf buffer, Collection<T> array, BiConsumer<ByteBuf, T> consumer);

    <T> T[] readArray(ByteBuf buffer, T[] array, Function<ByteBuf, T> function);

    <T> void writeArray(ByteBuf buffer, T[] array, BiConsumer<ByteBuf, T> consumer);

    // Encoding methods

    EntityLinkData readEntityLink(ByteBuf buffer);

    void writeEntityLink(ByteBuf buffer, EntityLinkData link);

    ItemData readNetItem(ByteBuf buffer);

    void writeNetItem(ByteBuf buffer, ItemData item);

    ItemData readItem(ByteBuf buffer);

    void writeItem(ByteBuf buffer, ItemData item);

    ItemData readItemInstance(ByteBuf buffer);

    void writeItemInstance(ByteBuf buffer, ItemData item);

    CommandOriginData readCommandOrigin(ByteBuf buffer);

    void writeCommandOrigin(ByteBuf buffer, CommandOriginData commandOrigin);

    GameRuleData<?> readGameRule(ByteBuf buffer);

    void writeGameRule(ByteBuf buffer, GameRuleData<?> gameRule);

    void readEntityData(ByteBuf buffer, EntityDataMap entityData);

    void writeEntityData(ByteBuf buffer, EntityDataMap entityData);

    CommandEnumData readCommandEnum(ByteBuf buffer, boolean soft);

    void writeCommandEnum(ByteBuf buffer, CommandEnumData commandEnum);

    StructureSettings readStructureSettings(ByteBuf buffer);

    void writeStructureSettings(ByteBuf buffer, StructureSettings settings);

    SerializedSkin readSkin(ByteBuf buffer);

    void writeSkin(ByteBuf buffer, SerializedSkin skin);

    byte[] readByteArray(ByteBuf buffer);

    void writeByteArray(ByteBuf buffer, byte[] bytes);

    ByteBuf readByteBuf(ByteBuf buffer);

    void writeByteBuf(ByteBuf buffer, ByteBuf toWrite);

    String readString(ByteBuf buffer);

    void writeString(ByteBuf buffer, String string);

    UUID readUuid(ByteBuf buffer);

    void writeUuid(ByteBuf buffer, UUID uuid);

    Vector3f readVector3f(ByteBuf buffer);

    void writeVector3f(ByteBuf buffer, Vector3f vector3f);

    Vector2f readVector2f(ByteBuf buffer);

    void writeVector2f(ByteBuf buffer, Vector2f vector2f);

    Vector3i readVector3i(ByteBuf buffer);

    void writeVector3i(ByteBuf buffer, Vector3i vector3i);

    float readByteAngle(ByteBuf buffer);

    void writeByteAngle(ByteBuf buffer, float angle);

    Vector3i readBlockPosition(ByteBuf buffer);

    void writeBlockPosition(ByteBuf buffer, Vector3i blockPosition);

    default Object readTag(ByteBuf buffer) {
        return readTag(buffer, Object.class);
    }

    <T> T readTag(ByteBuf buffer, Class<T> expected);

    void writeTag(ByteBuf buffer, Object tag);

    default Object readTagLE(ByteBuf buffer) {
        return readTag(buffer, Object.class);
    }

    <T> T readTagLE(ByteBuf buffer, Class<T> expected);

    void writeTagLE(ByteBuf buffer, Object tag);

    void readItemUse(ByteBuf buffer, InventoryTransactionPacket packet);

    void writeItemUse(ByteBuf buffer, InventoryTransactionPacket packet);

    boolean readInventoryActions(ByteBuf buffer, List<InventoryActionData> actions);

    void writeInventoryActions(ByteBuf buffer, List<InventoryActionData> actions, boolean hasNetworkIds);

    void readExperiments(ByteBuf buffer, List<ExperimentData> experiments);

    void writeExperiments(ByteBuf buffer, List<ExperimentData> experiments);

    ItemStackRequest readItemStackRequest(ByteBuf buffer);

    void writeItemStackRequest(ByteBuf buffer, ItemStackRequest request);

    <O> O readOptional(ByteBuf buffer, O emptyValue, Function<ByteBuf, O> function);

    <T> void writeOptional(ByteBuf buffer, Predicate<T> isPresent, T object, BiConsumer<ByteBuf, T> consumer);

    void readEntityProperties(ByteBuf buffer, EntityProperties properties);

    void writeEntityProperties(ByteBuf buffer, EntityProperties properties);
}
