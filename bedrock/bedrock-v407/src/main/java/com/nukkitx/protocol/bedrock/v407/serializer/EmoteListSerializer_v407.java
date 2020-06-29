package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.EmoteListPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmoteListSerializer_v407 implements BedrockPacketSerializer<EmoteListPacket> {
    public static final EmoteListSerializer_v407 INSTANCE = new EmoteListSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EmoteListPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeArray(buffer, packet.getPieceIds(), helper::writeUuid);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EmoteListPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        helper.readArray(buffer, packet.getPieceIds(), helper::readUuid);
    }
}
