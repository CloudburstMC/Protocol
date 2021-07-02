package org.cloudburstmc.protocol.bedrock.codec.v448.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v422.serializer.ResourcePacksInfoSerializer_v422;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePacksInfoSerializer_v448 extends ResourcePacksInfoSerializer_v422 {

    public static final ResourcePacksInfoSerializer_v448 INSTANCE = new ResourcePacksInfoSerializer_v448();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        buffer.writeBoolean(packet.isForcedToAccept());
        buffer.writeBoolean(packet.isScriptingEnabled());
        buffer.writeBoolean(packet.isForcingServerPacksEnabled());
        writeArrayShortLE(buffer, packet.getBehaviorPackInfos(), helper, this::writeEntry);
        writeArrayShortLE(buffer, packet.getResourcePackInfos(), helper, this::writeResourcePackEntry);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        packet.setForcedToAccept(buffer.readBoolean());
        packet.setScriptingEnabled(buffer.readBoolean());
        packet.setForcingServerPacksEnabled(buffer.readBoolean());
        readArrayShortLE(buffer, packet.getBehaviorPackInfos(), helper, this::readEntry);
        readArrayShortLE(buffer, packet.getResourcePackInfos(), helper, this::readResourcePackEntry);
    }
}
