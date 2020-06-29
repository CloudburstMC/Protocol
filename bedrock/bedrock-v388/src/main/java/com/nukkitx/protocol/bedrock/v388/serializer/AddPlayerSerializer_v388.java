package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.AddPlayerPacket;
import com.nukkitx.protocol.bedrock.v291.serializer.AddPlayerSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddPlayerSerializer_v388 extends AddPlayerSerializer_v291 {
    public static final AddPlayerSerializer_v388 INSTANCE = new AddPlayerSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AddPlayerPacket packet) {
        super.serialize(buffer, helper, packet);

        buffer.writeIntLE(packet.getBuildPlatform());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AddPlayerPacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setBuildPlatform(buffer.readIntLE());
    }
}
