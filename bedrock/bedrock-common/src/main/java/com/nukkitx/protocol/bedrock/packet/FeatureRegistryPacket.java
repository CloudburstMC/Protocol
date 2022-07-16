package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.FeatureData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * World generation features used for client-side chunk generation.
 *
 * @since 1.19.20
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(callSuper = false)
public class FeatureRegistryPacket extends BedrockPacket {
    private final List<FeatureData> features = new ObjectArrayList<>();

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.FEATURE_REGISTRY;
    }
}
