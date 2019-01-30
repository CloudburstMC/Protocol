package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.protocol.bedrock.packet.WSConnectPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WSConnectSerializer_v332 implements PacketSerializer<WSConnectPacket> {
    public static final WSConnectSerializer_v332 INSTANCE = new WSConnectSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, WSConnectPacket packet) {
        BedrockUtils.writeString(buffer, packet.getAddress());
    }

    @Override
    public void deserialize(ByteBuf buffer, WSConnectPacket packet) {
        packet.setAddress(BedrockUtils.readString(buffer));
    }
}
