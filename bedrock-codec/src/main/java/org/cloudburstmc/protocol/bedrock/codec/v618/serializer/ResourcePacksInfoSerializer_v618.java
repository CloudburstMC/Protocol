package org.cloudburstmc.protocol.bedrock.codec.v618.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v448.serializer.ResourcePacksInfoSerializer_v448;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePacksInfoSerializer_v618 extends ResourcePacksInfoSerializer_v448 {

    public static final ResourcePacksInfoSerializer_v618 INSTANCE = new ResourcePacksInfoSerializer_v618();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        super.serialize(buffer, helper, packet);
        this.writeCDNEntries(buffer, packet, helper);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        super.deserialize(buffer, helper, packet);
        this.readCDNEntries(buffer, packet, helper);
    }

    protected void writeCDNEntries(ByteBuf buffer, ResourcePacksInfoPacket packet, BedrockCodecHelper helper) {
        helper.writeArray(buffer, packet.getCDNEntries(), (buf, entry) -> {
            helper.writeString(buf, entry.getPackId());
            helper.writeString(buf, entry.getRemoteUrl());
        });
    }

    protected void readCDNEntries(ByteBuf buffer, ResourcePacksInfoPacket packet, BedrockCodecHelper helper) {
        helper.readArray(buffer, packet.getCDNEntries(), buf ->
                new ResourcePacksInfoPacket.CDNEntry(helper.readString(buf), helper.readString(buf)));
    }
}
