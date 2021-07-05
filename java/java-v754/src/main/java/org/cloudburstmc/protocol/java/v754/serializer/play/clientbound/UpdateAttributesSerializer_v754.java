package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.entity.Attribute;
import org.cloudburstmc.protocol.java.data.entity.AttributeModifier;
import org.cloudburstmc.protocol.java.data.entity.AttributeType;
import org.cloudburstmc.protocol.java.packet.play.clientbound.UpdateAttributesPacket;

import java.util.List;
import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAttributesSerializer_v754 implements JavaPacketSerializer<UpdateAttributesPacket> {
    public static final UpdateAttributesSerializer_v754 INSTANCE = new UpdateAttributesSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, UpdateAttributesPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getEntityId());
        helper.writeArray(buffer, packet.getAttributes(), (buf, attribute) -> {
            helper.writeKey(buffer, attribute.getType().getKey());
            buffer.writeDouble(attribute.getValue());
            helper.writeArray(buffer, attribute.getModifiers(), (buf1, modifier) -> {
                helper.writeUUID(buffer, modifier.getId());
                buffer.writeDouble(modifier.getAmount());
                buffer.writeByte(modifier.getOperation().ordinal());
            });
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, UpdateAttributesPacket packet) throws PacketSerializeException {
        packet.setEntityId(helper.readVarInt(buffer));
        packet.setAttributes(new ObjectArrayList<>(
                helper.readArray(buffer, new Attribute[0], buf -> {
                    AttributeType type = AttributeType.valueOf(helper.readKey(buffer).toString().split(":")[1].toUpperCase(Locale.ROOT));
                    double value = buffer.readDouble();
                    List<AttributeModifier> modifiers = new ObjectArrayList<>(
                        helper.readArray(buffer, new AttributeModifier[0], buf1 ->
                                new AttributeModifier(helper.readUUID(buffer), buffer.readDouble(), AttributeModifier.Operation.getById(buffer.readByte())))
                    );
                    return new Attribute(type, value, modifiers);
                })
        ));
    }
}
