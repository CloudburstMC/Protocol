package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ShowCreditsPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShowCreditsSerializer_v332 implements PacketSerializer<ShowCreditsPacket> {
    public static final ShowCreditsSerializer_v332 INSTANCE = new ShowCreditsSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, ShowCreditsPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeInt(buffer, packet.getStatus().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, ShowCreditsPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setStatus(ShowCreditsPacket.Status.values()[VarInts.readInt(buffer)]);
    }
}
