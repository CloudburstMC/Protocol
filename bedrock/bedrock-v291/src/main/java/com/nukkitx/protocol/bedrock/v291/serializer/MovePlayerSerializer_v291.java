package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.MovePlayerPacket.Mode;
import static com.nukkitx.protocol.bedrock.packet.MovePlayerPacket.TeleportationCause;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovePlayerSerializer_v291 implements BedrockPacketSerializer<MovePlayerPacket> {
    public static final MovePlayerSerializer_v291 INSTANCE = new MovePlayerSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MovePlayerPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeVector3f(buffer, packet.getPosition());
        helper.writeVector3f(buffer, packet.getRotation());
        buffer.writeByte(packet.getMode().ordinal());
        buffer.writeBoolean(packet.isOnGround());
        VarInts.writeUnsignedLong(buffer, packet.getRidingRuntimeEntityId());
        if (packet.getMode() == Mode.TELEPORT) {
            buffer.writeIntLE(packet.getTeleportationCause().ordinal());
            buffer.writeIntLE(packet.getEntityType());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MovePlayerPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setRotation(helper.readVector3f(buffer));
        packet.setMode(Mode.values()[buffer.readUnsignedByte()]);
        packet.setOnGround(buffer.readBoolean());
        packet.setRidingRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        if (packet.getMode() == Mode.TELEPORT) {
            packet.setTeleportationCause(TeleportationCause.byId(buffer.readIntLE()));
            packet.setEntityType(buffer.readIntLE());
        }
    }
}
