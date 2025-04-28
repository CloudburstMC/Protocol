package org.cloudburstmc.protocol.bedrock.codec.v800.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.PlayerLocationPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerLocationSerializer_v800 implements BedrockPacketSerializer<PlayerLocationPacket> {

    public static final PlayerLocationSerializer_v800 INSTANCE = new PlayerLocationSerializer_v800();
    private static final PlayerLocationPacket.Type[] VALUES = PlayerLocationPacket.Type.values();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerLocationPacket packet) {
        buffer.writeIntLE(packet.getType().ordinal());
        VarInts.writeLong(buffer, packet.getTargetEntityId());
        if (packet.getType() == PlayerLocationPacket.Type.COORDINATES) {
            helper.writeVector3f(buffer, packet.getPosition());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerLocationPacket packet) {
        packet.setType(VALUES[buffer.readIntLE()]);
        packet.setTargetEntityId(VarInts.readLong(buffer));
        if (packet.getType() == PlayerLocationPacket.Type.COORDINATES) {
            packet.setPosition(helper.readVector3f(buffer));
        }
    }
}
