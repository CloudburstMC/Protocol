package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.AttributeData;
import org.cloudburstmc.protocol.bedrock.packet.AddEntityPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddEntitySerializer_v291 implements BedrockPacketSerializer<AddEntityPacket> {
    public static final AddEntitySerializer_v291 INSTANCE = new AddEntitySerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AddEntityPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeUnsignedInt(buffer, packet.getEntityType());
        helper.writeVector3f(buffer, packet.getPosition());
        helper.writeVector3f(buffer, packet.getMotion());
        helper.writeVector2f(buffer, packet.getRotation());
        buffer.writeFloatLE(packet.getHeadRotation());
        helper.writeArray(buffer, packet.getAttributes(), this::writeAttribute);
        helper.writeEntityData(buffer, packet.getMetadata());
        helper.writeArray(buffer, packet.getEntityLinks(), helper::writeEntityLink);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AddEntityPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setEntityType(VarInts.readUnsignedInt(buffer));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setMotion(helper.readVector3f(buffer));
        packet.setRotation(helper.readVector2f(buffer));
        packet.setHeadRotation(buffer.readFloatLE());
        helper.readArray(buffer, packet.getAttributes(), this::readAttribute);
        helper.readEntityData(buffer, packet.getMetadata());
        helper.readArray(buffer, packet.getEntityLinks(), helper::readEntityLink);
    }

    public AttributeData readAttribute(ByteBuf buffer, BedrockCodecHelper helper) {

        String name = helper.readString(buffer);
        float min = buffer.readFloatLE();
        float max = buffer.readFloatLE();
        float val = buffer.readFloatLE();

        return new AttributeData(name, min, max, val);
    }

    public void writeAttribute(ByteBuf buffer, BedrockCodecHelper helper, AttributeData attribute) {
        requireNonNull(attribute, "attribute is null");

        helper.writeString(buffer, attribute.getName());
        buffer.writeFloatLE(attribute.getMinimum());
        buffer.writeFloatLE(attribute.getMaximum());
        buffer.writeFloatLE(attribute.getValue());
    }
}
