package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.protocol.bedrock.packet.AutomationClientConnectPacket;
import com.nukkitx.protocol.bedrock.v313.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AutomationClientConnectSerializer_v313 implements PacketSerializer<AutomationClientConnectPacket> {
    public static final AutomationClientConnectSerializer_v313 INSTANCE = new AutomationClientConnectSerializer_v313();


    @Override
    public void serialize(ByteBuf buffer, AutomationClientConnectPacket packet) {
        BedrockUtils.writeString(buffer, packet.getAddress());
    }

    @Override
    public void deserialize(ByteBuf buffer, AutomationClientConnectPacket packet) {
        packet.setAddress(BedrockUtils.readString(buffer));
    }
}
