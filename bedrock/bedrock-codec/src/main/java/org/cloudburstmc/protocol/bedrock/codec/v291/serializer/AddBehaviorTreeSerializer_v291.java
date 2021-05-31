package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.AddBehaviorTreePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddBehaviorTreeSerializer_v291 implements BedrockPacketSerializer<AddBehaviorTreePacket> {
    public static final AddBehaviorTreeSerializer_v291 INSTANCE = new AddBehaviorTreeSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AddBehaviorTreePacket packet) {
        helper.writeString(buffer, packet.getBehaviorTreeJson());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AddBehaviorTreePacket packet) {
        packet.setBehaviorTreeJson(helper.readString(buffer));
    }
}
