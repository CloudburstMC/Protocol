package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetTitlePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class SetTitlePacket_v291 extends SetTitlePacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeInt(buffer, type.ordinal());
        BedrockUtils.writeString(buffer, text);
        VarInts.writeInt(buffer, fadeInTime);
        VarInts.writeInt(buffer, stayTime);
        VarInts.writeInt(buffer, fadeOutTime);
    }

    @Override
    public void decode(ByteBuf buffer) {
        type = Type.values()[VarInts.readInt(buffer)];
        text = BedrockUtils.readString(buffer);
        fadeInTime = VarInts.readInt(buffer);
        stayTime = VarInts.readInt(buffer);
        fadeOutTime = VarInts.readInt(buffer);
    }
}
