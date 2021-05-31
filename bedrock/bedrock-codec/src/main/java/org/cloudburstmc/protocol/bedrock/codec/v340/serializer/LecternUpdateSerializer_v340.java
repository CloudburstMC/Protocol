package org.cloudburstmc.protocol.bedrock.codec.v340.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.LecternUpdatePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LecternUpdateSerializer_v340 implements BedrockPacketSerializer<LecternUpdatePacket> {
    public static final LecternUpdateSerializer_v340 INSTANCE = new LecternUpdateSerializer_v340();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, LecternUpdatePacket packet) {
        buffer.writeByte(packet.getPage());
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
        buffer.writeBoolean(packet.isDroppingBook());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, LecternUpdatePacket packet) {
        packet.setPage(buffer.readUnsignedByte());
        packet.setBlockPosition(helper.readBlockPosition(buffer));
        packet.setDroppingBook(buffer.readBoolean());
    }
}
