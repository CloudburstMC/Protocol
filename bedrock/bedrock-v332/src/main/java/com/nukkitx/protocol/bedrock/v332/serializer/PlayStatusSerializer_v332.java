package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.PlayStatusPacket.Status;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayStatusSerializer_v332 implements PacketSerializer<PlayStatusPacket> {
    public static final PlayStatusSerializer_v332 INSTANCE = new PlayStatusSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, PlayStatusPacket packet) {
        buffer.writeInt(packet.getStatus().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, PlayStatusPacket packet) {
        packet.setStatus(Status.values()[buffer.readInt()]);
    }
}
