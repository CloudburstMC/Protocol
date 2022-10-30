package org.cloudburstmc.protocol.bedrock.codec.v440.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.AddVolumeEntityPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddVolumeEntitySerializer_v440 implements BedrockPacketSerializer<AddVolumeEntityPacket> {

    public static final AddVolumeEntitySerializer_v440 INSTANCE = new AddVolumeEntitySerializer_v440();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AddVolumeEntityPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getId());
        helper.writeTag(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AddVolumeEntityPacket packet) {
        packet.setId(VarInts.readUnsignedInt(buffer));
        packet.setData(helper.readTag(buffer, NbtMap.class));
    }
}
