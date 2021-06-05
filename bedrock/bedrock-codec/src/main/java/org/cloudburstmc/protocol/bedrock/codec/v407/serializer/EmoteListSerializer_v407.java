package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.EmoteListPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmoteListSerializer_v407 implements BedrockPacketSerializer<EmoteListPacket> {
    public static final EmoteListSerializer_v407 INSTANCE = new EmoteListSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EmoteListPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeArray(buffer, packet.getPieceIds(), helper::writeUuid);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EmoteListPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        helper.readArray(buffer, packet.getPieceIds(), helper::readUuid);
    }
}
