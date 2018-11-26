package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.UpdateSoftEnumPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class UpdateSoftEnumPacket_v291 extends UpdateSoftEnumPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, enumName);
        BedrockUtils.writeArray(buffer, values, BedrockUtils::writeString);
        buffer.writeByte(type.ordinal());
    }

    @Override
    public void decode(ByteBuf buffer) {
        enumName = BedrockUtils.readString(buffer);
        BedrockUtils.readArray(buffer, values, BedrockUtils::readString);
        type = Type.values()[buffer.readUnsignedByte()];
    }
}
