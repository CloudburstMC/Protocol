package com.nukkitx.protocol.bedrock.v5000425.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.FilterTextPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilterTextSerializer_v5000425 implements BedrockPacketSerializer<FilterTextPacket> {

    public static final FilterTextSerializer_v5000425 INSTANCE = new FilterTextSerializer_v5000425();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, FilterTextPacket packet) {
        helper.writeString(buffer, packet.getText());
        buffer.writeBoolean(packet.isFromServer());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, FilterTextPacket packet) {
        packet.setText(helper.readString(buffer));
        packet.setFromServer(buffer.readBoolean());
    }
}