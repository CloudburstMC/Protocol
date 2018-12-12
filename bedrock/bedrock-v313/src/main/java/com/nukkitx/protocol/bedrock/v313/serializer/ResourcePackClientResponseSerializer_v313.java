package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket;
import com.nukkitx.protocol.bedrock.v313.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket.Status;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcePackClientResponseSerializer_v313 implements PacketSerializer<ResourcePackClientResponsePacket> {
    public static final ResourcePackClientResponseSerializer_v313 INSTANCE = new ResourcePackClientResponseSerializer_v313();


    @Override
    public void serialize(ByteBuf buffer, ResourcePackClientResponsePacket packet) {
        buffer.writeByte(packet.getStatus().ordinal());

        List<String> packIds = packet.getPackIds();
        buffer.writeIntLE(packIds.size());

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
