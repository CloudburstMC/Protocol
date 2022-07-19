package com.nukkitx.protocol.bedrock.v448.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.ResourcePacksInfoPacket;
import com.nukkitx.protocol.bedrock.v422.serializer.ResourcePacksInfoSerializer_v422;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePacksInfoSerializer_v448 extends ResourcePacksInfoSerializer_v422 {

    public static final ResourcePacksInfoSerializer_v448 INSTANCE = new ResourcePacksInfoSerializer_v448();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket packet) {
        buffer.writeBoolean(packet.isForcedToAccept());
        buffer.writeBoolean(packet.isScriptingEnabled());
        buffer.writeBoolean(packet.isForcingServerPacksEnabled());
        helper.writeArray(buffer, packet.getBehaviorPackInfos(), ByteBuf::writeShortLE, this::writeEntry);
        helper.writeArray(buffer, packet.getResourcePackInfos(), ByteBuf::writeShortLE, this::writeResourcePackEntry);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket packet) {
        packet.setForcedToAccept(buffer.readBoolean());
        packet.setScriptingEnabled(buffer.readBoolean());
        packet.setForcingServerPacksEnabled(buffer.readBoolean());
        helper.readArray(buffer, packet.getBehaviorPackInfos(), ByteBuf::readUnsignedShortLE, this::readEntry);
        helper.readArray(buffer, packet.getResourcePackInfos(), ByteBuf::readUnsignedShortLE, this::readResourcePackEntry);
    }
}
