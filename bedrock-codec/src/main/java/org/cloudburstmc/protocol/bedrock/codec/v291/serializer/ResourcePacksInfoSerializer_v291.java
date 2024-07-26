package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePacksInfoSerializer_v291 implements BedrockPacketSerializer<ResourcePacksInfoPacket> {
    public static final ResourcePacksInfoSerializer_v291 INSTANCE = new ResourcePacksInfoSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        buffer.writeBoolean(packet.isForcedToAccept());
        writePacks(buffer, packet.getBehaviorPackInfos(), helper, false);
        writePacks(buffer, packet.getResourcePackInfos(), helper, true);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        packet.setForcedToAccept(buffer.readBoolean());
        readPacks(buffer, packet.getBehaviorPackInfos(), helper, false);
        readPacks(buffer, packet.getResourcePackInfos(), helper, true);
    }

    protected ResourcePacksInfoPacket.Entry readEntry(ByteBuf buffer, BedrockCodecHelper helper, boolean resource) {
        String packId = helper.readString(buffer);
        String packVersion = helper.readString(buffer);
        long packSize = buffer.readLongLE();
        String contentKey = helper.readString(buffer);
        String subPackName = helper.readString(buffer);
        String contentId = helper.readString(buffer);
        return new ResourcePacksInfoPacket.Entry(packId, packVersion, packSize, contentKey, subPackName, contentId, false, false, false);
    }

    protected void writeEntry(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket.Entry entry, boolean resource) {
        requireNonNull(entry, "ResourcePacketInfoPacket entry was null");

        helper.writeString(buffer, entry.getPackId());
        helper.writeString(buffer, entry.getPackVersion());
        buffer.writeLongLE(entry.getPackSize());
        helper.writeString(buffer, entry.getContentKey());
        helper.writeString(buffer, entry.getSubPackName());
        helper.writeString(buffer, entry.getContentId());
    }

    protected void readPacks(ByteBuf buffer, Collection<ResourcePacksInfoPacket.Entry> array, BedrockCodecHelper helper,
                             boolean resource) {
        int length = buffer.readUnsignedShortLE();
        for (int i = 0; i < length; i++) {
            array.add(this.readEntry(buffer, helper, resource));
        }
    }

    protected void writePacks(ByteBuf buffer, Collection<ResourcePacksInfoPacket.Entry> array, BedrockCodecHelper helper,
                              boolean resource) {
        buffer.writeShortLE(array.size());
        for (ResourcePacksInfoPacket.Entry entry : array) {
            this.writeEntry(buffer, helper, entry, resource);
        }
    }
}
