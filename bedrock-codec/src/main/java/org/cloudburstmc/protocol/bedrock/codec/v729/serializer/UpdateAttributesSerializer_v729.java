package org.cloudburstmc.protocol.bedrock.codec.v729.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v544.serializer.UpdateAttributesSerializer_v544;
import org.cloudburstmc.protocol.bedrock.data.AttributeData;
import org.cloudburstmc.protocol.bedrock.data.attribute.AttributeModifierData;

import java.util.List;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkNotNull;


@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAttributesSerializer_v729 extends UpdateAttributesSerializer_v544 {

    public static final UpdateAttributesSerializer_v729 INSTANCE = new UpdateAttributesSerializer_v729();

    @Override
    public void writeAttribute(ByteBuf buffer, BedrockCodecHelper helper, AttributeData attribute) {
        checkNotNull(attribute, "attribute");

        buffer.writeFloatLE(attribute.getMinimum());
        buffer.writeFloatLE(attribute.getMaximum());
        buffer.writeFloatLE(attribute.getValue());
        buffer.writeFloatLE(attribute.getDefaultMinimum());
        buffer.writeFloatLE(attribute.getDefaultMaximum());
        buffer.writeFloatLE(attribute.getDefaultValue());
        helper.writeString(buffer, attribute.getName());
        helper.writeArray(buffer, attribute.getModifiers(), this::writeModifier);
    }

    @Override
    public AttributeData readAttribute(ByteBuf buffer, BedrockCodecHelper helper) {
        float min = buffer.readFloatLE();
        float max = buffer.readFloatLE();
        float val = buffer.readFloatLE();
        float defMin = buffer.readFloatLE();
        float defMax = buffer.readFloatLE();
        float def = buffer.readFloatLE();
        String name = helper.readString(buffer);

        List<AttributeModifierData> modifiers = new ObjectArrayList<>();
        helper.readArray(buffer, modifiers, this::readModifier);

        return new AttributeData(name, min, max, val, defMin, defMax, def, modifiers);
    }
}
