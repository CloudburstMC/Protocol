package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.RemoveObjectivePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RemoveObjectiveSerializer_v291 implements BedrockPacketSerializer<RemoveObjectivePacket> {
    public static final RemoveObjectiveSerializer_v291 INSTANCE = new RemoveObjectiveSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveObjectivePacket packet) {
        helper.writeString(buffer, packet.getObjectiveId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveObjectivePacket packet) {
        packet.setObjectiveId(helper.readString(buffer));
    }
}
