package com.nukkitx.protocol.bedrock.v553.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.RequestNetworkSettingsPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestNetworkSettingsSerializer_v553 implements BedrockPacketSerializer<RequestNetworkSettingsPacket> {

    public static final RequestNetworkSettingsSerializer_v553 INSTANCE = new RequestNetworkSettingsSerializer_v553();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RequestNetworkSettingsPacket packet) {
        buffer.writeInt(packet.getProtocolVersion());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RequestNetworkSettingsPacket packet) {
        packet.setProtocolVersion(buffer.readInt());
    }
}
