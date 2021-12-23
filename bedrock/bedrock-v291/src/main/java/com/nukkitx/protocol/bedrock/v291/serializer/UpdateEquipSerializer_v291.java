package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.UpdateEquipPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateEquipSerializer_v291 implements BedrockPacketSerializer<UpdateEquipPacket> {
    public static final UpdateEquipSerializer_v291 INSTANCE = new UpdateEquipSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateEquipPacket packet) {
        buffer.writeByte(packet.getWindowId());
        buffer.writeByte(packet.getWindowType());
        VarInts.writeInt(buffer, packet.getSize());
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        helper.writeTag(buffer, packet.getTag());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateEquipPacket packet) {
        packet.setWindowId(buffer.readUnsignedByte());
        packet.setWindowType(buffer.readUnsignedByte());
        packet.setSize(VarInts.readInt(buffer));
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setTag(helper.readTag(buffer));
    }
}
