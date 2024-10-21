package org.cloudburstmc.protocol.bedrock.codec.v618.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v448.serializer.ResourcePacksInfoSerializer_v448;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.HashMap;
import java.util.Map;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePacksInfoSerializer_v618 extends ResourcePacksInfoSerializer_v448 {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(ResourcePacksInfoSerializer_v618.class);

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
        ByteBuf byteBuf = buffer.alloc().ioBuffer();
        try {
            int size = 0;
            for (ResourcePacksInfoPacket.Entry info : packet.getResourcePackInfos()) {
                if (info.getCdnUrl() != null) {
                    helper.writeString(byteBuf, info.getPackId() + "_" + info.getPackVersion());
                    helper.writeString(byteBuf, info.getCdnUrl());
                    size++;
                }
            }

            VarInts.writeUnsignedInt(buffer, size);
            if (size > 0) {
                buffer.writeBytes(byteBuf);
            }
        } finally {
            byteBuf.release();
        }
    }

    protected void readCDNEntries(ByteBuf buffer, ResourcePacksInfoPacket packet, BedrockCodecHelper helper) {
        int size = VarInts.readUnsignedInt(buffer);
        checkArgument(helper.getEncodingSettings().maxListSize() <= 0 || size <= helper.getEncodingSettings().maxListSize(), "CDN entries size is too big: %s", size);

        if (size == 0 || packet.getResourcePackInfos().isEmpty()) {
            return;
        }

        Map<String, String> cdnUrls = new HashMap<>();
        for (int i = 0; i < size; i++) {
            String idVersion = helper.readString(buffer);
            String url = helper.readString(buffer);
            cdnUrls.put(idVersion, url);
        }

        for (ResourcePacksInfoPacket.Entry info : packet.getResourcePackInfos()) {
            String url = cdnUrls.remove(info.getPackId() + "_" + info.getPackVersion());
            if (url != null) {
                info.setCdnUrl(url);
            }
        }

        if (log.isDebugEnabled() && !cdnUrls.isEmpty()) {
            log.debug("Found {} CDN URLs that do not match any resource pack", cdnUrls.size());
        }
    }
}
