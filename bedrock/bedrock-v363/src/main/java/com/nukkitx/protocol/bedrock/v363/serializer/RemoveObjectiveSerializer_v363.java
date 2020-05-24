package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.protocol.bedrock.packet.RemoveObjectivePacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RemoveObjectiveSerializer_v363 implements PacketSerializer<RemoveObjectivePacket> {
    public static final RemoveObjectiveSerializer_v363 INSTANCE = new RemoveObjectiveSerializer_v363();


    @Override
    public void serialize(ByteBuf buffer, RemoveObjectivePacket packet) {
        BedrockUtils.writeString(buffer, packet.getObjectiveId());
    }

    @Override
    public void deserialize(ByteBuf buffer, RemoveObjectivePacket packet) {
        packet.setObjectiveId(BedrockUtils.readString(buffer));
    }
}
