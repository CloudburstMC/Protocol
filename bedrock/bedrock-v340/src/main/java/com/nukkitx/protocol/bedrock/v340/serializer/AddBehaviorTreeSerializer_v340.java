package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.protocol.bedrock.packet.AddBehaviorTreePacket;
import com.nukkitx.protocol.bedrock.v340.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddBehaviorTreeSerializer_v340 implements PacketSerializer<AddBehaviorTreePacket> {
    public static final AddBehaviorTreeSerializer_v340 INSTANCE = new AddBehaviorTreeSerializer_v340();

    @Override
    public void serialize(ByteBuf buffer, AddBehaviorTreePacket packet) {
        BedrockUtils.writeString(buffer, packet.getBehaviorTreeJson());
    }

    @Override
    public void deserialize(ByteBuf buffer, AddBehaviorTreePacket packet) {
        packet.setBehaviorTreeJson(BedrockUtils.readString(buffer));
    }
}
