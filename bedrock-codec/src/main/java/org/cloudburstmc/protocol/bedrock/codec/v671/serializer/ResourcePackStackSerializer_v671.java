package org.cloudburstmc.protocol.bedrock.codec.v671.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.ResourcePackStackSerializer_v419;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackStackPacket;

public class ResourcePackStackSerializer_v671 extends ResourcePackStackSerializer_v419 {
    public static final ResourcePackStackSerializer_v671 INSTANCE = new ResourcePackStackSerializer_v671();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePackStackPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeBoolean(packet.isHasEditorPacks());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePackStackPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setHasEditorPacks(buffer.readBoolean());
    }
}
