package org.cloudburstmc.protocol.bedrock.codec.v748.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.SetMovementAuthorityPacket;

public class SetMovementAuthoritySerializer_v748 implements BedrockPacketSerializer<SetMovementAuthorityPacket> {
    public static final SetMovementAuthoritySerializer_v748 INSTANCE = new SetMovementAuthoritySerializer_v748();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetMovementAuthorityPacket packet) {
        buffer.writeByte(packet.getNewAuthMovementMode().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetMovementAuthorityPacket packet) {
        packet.setNewAuthMovementMode(SetMovementAuthorityPacket.Mode.values()[buffer.readUnsignedByte()]);
    }
}