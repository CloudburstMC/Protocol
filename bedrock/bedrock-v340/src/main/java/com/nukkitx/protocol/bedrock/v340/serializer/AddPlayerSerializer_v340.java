package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.AddPlayerPacket;
import com.nukkitx.protocol.bedrock.v340.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddPlayerSerializer_v340 implements PacketSerializer<AddPlayerPacket> {
    public static final AddPlayerSerializer_v340 INSTANCE = new AddPlayerSerializer_v340();

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
        BedrockUtils.writeItemData(buffer, packet.getHand());
        BedrockUtils.writeEntityData(buffer, packet.getMetadata());
        AdventureSettingsSerializer_v340.INSTANCE.serialize(buffer, packet.getAdventureSettings());
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
        packet.setHand(BedrockUtils.readItemData(buffer));
        BedrockUtils.readEntityData(buffer, packet.getMetadata());
        AdventureSettingsSerializer_v340.INSTANCE.deserialize(buffer, packet.getAdventureSettings());
        BedrockUtils.readArray(buffer, packet.getEntityLinks(), BedrockUtils::readEntityLink);
        packet.setDeviceId(BedrockUtils.readString(buffer));
    }
}
