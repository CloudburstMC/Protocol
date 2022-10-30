package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.UpdateEquipPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateEquipSerializer_v291 implements BedrockPacketSerializer<UpdateEquipPacket> {
    public static final UpdateEquipSerializer_v291 INSTANCE = new UpdateEquipSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateEquipPacket packet) {
        buffer.writeByte(packet.getWindowId());
        buffer.writeByte(packet.getWindowType());
        VarInts.writeInt(buffer, packet.getSize());
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        helper.writeTag(buffer, packet.getTag());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateEquipPacket packet) {
        packet.setWindowId(buffer.readUnsignedByte());
        packet.setWindowType(buffer.readUnsignedByte());
        packet.setSize(VarInts.readInt(buffer));
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setTag(helper.readTag(buffer, NbtMap.class));
    }
}
