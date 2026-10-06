package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v557.serializer.AddPlayerSerializer_v557;
import org.cloudburstmc.protocol.bedrock.data.BuildPlatform;
import org.cloudburstmc.protocol.bedrock.packet.AddPlayerPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class AddPlayerSerializer_v2225 extends AddPlayerSerializer_v557 {

    public static final AddPlayerSerializer_v2225 INSTANCE = new AddPlayerSerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AddPlayerPacket packet) {
        helper.writeUuid(buffer, packet.getUuid());
        helper.writeString(buffer, packet.getUsername());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeString(buffer, packet.getPlatformChatId());
        helper.writeVector3f(buffer, packet.getPosition());
        helper.writeVector3f(buffer, packet.getMotion());
        helper.writeVector3f(buffer, packet.getRotation());
        helper.writeItem(buffer, packet.getHand());
        VarInts.writeInt(buffer, packet.getGameType().ordinal());
        helper.writeEntityData(buffer, packet.getMetadata());
        helper.writeEntityProperties(buffer, packet.getProperties());
        helper.writePlayerAbilities(buffer, packet);
        helper.writeArray(buffer, packet.getEntityLinks(), helper::writeEntityLink);
        helper.writeOptionalNull(buffer, packet.getPassengerOfBlockArguments(), helper::writePassengerOfBlockArguments); //new
        helper.writeString(buffer, packet.getDeviceId());
        buffer.writeIntLE(packet.getBuildPlatform().getId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AddPlayerPacket packet) {
        packet.setUuid(helper.readUuid(buffer));
        packet.setUsername(helper.readString(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPlatformChatId(helper.readString(buffer));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setMotion(helper.readVector3f(buffer));
        packet.setRotation(helper.readVector3f(buffer));
        packet.setHand(helper.readItem(buffer));
        packet.setGameType(VALUES[VarInts.readInt(buffer)]);
        helper.readEntityData(buffer, packet.getMetadata());
        helper.readEntityProperties(buffer, packet.getProperties());
        helper.readPlayerAbilities(buffer, packet);
        helper.readArray(buffer, packet.getEntityLinks(), helper::readEntityLink);
        packet.setPassengerOfBlockArguments(helper.readOptional(buffer, null, helper::readPassengerOfBlockArguments)); //new
        packet.setDeviceId(helper.readString(buffer));
        packet.setBuildPlatform(BuildPlatform.from(buffer.readIntLE()));
    }
}
