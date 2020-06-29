package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.RiderJumpPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RiderJumpSerializer_v291 implements BedrockPacketSerializer<RiderJumpPacket> {
    public static final RiderJumpSerializer_v291 INSTANCE = new RiderJumpSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RiderJumpPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getJumpStrength());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RiderJumpPacket packet) {
        packet.setJumpStrength(VarInts.readInt(buffer));
    }
}
