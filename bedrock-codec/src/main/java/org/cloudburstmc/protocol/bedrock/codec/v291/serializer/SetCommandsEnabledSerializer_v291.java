package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.SetCommandsEnabledPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetCommandsEnabledSerializer_v291 implements BedrockPacketSerializer<SetCommandsEnabledPacket> {
    public static final SetCommandsEnabledSerializer_v291 INSTANCE = new SetCommandsEnabledSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetCommandsEnabledPacket packet) {
        buffer.writeBoolean(packet.isCommandsEnabled());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetCommandsEnabledPacket packet) {
        packet.setCommandsEnabled(buffer.readBoolean());
    }
}
