package org.cloudburstmc.protocol.bedrock.codec.v594.serializer;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v527.serializer.RequestAbilitySerializer_v527;
import org.cloudburstmc.protocol.bedrock.packet.RequestAbilityPacket;

@NoArgsConstructor
public class RequestAbilitySerializer_v594 extends RequestAbilitySerializer_v527 {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, RequestAbilityPacket packet) {
        buffer.writeByte(packet.getAbility().ordinal());
        buffer.writeByte(packet.getType().ordinal());
        buffer.writeBoolean(packet.isBoolValue());
        buffer.writeFloatLE(packet.getFloatValue());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, RequestAbilityPacket packet) {
        packet.setAbility(ABILITIES[buffer.readUnsignedByte()]);
        packet.setType(TYPES[buffer.readUnsignedByte()]);
        packet.setBoolValue(buffer.readBoolean());
        packet.setFloatValue(buffer.readFloatLE());
    }
}