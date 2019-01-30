package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.RiderJumpPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RiderJumpSerializer_v332 implements PacketSerializer<RiderJumpPacket> {
    public static final RiderJumpSerializer_v332 INSTANCE = new RiderJumpSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, RiderJumpPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getJumpStrength());
    }

    @Override
    public void deserialize(ByteBuf buffer, RiderJumpPacket packet) {
        packet.setJumpStrength(VarInts.readInt(buffer));
    }
}
