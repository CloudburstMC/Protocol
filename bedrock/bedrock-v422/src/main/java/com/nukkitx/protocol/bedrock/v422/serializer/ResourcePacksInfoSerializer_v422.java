package com.nukkitx.protocol.bedrock.v422.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.ResourcePacksInfoPacket;
import com.nukkitx.protocol.bedrock.v332.serializer.ResourcePacksInfoSerializer_v332;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePacksInfoSerializer_v422 extends ResourcePacksInfoSerializer_v332 {
    public static final ResourcePacksInfoSerializer_v422 INSTANCE = new ResourcePacksInfoSerializer_v422();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket packet) {
        buffer.writeBoolean(packet.isForcedToAccept());
        buffer.writeBoolean(packet.isScriptingEnabled());
        helper.writeArrayShortLE(buffer, packet.getBehaviorPackInfos(), this::writeEntry);
        helper.writeArrayShortLE(buffer, packet.getResourcePackInfos(), this::writeResourcePackEntry);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket packet) {
        packet.setForcedToAccept(buffer.readBoolean());
        packet.setScriptingEnabled(buffer.readBoolean());
        helper.readArrayShortLE(buffer, packet.getBehaviorPackInfos(), this::readEntry);
        helper.readArrayShortLE(buffer, packet.getResourcePackInfos(), this::readResourcePackEntry);
    }

    public void writeResourcePackEntry(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket.Entry entry) {
        this.writeEntry(buffer, helper, entry);
        buffer.writeBoolean(entry.isRaytracingCapable());
    }

    @Override
    public ResourcePacksInfoPacket.Entry readEntry(ByteBuf buffer, BedrockPacketHelper helper) {
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

    public ResourcePacksInfoPacket.Entry readResourcePackEntry(ByteBuf buffer, BedrockPacketHelper helper) {
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
