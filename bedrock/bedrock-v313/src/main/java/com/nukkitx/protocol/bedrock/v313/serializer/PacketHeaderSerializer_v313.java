package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.PacketHeader;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PacketHeaderSerializer_v313 implements PacketSerializer<PacketHeader> {
    public static final PacketHeaderSerializer_v313 INSTANCE = new PacketHeaderSerializer_v313();


    @Override
    public void serialize(ByteBuf buffer, PacketHeader packet) {
        int header = 0;
        header |= (packet.getPacketId() & 0x3ff);
        header |= (packet.getSenderId() & 3) << 10;
        header |= (packet.getClientId() & 3) << 12;
        VarInts.writeUnsignedInt(buffer, header);
    }

    @Override
    public void deserialize(ByteBuf buffer, PacketHeader packet) {
        int header = VarInts.readUnsignedInt(buffer);
        packet.setPacketId(header & 0x3ff);
        packet.setSenderId((header >>> 10) & 3);
        packet.setClientId((header >>> 12) & 3);
    }
}
