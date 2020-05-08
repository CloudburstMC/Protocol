package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.protocol.bedrock.packet.AddBehaviorTreePacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddBehaviorTreeSerializer_v363 implements PacketSerializer<AddBehaviorTreePacket> {
    public static final AddBehaviorTreeSerializer_v363 INSTANCE = new AddBehaviorTreeSerializer_v363();

    @Override
    public void serialize(ByteBuf buffer, AddBehaviorTreePacket packet) {
        BedrockUtils.writeString(buffer, packet.getBehaviorTreeJson());
    }

    @Override
    public void deserialize(ByteBuf buffer, AddBehaviorTreePacket packet) {
        packet.setBehaviorTreeJson(BedrockUtils.readString(buffer));
    }
}
