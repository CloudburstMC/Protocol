package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.AddPlayerPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddPlayerSerializer_v332 implements PacketSerializer<AddPlayerPacket> {
    public static final AddPlayerSerializer_v332 INSTANCE = new AddPlayerSerializer_v332();

    @Override
    public void serialize(ByteBuf buffer, AddPlayerPacket packet) {
        BedrockUtils.writeUuid(buffer, packet.getUuid());
        BedrockUtils.writeString(buffer, packet.getUsername());
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        BedrockUtils.writeString(buffer, packet.getPlatformChatId());
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
        BedrockUtils.writeVector3f(buffer, packet.getMotion());
        BedrockUtils.writeVector3f(buffer, packet.getRotation());
        BedrockUtils.writeItemInstance(buffer, packet.getHand());
        BedrockUtils.writeMetadata(buffer, packet.getMetadata());
        // Adventure Settings start
        VarInts.writeUnsignedInt(buffer, packet.getPlayerFlags());
        VarInts.writeUnsignedInt(buffer, packet.getCommandPermission());
        VarInts.writeUnsignedInt(buffer, packet.getWorldFlags());
        VarInts.writeUnsignedInt(buffer, packet.getPlayerPermission());
        VarInts.writeUnsignedInt(buffer, packet.getCustomFlags());
        buffer.writeLongLE(packet.getUniqueEntityId());
        // Adventure Settings end
        BedrockUtils.writeArray(buffer, packet.getEntityLinks(), BedrockUtils::writeEntityLink);
        BedrockUtils.writeString(buffer, packet.getDeviceId());
    }

    @Override
    public void deserialize(ByteBuf buffer, AddPlayerPacket packet) {
        packet.setUuid(BedrockUtils.readUuid(buffer));
        packet.setUsername(BedrockUtils.readString(buffer));
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPlatformChatId(BedrockUtils.readString(buffer));
        packet.setPosition(BedrockUtils.readVector3f(buffer));
        packet.setMotion(BedrockUtils.readVector3f(buffer));
        packet.setRotation(BedrockUtils.readVector3f(buffer));
        packet.setHand(BedrockUtils.readItemInstance(buffer));
        BedrockUtils.readMetadata(buffer, packet.getMetadata());
        // Adventure Settings start
        packet.setPlayerFlags(VarInts.readUnsignedInt(buffer));
        packet.setCommandPermission(VarInts.readUnsignedInt(buffer));
        packet.setWorldFlags(VarInts.readUnsignedInt(buffer));
        packet.setPlayerPermission(VarInts.readUnsignedInt(buffer));
        packet.setCustomFlags(VarInts.readUnsignedInt(buffer));
        buffer.readLongLE(); // Ignore
        // Adventure settings end
        BedrockUtils.readArray(buffer, packet.getEntityLinks(), BedrockUtils::readEntityLink);
        packet.setDeviceId(BedrockUtils.readString(buffer));
    }
}
