package org.cloudburstmc.protocol.bedrock.codec.v422.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.ResourcePacksInfoSerializer_v332;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePacksInfoSerializer_v422 extends ResourcePacksInfoSerializer_v332 {
    public static final ResourcePacksInfoSerializer_v422 INSTANCE = new ResourcePacksInfoSerializer_v422();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        buffer.writeBoolean(packet.isForcedToAccept());
        buffer.writeBoolean(packet.isScriptingEnabled());
        writeArrayShortLE(buffer, packet.getBehaviorPackInfos(), helper, this::writeEntry);
        writeArrayShortLE(buffer, packet.getResourcePackInfos(), helper, this::writeResourcePackEntry);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        packet.setForcedToAccept(buffer.readBoolean());
        packet.setScriptingEnabled(buffer.readBoolean());
        readArrayShortLE(buffer, packet.getBehaviorPackInfos(), helper, this::readEntry);
        readArrayShortLE(buffer, packet.getResourcePackInfos(), helper, this::readResourcePackEntry);
    }

    public void writeResourcePackEntry(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket.Entry entry) {
        this.writeEntry(buffer, helper, entry);
        buffer.writeBoolean(entry.isRaytracingCapable());
    }

    @Override
    public ResourcePacksInfoPacket.Entry readEntry(ByteBuf buffer, BedrockCodecHelper helper) {
        String packId = helper.readString(buffer);
        String packVersion = helper.readString(buffer);
        long packSize = buffer.readLongLE();
        String contentKey = helper.readString(buffer);
        String subPackName = helper.readString(buffer);
        String contentId = helper.readString(buffer);
        boolean isScripting = buffer.readBoolean();
        return new ResourcePacksInfoPacket.Entry(packId, packVersion, packSize, contentKey, subPackName, contentId,
                isScripting, false);
    }

    public ResourcePacksInfoPacket.Entry readResourcePackEntry(ByteBuf buffer, BedrockCodecHelper helper) {
        String packId = helper.readString(buffer);
        String packVersion = helper.readString(buffer);
        long packSize = buffer.readLongLE();
        String contentKey = helper.readString(buffer);
        String subPackName = helper.readString(buffer);
        String contentId = helper.readString(buffer);
        boolean isScripting = buffer.readBoolean();
        boolean raytracingCapable = buffer.readBoolean();
        return new ResourcePacksInfoPacket.Entry(packId, packVersion, packSize, contentKey, subPackName, contentId,
                isScripting, raytracingCapable);
    }

}
