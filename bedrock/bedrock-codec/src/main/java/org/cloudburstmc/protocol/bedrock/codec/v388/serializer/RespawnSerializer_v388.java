package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.RespawnPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RespawnSerializer_v388 implements BedrockPacketSerializer<RespawnPacket> {
    public static final RespawnSerializer_v388 INSTANCE = new RespawnSerializer_v388();

    private static final RespawnPacket.State[] VALUES = RespawnPacket.State.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, RespawnPacket packet) {
        helper.writeVector3f(buffer, packet.getPosition());
        buffer.writeByte(packet.getState().ordinal());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, RespawnPacket packet) {
        packet.setPosition(helper.readVector3f(buffer));
        packet.setState(VALUES[buffer.readUnsignedByte()]);
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
    }
}
