package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.AttributeData;
import org.cloudburstmc.protocol.bedrock.packet.UpdateAttributesPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkNotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAttributesSerializer_v291 implements BedrockPacketSerializer<UpdateAttributesPacket> {
    public static final UpdateAttributesSerializer_v291 INSTANCE = new UpdateAttributesSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateAttributesPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeArray(buffer, packet.getAttributes(), this::writeAttribute);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateAttributesPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        helper.readArray(buffer, packet.getAttributes(), this::readAttribute);
    }

    public AttributeData readAttribute(ByteBuf buffer, BedrockCodecHelper helper) {

        float min = buffer.readFloatLE();
        float max = buffer.readFloatLE();
        float val = buffer.readFloatLE();
        float def = buffer.readFloatLE();
        String name = helper.readString(buffer);

        return new AttributeData(name, min, max, val, def);
    }

    public void writeAttribute(ByteBuf buffer, BedrockCodecHelper helper, AttributeData attribute) {
        checkNotNull(attribute, "attribute");

        buffer.writeFloatLE(attribute.getMinimum());
        buffer.writeFloatLE(attribute.getMaximum());
        buffer.writeFloatLE(attribute.getValue());
        buffer.writeFloatLE(attribute.getDefaultValue());
        helper.writeString(buffer, attribute.getName());
    }
}
