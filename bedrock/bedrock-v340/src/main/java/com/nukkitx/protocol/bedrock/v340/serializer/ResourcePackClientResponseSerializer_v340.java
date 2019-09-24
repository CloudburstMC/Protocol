package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket;
import com.nukkitx.protocol.bedrock.v340.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket.Status;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcePackClientResponseSerializer_v340 implements PacketSerializer<ResourcePackClientResponsePacket> {
    public static final ResourcePackClientResponseSerializer_v340 INSTANCE = new ResourcePackClientResponseSerializer_v340();


    @Override
    public void serialize(ByteBuf buffer, ResourcePackClientResponsePacket packet) {
        buffer.writeByte(packet.getStatus().ordinal());

        List<String> packIds = packet.getPackIds();
        buffer.writeShortLE(packIds.size());

        for (String packId : packIds) {
            BedrockUtils.writeString(buffer, packId);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, ResourcePackClientResponsePacket packet) {
        Status status = Status.values()[buffer.readByte()];
        packet.setStatus(status);

        List<String> packIds = packet.getPackIds();
        int packIdsCount = buffer.readShortLE();
        for (int i = 0; i < packIdsCount; i++) {
            packIds.add(BedrockUtils.readString(buffer));
        }
    }
}
