package org.cloudburstmc.protocol.bedrock.codec.v544.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.UpdateAttributesSerializer_v419;
import org.cloudburstmc.protocol.bedrock.data.AttributeData;
import org.cloudburstmc.protocol.bedrock.data.attribute.AttributeModifierData;
import org.cloudburstmc.protocol.bedrock.data.attribute.AttributeOperation;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAttributesSerializer_v544 extends UpdateAttributesSerializer_v419 {
    protected static final AttributeOperation[] VALUES = AttributeOperation.values();

    public static final UpdateAttributesSerializer_v544 INSTANCE = new UpdateAttributesSerializer_v544();

    @Override
    public void writeAttribute(ByteBuf buffer, BedrockCodecHelper helper, AttributeData attribute) {
        super.writeAttribute(buffer, helper, attribute);

        helper.writeArray(buffer, attribute.getModifiers(), this::writeModifier);
    }

    @Override
    public AttributeData readAttribute(ByteBuf buffer, BedrockCodecHelper helper) {
        float min = buffer.readFloatLE();
        float max = buffer.readFloatLE();
        float val = buffer.readFloatLE();
        float def = buffer.readFloatLE();
        String name = helper.readString(buffer);

        List<AttributeModifierData> modifiers = new ObjectArrayList<>();
        helper.readArray(buffer, modifiers, this::readModifier);

        return new AttributeData(name, min, max, val, def, modifiers);
    }

    public void writeModifier(ByteBuf buffer, BedrockCodecHelper helper, AttributeModifierData modifier) {
        helper.writeString(buffer, modifier.getId());
        helper.writeString(buffer, modifier.getName());
        buffer.writeFloatLE(modifier.getAmount());
        buffer.writeIntLE(modifier.getOperation().ordinal());
        buffer.writeIntLE(modifier.getOperand());
        buffer.writeBoolean(modifier.isSerializable());
    }

    public AttributeModifierData readModifier(ByteBuf buffer, BedrockCodecHelper helper) {
        String id = helper.readString(buffer);
        String name = helper.readString(buffer);
        float amount = buffer.readFloatLE();
        AttributeOperation operation = VALUES[buffer.readIntLE()];
        int operand = buffer.readIntLE();
        boolean serializable = buffer.readBoolean();

        return new AttributeModifierData(id, name, amount, operation, operand, serializable);
    }
}
