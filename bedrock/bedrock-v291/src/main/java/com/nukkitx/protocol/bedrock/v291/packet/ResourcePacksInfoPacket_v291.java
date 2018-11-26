package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.ResourcePacksInfoPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class ResourcePacksInfoPacket_v291 extends ResourcePacksInfoPacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeBoolean(forcedToAccept);
        BedrockUtils.writePacksInfoEntries(buffer, behaviorPackInfos);
        BedrockUtils.writePacksInfoEntries(buffer, resourcePackInfos);
    }

    @Override
    public void decode(ByteBuf buffer) {
        forcedToAccept = buffer.readBoolean();
        behaviorPackInfos.addAll(BedrockUtils.readPacksInfoEntries(buffer));
        resourcePackInfos.addAll(BedrockUtils.readPacksInfoEntries(buffer));
    }
}
