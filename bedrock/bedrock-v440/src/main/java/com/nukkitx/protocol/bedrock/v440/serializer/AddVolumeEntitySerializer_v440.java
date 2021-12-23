package com.nukkitx.protocol.bedrock.v440.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.AddVolumeEntityPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddVolumeEntitySerializer_v440 implements BedrockPacketSerializer<AddVolumeEntityPacket> {

    public static final AddVolumeEntitySerializer_v440 INSTANCE = new AddVolumeEntitySerializer_v440();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AddVolumeEntityPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getId());
        helper.writeTag(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AddVolumeEntityPacket packet) {
        packet.setId(VarInts.readUnsignedInt(buffer));
        packet.setData(helper.readTag(buffer));
    }
}
