package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SetTitlePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetTitleSerializer_v291 implements BedrockPacketSerializer<SetTitlePacket> {
    public static final SetTitleSerializer_v291 INSTANCE = new SetTitleSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetTitlePacket packet) {
        VarInts.writeInt(buffer, packet.getType().ordinal());
        helper.writeString(buffer, packet.getText());
        VarInts.writeInt(buffer, packet.getFadeInTime());
        VarInts.writeInt(buffer, packet.getStayTime());
        VarInts.writeInt(buffer, packet.getFadeOutTime());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetTitlePacket packet) {
        packet.setType(SetTitlePacket.Type.values()[VarInts.readInt(buffer)]);
        packet.setText(helper.readString(buffer));
        packet.setFadeInTime(VarInts.readInt(buffer));
        packet.setStayTime(VarInts.readInt(buffer));
        packet.setFadeOutTime(VarInts.readInt(buffer));
    }
}
