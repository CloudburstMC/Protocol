package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.AddPlayerSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.AddPlayerPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddPlayerSerializer_v388 extends AddPlayerSerializer_v291 {
    public static final AddPlayerSerializer_v388 INSTANCE = new AddPlayerSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AddPlayerPacket packet) {
        super.serialize(buffer, helper, packet);

        buffer.writeIntLE(packet.getBuildPlatform());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AddPlayerPacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setBuildPlatform(buffer.readIntLE());
    }
}
