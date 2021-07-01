package com.nukkitx.protocol.bedrock.v448.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.NpcRequestPacket;
import com.nukkitx.protocol.bedrock.v291.serializer.NpcRequestSerializer_v291;
import io.netty.buffer.ByteBuf;

public class NpcRequestSerializer_v448 extends NpcRequestSerializer_v291 {

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NpcRequestPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeString(buffer, packet.getSceneName());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NpcRequestPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setSceneName(helper.readString(buffer));
    }
}
