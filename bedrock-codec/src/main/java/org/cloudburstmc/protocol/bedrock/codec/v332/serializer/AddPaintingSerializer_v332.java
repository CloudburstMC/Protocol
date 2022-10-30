package org.cloudburstmc.protocol.bedrock.codec.v332.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.AddPaintingPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddPaintingSerializer_v332 implements BedrockPacketSerializer<AddPaintingPacket> {
    public static final AddPaintingSerializer_v332 INSTANCE = new AddPaintingSerializer_v332();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AddPaintingPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeBlockPosition(buffer, packet.getPosition().toInt());
        VarInts.writeInt(buffer, packet.getDirection());
        helper.writeString(buffer, packet.getMotive());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AddPaintingPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPosition(helper.readBlockPosition(buffer).toFloat());
        packet.setDirection(VarInts.readInt(buffer));
        packet.setMotive(helper.readString(buffer));
    }
}
