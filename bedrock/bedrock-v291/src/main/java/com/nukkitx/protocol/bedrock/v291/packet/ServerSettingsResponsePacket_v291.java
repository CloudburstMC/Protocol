package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ServerSettingsResponsePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class ServerSettingsResponsePacket_v291 extends ServerSettingsResponsePacket {

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
