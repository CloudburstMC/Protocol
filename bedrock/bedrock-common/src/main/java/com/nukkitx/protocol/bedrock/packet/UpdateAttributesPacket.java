package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.AttributeData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class UpdateAttributesPacket extends BedrockPacket {
    private long runtimeEntityId;
    private final List<AttributeData> attributes = new ObjectArrayList<>();
    private long tick;

    public UpdateAttributesPacket addAttribute(AttributeData attribute) {
        this.attributes.add(attribute);
        return this;
    }

    public UpdateAttributesPacket addAttributes(AttributeData... attributes) {
        this.attributes.addAll(Arrays.asList(attributes));
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_ATTRIBUTES;
    }
}
