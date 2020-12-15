package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ShowProfilePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShowProfileSerializer_v291 implements BedrockPacketSerializer<ShowProfilePacket> {
    public static final ShowProfileSerializer_v291 INSTANCE = new ShowProfileSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ShowProfilePacket packet) {
        helper.writeString(buffer, packet.getXuid());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ShowProfilePacket packet) {
        packet.setXuid(helper.readString(buffer));
    }
}
