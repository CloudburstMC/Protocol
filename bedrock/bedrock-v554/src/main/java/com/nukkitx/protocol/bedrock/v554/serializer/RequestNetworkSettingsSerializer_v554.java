package com.nukkitx.protocol.bedrock.v554.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.RequestNetworkSettingsPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestNetworkSettingsSerializer_v554 implements BedrockPacketSerializer<RequestNetworkSettingsPacket> {

    public static final RequestNetworkSettingsSerializer_v554 INSTANCE = new RequestNetworkSettingsSerializer_v554();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RequestNetworkSettingsPacket packet) {
        buffer.writeInt(packet.getProtocolVersion());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RequestNetworkSettingsPacket packet) {
        packet.setProtocolVersion(buffer.readInt());
    }
}
