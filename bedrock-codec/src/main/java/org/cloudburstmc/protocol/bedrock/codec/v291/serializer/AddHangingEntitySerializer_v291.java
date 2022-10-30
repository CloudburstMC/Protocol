package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.AddHangingEntityPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddHangingEntitySerializer_v291 implements BedrockPacketSerializer<AddHangingEntityPacket> {
    public static final AddHangingEntitySerializer_v291 INSTANCE = new AddHangingEntitySerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AddHangingEntityPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeBlockPosition(buffer, packet.getPosition().toInt());
        VarInts.writeInt(buffer, packet.getDirection());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AddHangingEntityPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPosition(helper.readBlockPosition(buffer).toFloat());
        packet.setDirection(VarInts.readInt(buffer));
    }
}
