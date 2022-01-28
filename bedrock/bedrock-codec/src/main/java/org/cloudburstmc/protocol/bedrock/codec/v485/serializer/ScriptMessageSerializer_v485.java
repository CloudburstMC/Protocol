package org.cloudburstmc.protocol.bedrock.codec.v485.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ScriptMessagePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScriptMessageSerializer_v485 implements BedrockPacketSerializer<ScriptMessagePacket> {

    public static final ScriptMessageSerializer_v485 INSTANCE = new ScriptMessageSerializer_v485();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ScriptMessagePacket packet) {
        helper.writeString(buffer, packet.getChannel());
        helper.writeString(buffer, packet.getMessage());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ScriptMessagePacket packet) {
        packet.setChannel(helper.readString(buffer));
        packet.setMessage(helper.readString(buffer));
    }
}
