package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v662.serializer.ResourcePacksInfoSerializer_v622;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;

import java.util.Objects;

public class ResourcePacksInfoSerializer_v712 extends ResourcePacksInfoSerializer_v622 {
    public static final ResourcePacksInfoSerializer_v712 INSTANCE = new ResourcePacksInfoSerializer_v712();

    @Override
    public void writeEntry(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket.Entry entry, boolean resource) {
        Objects.requireNonNull(entry, "ResourcePacketInfoPacket entry was null");

        helper.writeString(buffer, entry.getPackId());
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
    }

    @Override
    public ResourcePacksInfoPacket.Entry readEntry(ByteBuf buffer, BedrockCodecHelper helper, boolean resource) {
        String packId = helper.readString(buffer);
        String packVersion = helper.readString(buffer);
        long packSize = buffer.readLongLE();
        String contentKey = helper.readString(buffer);
        String subPackName = helper.readString(buffer);
        String contentId = helper.readString(buffer);
        boolean isScripting = buffer.readBoolean();
        boolean isAddonPack = buffer.readBoolean();
        boolean raytracingCapable = resource && buffer.readBoolean();
        return new ResourcePacksInfoPacket.Entry(packId, packVersion, packSize, contentKey, subPackName, contentId,
                isScripting, raytracingCapable, isAddonPack, null);
    }
}