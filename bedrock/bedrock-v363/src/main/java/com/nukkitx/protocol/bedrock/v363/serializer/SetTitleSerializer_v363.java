package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetTitlePacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetTitleSerializer_v363 implements PacketSerializer<SetTitlePacket> {
    public static final SetTitleSerializer_v363 INSTANCE = new SetTitleSerializer_v363();


    @Override
    public void serialize(ByteBuf buffer, SetTitlePacket packet) {
        VarInts.writeInt(buffer, packet.getType().ordinal());
        BedrockUtils.writeString(buffer, packet.getText());
        VarInts.writeInt(buffer, packet.getFadeInTime());
        VarInts.writeInt(buffer, packet.getStayTime());
        VarInts.writeInt(buffer, packet.getFadeOutTime());
    }

    @Override
    public void deserialize(ByteBuf buffer, SetTitlePacket packet) {
        packet.setType(SetTitlePacket.Type.values()[VarInts.readInt(buffer)]);
        packet.setText(BedrockUtils.readString(buffer));
        packet.setFadeInTime(VarInts.readInt(buffer));
        packet.setStayTime(VarInts.readInt(buffer));
        packet.setFadeOutTime(VarInts.readInt(buffer));
    }
}
