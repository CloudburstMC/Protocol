package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.EmotePacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EmoteSerializer_v388 implements PacketSerializer<EmotePacket> {

    public static final EmoteSerializer_v388 INSTANCE = new EmoteSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, EmotePacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        BedrockUtils.writeString(buffer, packet.getEmoteId());
        buffer.writeByte(packet.getFlags());
    }

    @Override
    public void deserialize(ByteBuf buffer, EmotePacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setEmoteId(BedrockUtils.readString(buffer));
        packet.setFlags(buffer.readByte());
    }
}
