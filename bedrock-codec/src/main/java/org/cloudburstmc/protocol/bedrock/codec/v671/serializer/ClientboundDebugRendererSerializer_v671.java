package org.cloudburstmc.protocol.bedrock.codec.v671.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v428.serializer.ClientboundDebugRendererSerializer_v428;
import org.cloudburstmc.protocol.bedrock.data.ClientboundDebugRendererType;

public class ClientboundDebugRendererSerializer_v671 extends ClientboundDebugRendererSerializer_v428 {
    public static final ClientboundDebugRendererSerializer_v671 INSTANCE = new ClientboundDebugRendererSerializer_v671();

    @Override
    protected ClientboundDebugRendererType readMarkerType(ByteBuf buffer, BedrockCodecHelper helper) {
        return ClientboundDebugRendererType.values()[buffer.readIntLE()];
    }

    @Override
    protected void writeMarkerType(ByteBuf buffer, BedrockCodecHelper helper, ClientboundDebugRendererType type) {
        buffer.writeIntLE(type.ordinal());
    }
}
