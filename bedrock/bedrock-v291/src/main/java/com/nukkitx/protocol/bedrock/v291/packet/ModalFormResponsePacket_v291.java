package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ModalFormResponsePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class ModalFormResponsePacket_v291 extends ModalFormResponsePacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedInt(buffer, formId);
        BedrockUtils.writeString(buffer, formData);
    }

    @Override
    public void decode(ByteBuf buffer) {
        formId = VarInts.readUnsignedInt(buffer);
        formData = BedrockUtils.readString(buffer);
    }
}
