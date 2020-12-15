package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.PlayStatusPacket.Status;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayStatusSerializer_v291 implements BedrockPacketSerializer<PlayStatusPacket> {
    public static final PlayStatusSerializer_v291 INSTANCE = new PlayStatusSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayStatusPacket packet) {
        buffer.writeInt(packet.getStatus().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayStatusPacket packet) {
        packet.setStatus(Status.values()[buffer.readInt()]);
    }
}
