package org.cloudburstmc.protocol.bedrock.codec.v332.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.ResourcePacksInfoSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePacksInfoSerializer_v332 extends ResourcePacksInfoSerializer_v291 {
    public static final ResourcePacksInfoSerializer_v332 INSTANCE = new ResourcePacksInfoSerializer_v332();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        buffer.writeBoolean(packet.isForcedToAccept());
        buffer.writeBoolean(packet.isScriptingEnabled());
        writePacks(buffer, packet.getBehaviorPackInfos(), helper, false);
        writePacks(buffer, packet.getResourcePackInfos(), helper, true);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        packet.setForcedToAccept(buffer.readBoolean());
        packet.setScriptingEnabled(buffer.readBoolean());
        readPacks(buffer, packet.getBehaviorPackInfos(), helper, false);
        readPacks(buffer, packet.getResourcePackInfos(), helper, true);
    }

    @Override
    public void writeEntry(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket.Entry entry,
                              boolean resource) {
        super.writeEntry(buffer, helper, entry, resource);

        buffer.writeBoolean(entry.isScripting());
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
        return new ResourcePacksInfoPacket.Entry(packId, packVersion, packSize, contentKey, subPackName, contentId,
                isScripting, false, false);
    }
}
