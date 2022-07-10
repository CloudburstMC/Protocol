package com.nukkitx.protocol.bedrock.v534.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.EditorNetworkPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditorNetworkSerializer_v534 implements BedrockPacketSerializer<EditorNetworkPacket> {
    public static final EditorNetworkSerializer_v534 INSTANCE = new EditorNetworkSerializer_v534();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EditorNetworkPacket packet) {
        helper.writeTag(buffer, packet.getPayload());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EditorNetworkPacket packet) {
        packet.setPayload(helper.readTag(buffer));
    }
}
