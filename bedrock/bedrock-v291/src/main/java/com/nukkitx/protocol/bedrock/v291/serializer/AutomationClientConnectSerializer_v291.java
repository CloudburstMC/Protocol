package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.AutomationClientConnectPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AutomationClientConnectSerializer_v291 implements BedrockPacketSerializer<AutomationClientConnectPacket> {
    public static final AutomationClientConnectSerializer_v291 INSTANCE = new AutomationClientConnectSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AutomationClientConnectPacket packet) {
        helper.writeString(buffer, packet.getAddress());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AutomationClientConnectPacket packet) {
        packet.setAddress(helper.readString(buffer));
    }
}
