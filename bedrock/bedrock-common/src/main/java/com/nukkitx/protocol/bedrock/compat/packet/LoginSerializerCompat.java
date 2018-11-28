package com.nukkitx.protocol.bedrock.compat.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginSerializerCompat implements PacketSerializer<LoginPacket> {
    public static final LoginSerializerCompat INSTANCE = new LoginSerializerCompat();

    private static AsciiString readLEAsciiString(ByteBuf buffer) {
        int length = buffer.readIntLE();
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        return new AsciiString(bytes);
    }

    @Override
    public void serialize(ByteBuf buffer, LoginPacket packet) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deserialize(ByteBuf buffer, LoginPacket packet) {
        int index = buffer.readerIndex();
        int protocol = buffer.readInt();
        if (protocol == 0) {
            buffer.readerIndex(index + 2);
            protocol = buffer.readInt();
        }
        packet.setProtocolVersion(protocol);

        ByteBuf jwt = buffer.readSlice(VarInts.readUnsignedInt(buffer)); // Get the JWT.
        packet.setChainData(readLEAsciiString(jwt));
        packet.setSkinData(readLEAsciiString(jwt));
    }
}
