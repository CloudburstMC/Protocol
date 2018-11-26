package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.AddBehaviorTreePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;


public class AddBehaviorTreePacket_v291 extends AddBehaviorTreePacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, behaviorTreeJson);
    }

    @Override
    public void decode(ByteBuf buffer) {
        behaviorTreeJson = BedrockUtils.readString(buffer);
    }
}
