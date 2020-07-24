package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.packet.CreativeContentPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreativeContentSerializer_v407 implements BedrockPacketSerializer<CreativeContentPacket> {

    public static final CreativeContentSerializer_v407 INSTANCE = new CreativeContentSerializer_v407();

    private static final ItemData[] EMPTY = new ItemData[0];

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CreativeContentPacket packet) {
        helper.writeArray(buffer, packet.getContents(), helper::writeNetItem);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CreativeContentPacket packet) {
        packet.setContents(helper.readArray(buffer, EMPTY, helper::readNetItem));
    }
}
