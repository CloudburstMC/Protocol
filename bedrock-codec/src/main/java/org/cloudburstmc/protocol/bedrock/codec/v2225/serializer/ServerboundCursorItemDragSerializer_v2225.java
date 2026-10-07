package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundCursorItemDragPacket;

public class ServerboundCursorItemDragSerializer_v2225 implements BedrockPacketSerializer<ServerboundCursorItemDragPacket> {

    public static final ServerboundCursorItemDragSerializer_v2225 INSTANCE = new ServerboundCursorItemDragSerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundCursorItemDragPacket packet) {
        buffer.writeByte(packet.getState().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundCursorItemDragPacket packet) {
        packet.setState(ServerboundCursorItemDragPacket.State.values()[buffer.readUnsignedByte()]);
    }
}
