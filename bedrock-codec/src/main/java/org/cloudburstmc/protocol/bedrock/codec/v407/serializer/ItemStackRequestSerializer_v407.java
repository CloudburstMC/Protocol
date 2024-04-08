package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackRequestPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemStackRequestSerializer_v407 implements BedrockPacketSerializer<ItemStackRequestPacket> {

    public static final ItemStackRequestSerializer_v407 INSTANCE = new ItemStackRequestSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ItemStackRequestPacket packet) {
        helper.writeArray(buffer, packet.getRequests(), helper::writeItemStackRequest);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ItemStackRequestPacket packet) {
        helper.readArray(buffer, packet.getRequests(), helper::readItemStackRequest, 64); // 64 should be enough
    }
}
