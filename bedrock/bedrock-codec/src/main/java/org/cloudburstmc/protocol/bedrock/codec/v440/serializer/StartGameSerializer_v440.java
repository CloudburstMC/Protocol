package org.cloudburstmc.protocol.bedrock.codec.v440.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v428.serializer.StartGameSerializer_v428;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializer_v440 extends StartGameSerializer_v428 {

    public static final StartGameSerializer_v440 INSTANCE = new StartGameSerializer_v440();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeString(buffer, packet.getServerEngine());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setServerEngine(helper.readString(buffer));
    }
}
