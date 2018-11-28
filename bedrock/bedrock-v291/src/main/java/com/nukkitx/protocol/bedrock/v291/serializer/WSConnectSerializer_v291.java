package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.packet.WSConnectPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WSConnectSerializer_v291 implements PacketSerializer<WSConnectPacket> {
    public static final WSConnectSerializer_v291 INSTANCE = new WSConnectSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, WSConnectPacket packet) {
        BedrockUtils.writeString(buffer, packet.getAddress());
    }

    @Override
    public void deserialize(ByteBuf buffer, WSConnectPacket packet) {
        packet.setAddress(BedrockUtils.readString(buffer));
    }
}
