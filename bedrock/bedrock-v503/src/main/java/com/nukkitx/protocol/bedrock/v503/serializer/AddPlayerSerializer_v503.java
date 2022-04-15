package com.nukkitx.protocol.bedrock.v503.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.GameType;
import com.nukkitx.protocol.bedrock.packet.AddPlayerPacket;
import com.nukkitx.protocol.bedrock.v291.serializer.AdventureSettingsSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddPlayerSerializer_v503 implements BedrockPacketSerializer<AddPlayerPacket> {
    public static final AddPlayerSerializer_v503 INSTANCE = new AddPlayerSerializer_v503();

    private static final GameType[] VALUES = GameType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AddPlayerPacket packet, BedrockSession session) {
        helper.writeUuid(buffer, packet.getUuid());
        helper.writeString(buffer, packet.getUsername());
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeString(buffer, packet.getPlatformChatId());
        helper.writeVector3f(buffer, packet.getPosition());
        helper.writeVector3f(buffer, packet.getMotion());
        helper.writeVector3f(buffer, packet.getRotation());
        helper.writeItem(buffer, packet.getHand(), session);
        VarInts.writeInt(buffer, packet.getGameType().ordinal());
        helper.writeEntityData(buffer, packet.getMetadata());
        AdventureSettingsSerializer_v291.INSTANCE.serialize(buffer, helper, packet.getAdventureSettings());
        helper.writeArray(buffer, packet.getEntityLinks(), helper::writeEntityLink);
        helper.writeString(buffer, packet.getDeviceId());
        buffer.writeIntLE(packet.getBuildPlatform());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AddPlayerPacket packet, BedrockSession session) {
        packet.setUuid(helper.readUuid(buffer));
        packet.setUsername(helper.readString(buffer));
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPlatformChatId(helper.readString(buffer));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setMotion(helper.readVector3f(buffer));
        packet.setRotation(helper.readVector3f(buffer));
        packet.setHand(helper.readItem(buffer, session));
        packet.setGameType(VALUES[VarInts.readInt(buffer)]);
        helper.readEntityData(buffer, packet.getMetadata());
        AdventureSettingsSerializer_v291.INSTANCE.deserialize(buffer, helper, packet.getAdventureSettings());
        helper.readArray(buffer, packet.getEntityLinks(), helper::readEntityLink);
        packet.setDeviceId(helper.readString(buffer));
        packet.setBuildPlatform(buffer.readIntLE());
    }
}