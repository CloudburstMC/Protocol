package org.cloudburstmc.protocol.bedrock.codec.v428.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.StartGameSerializer_v419;
import org.cloudburstmc.protocol.bedrock.data.BlockPropertyData;
import org.cloudburstmc.protocol.bedrock.data.GameType;
import org.cloudburstmc.protocol.bedrock.data.definitions.SimpleItemDefinition;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@SuppressWarnings("DuplicatedCode")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializer_v428 extends StartGameSerializer_v419 {

    public static final StartGameSerializer_v428 INSTANCE = new StartGameSerializer_v428();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeInt(buffer, packet.getPlayerGameType().ordinal());
        helper.writeVector3f(buffer, packet.getPlayerPosition());
        helper.writeVector2f(buffer, packet.getRotation());

        this.writeLevelSettings(buffer, helper, packet);

        helper.writeString(buffer, packet.getLevelId());
        helper.writeString(buffer, packet.getLevelName());
        helper.writeString(buffer, packet.getPremiumWorldTemplateId());
        buffer.writeBoolean(packet.isTrial());
        writeSyncedPlayerMovementSettings(buffer, packet); // new for v428
        buffer.writeLongLE(packet.getCurrentTick());
        VarInts.writeInt(buffer, packet.getEnchantmentSeed());

        helper.writeArray(buffer, packet.getBlockProperties(), (buf, packetHelper, block) -> {
            packetHelper.writeString(buf, block.getName());
            packetHelper.writeTag(buf, block.getProperties());
        });

        this.writeItemDefinitions(buffer, helper, packet.getItemDefinitions());

        helper.writeString(buffer, packet.getMultiplayerCorrelationId());
        buffer.writeBoolean(packet.isInventoriesServerAuthoritative());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPlayerGameType(GameType.from(VarInts.readInt(buffer)));
        packet.setPlayerPosition(helper.readVector3f(buffer));
        packet.setRotation(helper.readVector2f(buffer));

        this.readLevelSettings(buffer, helper, packet);

        packet.setLevelId(helper.readString(buffer));
        packet.setLevelName(helper.readString(buffer));
        packet.setPremiumWorldTemplateId(helper.readString(buffer));
        packet.setTrial(buffer.readBoolean());
        readSyncedPlayerMovementSettings(buffer, packet); // new for v428
        packet.setCurrentTick(buffer.readLongLE());
        packet.setEnchantmentSeed(VarInts.readInt(buffer));

        helper.readArray(buffer, packet.getBlockProperties(), (buf, packetHelper) -> {
            String name = packetHelper.readString(buf);
            NbtMap properties = packetHelper.readTag(buf, NbtMap.class);
            return new BlockPropertyData(name, properties);
        });

        this.readItemDefinitions(buffer, helper, packet.getItemDefinitions());

        packet.setMultiplayerCorrelationId(helper.readString(buffer));
        packet.setInventoriesServerAuthoritative(buffer.readBoolean());
    }

    protected void writeSyncedPlayerMovementSettings(ByteBuf buffer, StartGamePacket packet) {
        VarInts.writeInt(buffer, packet.getAuthoritativeMovementMode().ordinal());
        VarInts.writeInt(buffer, packet.getRewindHistorySize());
        buffer.writeBoolean(packet.isServerAuthoritativeBlockBreaking());
    }

    protected void readSyncedPlayerMovementSettings(ByteBuf buffer, StartGamePacket packet) {
        packet.setAuthoritativeMovementMode(MOVEMENT_MODES[VarInts.readInt(buffer)]);
        packet.setRewindHistorySize(VarInts.readInt(buffer));
        packet.setServerAuthoritativeBlockBreaking(buffer.readBoolean());
    }

}
