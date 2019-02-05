package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.MovePlayerPacket.Mode;
import static com.nukkitx.protocol.bedrock.packet.MovePlayerPacket.TeleportationCause;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MovePlayerSerializer_v332 implements PacketSerializer<MovePlayerPacket> {
    public static final MovePlayerSerializer_v332 INSTANCE = new MovePlayerSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, MovePlayerPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
        BedrockUtils.writeVector3f(buffer, packet.getRotation());
        buffer.writeByte(packet.getMode().ordinal());
        buffer.writeBoolean(packet.isOnGround());
        VarInts.writeUnsignedLong(buffer, packet.getRidingRuntimeEntityId());
        if (packet.getMode() == Mode.TELEPORT) {
            buffer.writeIntLE(packet.getTeleportationCause().ordinal());
            buffer.writeIntLE(packet.getUnknown0());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, MovePlayerPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPosition(BedrockUtils.readVector3f(buffer));
        packet.setRotation(BedrockUtils.readVector3f(buffer));
        packet.setMode(Mode.values()[buffer.readByte()]);
        packet.setOnGround(buffer.readBoolean());
        packet.setRidingRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        if (packet.getMode() == Mode.TELEPORT) {
            packet.setTeleportationCause(TeleportationCause.values()[buffer.readIntLE()]);
            packet.setUnknown0(buffer.readIntLE());
        }
    }
}
