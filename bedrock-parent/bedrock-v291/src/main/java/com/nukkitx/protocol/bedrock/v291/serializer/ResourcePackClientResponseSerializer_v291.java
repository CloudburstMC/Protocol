package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket.Status;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePackClientResponseSerializer_v291 implements BedrockPacketSerializer<ResourcePackClientResponsePacket> {
    public static final ResourcePackClientResponseSerializer_v291 INSTANCE = new ResourcePackClientResponseSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackClientResponsePacket packet) {
        buffer.writeByte(packet.getStatus().ordinal());

        helper.writeArrayShortLE(buffer, packet.getPackIds(), helper::writeString);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackClientResponsePacket packet) {
        Status status = Status.values()[buffer.readUnsignedByte()];
        packet.setStatus(status);

        helper.readArrayShortLE(buffer, packet.getPackIds(), helper::readString);
    }
}
