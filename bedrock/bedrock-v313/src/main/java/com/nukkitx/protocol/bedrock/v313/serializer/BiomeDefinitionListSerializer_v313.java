package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.BiomeDefinitionListPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BiomeDefinitionListSerializer_v313 implements BedrockPacketSerializer<BiomeDefinitionListPacket> {
    public static final BiomeDefinitionListSerializer_v313 INSTANCE = new BiomeDefinitionListSerializer_v313();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, BiomeDefinitionListPacket packet) {
        helper.writeTag(buffer, packet.getDefinitions());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, BiomeDefinitionListPacket packet) {
        packet.setDefinitions(helper.readTag(buffer));
    }
}
