package com.nukkitx.protocol.bedrock.compat.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;

public class LoginPacketCompat extends LoginPacket {
    private boolean v1_2 = false;

    private static AsciiString readLEAsciiString(ByteBuf buffer) {
        int length = buffer.readIntLE();
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        return new AsciiString(bytes);
    }

    @Override
    public void encode(ByteBuf buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void decode(ByteBuf buffer) {
        int index = buffer.readerIndex();
        int protocol = buffer.readInt();
        if (protocol == 0) {
            v1_2 = true;
            buffer.readerIndex(index + 2);
            protocol = buffer.readInt();
        }
        protocolVersion = protocol;

        ByteBuf jwt = buffer.readSlice(VarInts.readUnsignedInt(buffer)); // Get the JWT.
        chainData = readLEAsciiString(jwt);
        skinData = readLEAsciiString(jwt);
    }
}
