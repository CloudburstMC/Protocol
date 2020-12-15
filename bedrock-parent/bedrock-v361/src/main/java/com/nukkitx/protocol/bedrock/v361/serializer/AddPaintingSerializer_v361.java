package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.AddPaintingPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.network.VarInts.readInt;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddPaintingSerializer_v361 implements BedrockPacketSerializer<AddPaintingPacket> {
    public static final AddPaintingSerializer_v361 INSTANCE = new AddPaintingSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AddPaintingPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, packet.getDirection());
        helper.writeString(buffer, packet.getMotive());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AddPaintingPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setDirection(readInt(buffer));
        packet.setMotive(helper.readString(buffer));
    }
}
