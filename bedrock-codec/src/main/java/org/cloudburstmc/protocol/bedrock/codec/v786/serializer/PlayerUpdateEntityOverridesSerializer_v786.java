package org.cloudburstmc.protocol.bedrock.codec.v786.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.PlayerUpdateEntityOverridesPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class PlayerUpdateEntityOverridesSerializer_v786 implements BedrockPacketSerializer<PlayerUpdateEntityOverridesPacket> {
    public static final PlayerUpdateEntityOverridesSerializer_v786 INSTANCE = new PlayerUpdateEntityOverridesSerializer_v786();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerUpdateEntityOverridesPacket packet) {
        VarInts.writeLong(buffer, packet.getTargetID());
        VarInts.writeUnsignedInt(buffer, packet.getPropertyIndex());
        buffer.writeByte(packet.getUpdateType().ordinal());
        if (packet.getUpdateType().equals(PlayerUpdateEntityOverridesPacket.UpdateType.SET_INT_OVERRIDE)) {
            buffer.writeIntLE(packet.getIntValue());
        } else if (packet.getUpdateType().equals(PlayerUpdateEntityOverridesPacket.UpdateType.SET_FLOAT_OVERRIDE)) {
            buffer.writeFloatLE(packet.getFloatValue());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerUpdateEntityOverridesPacket packet) {
        packet.setTargetID(VarInts.readLong(buffer));
        packet.setPropertyIndex(VarInts.readUnsignedInt(buffer));
        packet.setUpdateType(PlayerUpdateEntityOverridesPacket.UpdateType.values()[buffer.readUnsignedByte()]);
        if (packet.getUpdateType().equals(PlayerUpdateEntityOverridesPacket.UpdateType.SET_INT_OVERRIDE)) {
            packet.setIntValue(buffer.readIntLE());
        } else if (packet.getUpdateType().equals(PlayerUpdateEntityOverridesPacket.UpdateType.SET_FLOAT_OVERRIDE)) {
            packet.setFloatValue(buffer.readFloatLE());
        }
    }
}