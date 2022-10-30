package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.RiderJumpPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RiderJumpSerializer_v291 implements BedrockPacketSerializer<RiderJumpPacket> {
    public static final RiderJumpSerializer_v291 INSTANCE = new RiderJumpSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, RiderJumpPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getJumpStrength());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, RiderJumpPacket packet) {
        packet.setJumpStrength(VarInts.readInt(buffer));
    }
}
