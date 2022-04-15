package com.nukkitx.protocol.bedrock.v503.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.AddVolumeEntityPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddVolumeEntitySerializer_v503 implements BedrockPacketSerializer<AddVolumeEntityPacket> {
    public static final AddVolumeEntitySerializer_v503 INSTANCE = new AddVolumeEntitySerializer_v503();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AddVolumeEntityPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getId());
        helper.writeTag(buffer, packet.getData());
        helper.writeString(buffer, packet.getIdentifier());
        helper.writeString(buffer, packet.getInstanceName());
        helper.writeBlockPosition(buffer, packet.getMinBounds());
        helper.writeBlockPosition(buffer, packet.getMaxBounds());
        VarInts.writeInt(buffer, packet.getDimension());
        helper.writeString(buffer, packet.getEngineVersion());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AddVolumeEntityPacket packet) {
        packet.setId(VarInts.readUnsignedInt(buffer));
        packet.setData(helper.readTag(buffer));
        packet.setIdentifier(helper.readString(buffer));
        packet.setInstanceName(helper.readString(buffer));
        packet.setMinBounds(helper.readBlockPosition(buffer));
        packet.setMaxBounds(helper.readBlockPosition(buffer));
        packet.setDimension(VarInts.readInt(buffer));
        packet.setEngineVersion(helper.readString(buffer));
    }
}
