package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.PhotoTransferPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class PhotoTransferPacket_v291 extends PhotoTransferPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, name);
        VarInts.writeUnsignedInt(buffer, data.length);
        buffer.writeBytes(data);
        BedrockUtils.writeString(buffer, bookId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        name = BedrockUtils.readString(buffer);
        data = new byte[VarInts.readUnsignedInt(buffer)];
        buffer.readBytes(data);
        bookId = BedrockUtils.readString(buffer);
    }
}
