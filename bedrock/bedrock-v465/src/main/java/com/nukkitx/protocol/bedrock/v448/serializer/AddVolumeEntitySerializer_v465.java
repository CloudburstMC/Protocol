package com.nukkitx.protocol.bedrock.v448.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.AddVolumeEntityPacket;
import com.nukkitx.protocol.bedrock.v440.serializer.AddVolumeEntitySerializer_v440;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AddVolumeEntitySerializer_v465 extends AddVolumeEntitySerializer_v440 {

    public static final AddVolumeEntitySerializer_v465 INSTANCE = new AddVolumeEntitySerializer_v465();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AddVolumeEntityPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeString(buffer, packet.getEngineVersion());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AddVolumeEntityPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setEngineVersion(helper.readString(buffer));
    }
}
