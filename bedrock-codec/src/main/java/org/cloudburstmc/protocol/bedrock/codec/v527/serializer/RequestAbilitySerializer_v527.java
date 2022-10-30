package org.cloudburstmc.protocol.bedrock.codec.v527.serializer;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.packet.RequestAbilityPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor
public class RequestAbilitySerializer_v527 implements BedrockPacketSerializer<RequestAbilityPacket> {

    private static final Ability[] ABILITIES = Ability.values();
    private static final Ability.Type[] TYPES = Ability.Type.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, RequestAbilityPacket packet) {
        VarInts.writeInt(buffer, packet.getAbility().ordinal());
        buffer.writeByte(packet.getType().ordinal());
        buffer.writeBoolean(packet.isBoolValue());
        buffer.writeFloatLE(packet.getFloatValue());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, RequestAbilityPacket packet) {
        packet.setAbility(ABILITIES[VarInts.readInt(buffer)]);
        packet.setType(TYPES[buffer.readUnsignedByte()]);
        packet.setBoolValue(buffer.readBoolean());
        packet.setFloatValue(buffer.readFloatLE());
    }
}