package com.nukkitx.protocol.bedrock.v534.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.AddEntityPacket;
import com.nukkitx.protocol.bedrock.v313.serializer.AddEntitySerializer_v313;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddEntitySerializer_v534 extends AddEntitySerializer_v313 {
    public static final AddEntitySerializer_v534 INSTANCE = new AddEntitySerializer_v534();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AddEntityPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeString(buffer, packet.getIdentifier());
        helper.writeVector3f(buffer, packet.getPosition());
        helper.writeVector3f(buffer, packet.getMotion());
        helper.writeVector3f(buffer, packet.getRotation());
        buffer.writeFloatLE(packet.getBodyRotation());
        helper.writeArray(buffer, packet.getAttributes(), this::writeAttribute);
        helper.writeEntityData(buffer, packet.getMetadata());
        helper.writeArray(buffer, packet.getEntityLinks(), helper::writeEntityLink);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AddEntityPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setIdentifier(helper.readString(buffer));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setMotion(helper.readVector3f(buffer));
        packet.setRotation(helper.readVector3f(buffer));
        packet.setBodyRotation(buffer.readFloatLE());
        helper.readArray(buffer, packet.getAttributes(), this::readAttribute);
        helper.readEntityData(buffer, packet.getMetadata());
        helper.readArray(buffer, packet.getEntityLinks(), helper::readEntityLink);
    }
}
