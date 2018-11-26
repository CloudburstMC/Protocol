package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.ResourcePackStackPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class ResourcePackStackPacket_v291 extends ResourcePackStackPacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeBoolean(forcedToAccept);
        BedrockUtils.writePackInstanceEntries(buffer, behaviorPacks);
        BedrockUtils.writePackInstanceEntries(buffer, resourcePacks);
    }

    @Override
    public void decode(ByteBuf buffer) {
        forcedToAccept = buffer.readBoolean();
        behaviorPacks.addAll(BedrockUtils.readPackInstanceEntries(buffer));
        resourcePacks.addAll(BedrockUtils.readPackInstanceEntries(buffer));
    }
}
