package com.nukkitx.protocol.bedrock.v534.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.packet.AddPlayerPacket;
import com.nukkitx.protocol.bedrock.v503.serializer.AddPlayerSerializer_v503;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddPlayerSerializer_v534 extends AddPlayerSerializer_v503 {
    public static final AddPlayerSerializer_v534 INSTANCE = new AddPlayerSerializer_v534();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AddPlayerPacket packet, BedrockSession session) {
        helper.writeUuid(buffer, packet.getUuid());
        helper.writeString(buffer, packet.getUsername());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeString(buffer, packet.getPlatformChatId());
        helper.writeVector3f(buffer, packet.getPosition());
        helper.writeVector3f(buffer, packet.getMotion());
        helper.writeVector3f(buffer, packet.getRotation());
        helper.writeItem(buffer, packet.getHand(), session);
        VarInts.writeInt(buffer, packet.getGameType().ordinal());
        helper.writeEntityData(buffer, packet.getMetadata());
        UpdateAbilitiesSerializer_v534.INSTANCE.writePlayerAbilities(buffer, helper, packet);
        helper.writeArray(buffer, packet.getEntityLinks(), helper::writeEntityLink);
        helper.writeString(buffer, packet.getDeviceId());
        buffer.writeIntLE(packet.getBuildPlatform());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AddPlayerPacket packet, BedrockSession session) {
        packet.setUuid(helper.readUuid(buffer));
        packet.setUsername(helper.readString(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPlatformChatId(helper.readString(buffer));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setMotion(helper.readVector3f(buffer));
        packet.setRotation(helper.readVector3f(buffer));
        packet.setHand(helper.readItem(buffer, session));
        packet.setGameType(VALUES[VarInts.readInt(buffer)]);
        helper.readEntityData(buffer, packet.getMetadata());
        UpdateAbilitiesSerializer_v534.INSTANCE.readPlayerAbilities(buffer, helper, packet);
        helper.readArray(buffer, packet.getEntityLinks(), helper::readEntityLink);
        packet.setDeviceId(helper.readString(buffer));
        packet.setBuildPlatform(buffer.readIntLE());
    }
}
