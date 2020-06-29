package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.AttributeData;
import com.nukkitx.protocol.bedrock.packet.UpdateAttributesPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAttributesSerializer_v291 implements BedrockPacketSerializer<UpdateAttributesPacket> {
    public static final UpdateAttributesSerializer_v291 INSTANCE = new UpdateAttributesSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateAttributesPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeArray(buffer, packet.getAttributes(), this::writeAttribute);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateAttributesPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        helper.readArray(buffer, packet.getAttributes(), this::readAttribute);
    }

    public AttributeData readAttribute(ByteBuf buffer, BedrockPacketHelper helper) {
        Preconditions.checkNotNull(buffer, "buffer");

        float min = buffer.readFloatLE();
        float max = buffer.readFloatLE();
        float val = buffer.readFloatLE();
        float def = buffer.readFloatLE();
        String name = helper.readString(buffer);

        return new AttributeData(name, min, max, val, def);
    }

    public void writeAttribute(ByteBuf buffer, BedrockPacketHelper helper, AttributeData attribute) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(attribute, "attribute");

        buffer.writeFloatLE(attribute.getMinimum());
        buffer.writeFloatLE(attribute.getMaximum());
        buffer.writeFloatLE(attribute.getValue());
        buffer.writeFloatLE(attribute.getDefaultValue());
        helper.writeString(buffer, attribute.getName());
    }
}
