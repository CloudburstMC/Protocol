package org.cloudburstmc.protocol.bedrock.codec.v766.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v748.serializer.ResourcePacksInfoSerializer_v748;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;

import java.util.Objects;
import java.util.UUID;

public class ResourcePacksInfoSerializer_v766 extends ResourcePacksInfoSerializer_v748 {
    public static final ResourcePacksInfoSerializer_v766 INSTANCE = new ResourcePacksInfoSerializer_v766();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        buffer.writeBoolean(packet.isForcedToAccept());
        buffer.writeBoolean(packet.isHasAddonPacks());
        buffer.writeBoolean(packet.isScriptingEnabled());
        helper.writeUuid(buffer, packet.getWorldTemplateId());
        helper.writeString(buffer, packet.getWorldTemplateVersion());
        writePacks(buffer, packet.getResourcePackInfos(), helper, true);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        packet.setForcedToAccept(buffer.readBoolean());
        packet.setHasAddonPacks(buffer.readBoolean());
        packet.setScriptingEnabled(buffer.readBoolean());
        packet.setWorldTemplateId(helper.readUuid(buffer));
        packet.setWorldTemplateVersion(helper.readString(buffer));
        readPacks(buffer, packet.getResourcePackInfos(), helper, true);
    }

    @Override
    public void writeEntry(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket.Entry entry, boolean resource) {
        Objects.requireNonNull(entry, "ResourcePacketInfoPacket entry was null");

        helper.writeUuid(buffer, entry.getPackId());
        helper.writeString(buffer, entry.getPackVersion());
        buffer.writeLongLE(entry.getPackSize());
        helper.writeString(buffer, entry.getContentKey());
        helper.writeString(buffer, entry.getSubPackName());
        helper.writeString(buffer, entry.getContentId());
        buffer.writeBoolean(entry.isScripting());
        buffer.writeBoolean(entry.isAddonPack());
        if (resource) {
            buffer.writeBoolean(entry.isRaytracingCapable());
        }
        helper.writeString(buffer, entry.getCdnUrl() == null ? "" : entry.getCdnUrl());
    }

    @Override
    public ResourcePacksInfoPacket.Entry readEntry(ByteBuf buffer, BedrockCodecHelper helper, boolean resource) {
        UUID packId = helper.readUuid(buffer);
        String packVersion = helper.readString(buffer);
        long packSize = buffer.readLongLE();
        String contentKey = helper.readString(buffer);
        String subPackName = helper.readString(buffer);
        String contentId = helper.readString(buffer);
        boolean isScripting = buffer.readBoolean();
        boolean isAddonPack = buffer.readBoolean();
        boolean raytracingCapable = resource && buffer.readBoolean();
        String cdnUrl = helper.readString(buffer);
        return new ResourcePacksInfoPacket.Entry(packId, packVersion, packSize, contentKey, subPackName, contentId,
                isScripting, raytracingCapable, isAddonPack, cdnUrl);
    }
}