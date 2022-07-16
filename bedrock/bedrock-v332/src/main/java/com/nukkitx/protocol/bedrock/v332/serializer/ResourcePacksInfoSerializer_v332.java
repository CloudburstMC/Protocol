package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.ResourcePacksInfoPacket;
import com.nukkitx.protocol.bedrock.v291.serializer.ResourcePacksInfoSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePacksInfoSerializer_v332 extends ResourcePacksInfoSerializer_v291 {
    public static final ResourcePacksInfoSerializer_v332 INSTANCE = new ResourcePacksInfoSerializer_v332();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket packet) {
        buffer.writeBoolean(packet.isForcedToAccept());
        buffer.writeBoolean(packet.isScriptingEnabled());
        helper.writeArray(buffer, packet.getBehaviorPackInfos(), ByteBuf::writeShortLE, this::writeEntry);
        helper.writeArray(buffer, packet.getResourcePackInfos(), ByteBuf::writeShortLE, this::writeEntry);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket packet) {
        packet.setForcedToAccept(buffer.readBoolean());
        packet.setScriptingEnabled(buffer.readBoolean());
        helper.readArray(buffer, packet.getBehaviorPackInfos(), ByteBuf::readUnsignedShortLE, this::readEntry);
        helper.readArray(buffer, packet.getResourcePackInfos(), ByteBuf::readUnsignedShortLE, this::readEntry);
    }

    @Override
    public void writeEntry(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket.Entry entry) {
        super.writeEntry(buffer, helper, entry);

        buffer.writeBoolean(entry.isScripting());
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
}
