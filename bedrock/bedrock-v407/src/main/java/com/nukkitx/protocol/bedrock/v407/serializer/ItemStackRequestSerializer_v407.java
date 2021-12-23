package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.packet.ItemStackRequestPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemStackRequestSerializer_v407 implements BedrockPacketSerializer<ItemStackRequestPacket> {

    public static final ItemStackRequestSerializer_v407 INSTANCE = new ItemStackRequestSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackRequestPacket packet, BedrockSession session) {
        helper.writeArray(buffer, packet.getRequests(), (buf, requests) -> helper.writeItemStackRequest(buffer, session, requests));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackRequestPacket packet, BedrockSession session) {
        helper.readArray(buffer, packet.getRequests(), buf -> helper.readItemStackRequest(buf, session));
    }
}
