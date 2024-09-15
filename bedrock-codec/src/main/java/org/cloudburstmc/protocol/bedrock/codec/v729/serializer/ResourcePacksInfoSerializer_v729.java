package org.cloudburstmc.protocol.bedrock.codec.v729.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v712.serializer.ResourcePacksInfoSerializer_v712;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;


public class ResourcePacksInfoSerializer_v729 extends ResourcePacksInfoSerializer_v712 {
    public static final ResourcePacksInfoSerializer_v729 INSTANCE = new ResourcePacksInfoSerializer_v729();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        buffer.writeBoolean(packet.isForcedToAccept());
        buffer.writeBoolean(packet.isHasAddonPacks());
        buffer.writeBoolean(packet.isScriptingEnabled());
        this.writePacks(buffer, packet.getResourcePackInfos(), helper, true);
        this.writeCDNEntries(buffer, packet, helper);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        packet.setForcedToAccept(buffer.readBoolean());
        packet.setHasAddonPacks(buffer.readBoolean());
        packet.setScriptingEnabled(buffer.readBoolean());
        this.readPacks(buffer, packet.getResourcePackInfos(), helper, true);
        this.readCDNEntries(buffer, packet, helper);
    }
}