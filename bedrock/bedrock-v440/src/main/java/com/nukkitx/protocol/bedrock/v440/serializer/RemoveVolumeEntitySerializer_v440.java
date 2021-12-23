package com.nukkitx.protocol.bedrock.v440.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.RemoveVolumeEntityPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RemoveVolumeEntitySerializer_v440 implements BedrockPacketSerializer<RemoveVolumeEntityPacket> {

    public static final RemoveVolumeEntitySerializer_v440 INSTANCE = new RemoveVolumeEntitySerializer_v440();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveVolumeEntityPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveVolumeEntityPacket packet) {
        packet.setId(VarInts.readUnsignedInt(buffer));
    }
}
