package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.MoveEntityAbsolutePacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MoveEntityAbsoluteSerializer_v388 implements PacketSerializer<MoveEntityAbsolutePacket> {
    public static final MoveEntityAbsoluteSerializer_v388 INSTANCE = new MoveEntityAbsoluteSerializer_v388();

    private static final int FLAG_ON_GROUND = 0x1;
    private static final int FLAG_TELEPORTED = 0x2;

    @Override
    public void serialize(ByteBuf buffer, MoveEntityAbsolutePacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        int flags = 0;
        if (packet.isOnGround()) {
            flags |= FLAG_ON_GROUND;
        }
        if (packet.isTeleported()) {
            flags |= FLAG_TELEPORTED;
        }
        buffer.writeByte(flags);
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
        BedrockUtils.writeByteRotation(buffer, packet.getRotation());
    }

    @Override
    public void deserialize(ByteBuf buffer, MoveEntityAbsolutePacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        int flags = buffer.readUnsignedByte();
        packet.setOnGround((flags & FLAG_ON_GROUND) != 0);
        packet.setTeleported((flags & FLAG_TELEPORTED) != 0);
        packet.setPosition(BedrockUtils.readVector3f(buffer));
        packet.setRotation(BedrockUtils.readByteRotation(buffer));
    }
}
