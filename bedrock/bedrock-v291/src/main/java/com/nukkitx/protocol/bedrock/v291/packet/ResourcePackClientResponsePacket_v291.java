package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class ResourcePackClientResponsePacket_v291 extends ResourcePackClientResponsePacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(status.ordinal());

        buffer.writeIntLE(packIds.size());

        for (String packId : packIds) {
            BedrockUtils.writeString(buffer, packId);
        }
    }

    @Override
    public void decode(ByteBuf buffer) {
        status = Status.values()[buffer.readByte()];

        int packIdsCount = buffer.readShortLE();
        for (int i = 0; i < packIdsCount; i++) {
            packIds.add(BedrockUtils.readString(buffer));
        }
    }
}
