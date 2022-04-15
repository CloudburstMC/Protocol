package com.nukkitx.protocol.bedrock.v503.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.RemoveVolumeEntityPacket;
import com.nukkitx.protocol.bedrock.v440.serializer.RemoveVolumeEntitySerializer_v440;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RemoveVolumeEntitySerializer_v503 extends RemoveVolumeEntitySerializer_v440 {
    public static final RemoveVolumeEntitySerializer_v503 INSTANCE = new RemoveVolumeEntitySerializer_v503();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveVolumeEntityPacket packet) {
        super.serialize(buffer, helper, packet);
        VarInts.writeInt(buffer, packet.getDimension());
    }

    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RemoveVolumeEntityPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setDimension(VarInts.readInt(buffer));
    }
}
