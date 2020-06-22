package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.MoveEntityAbsolutePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoveEntityAbsoluteSerializer_v291 implements BedrockPacketSerializer<MoveEntityAbsolutePacket> {
    public static final MoveEntityAbsoluteSerializer_v291 INSTANCE = new MoveEntityAbsoluteSerializer_v291();

    private static final int FLAG_ON_GROUND = 0x1;
    private static final int FLAG_TELEPORTED = 0x2;

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MoveEntityAbsolutePacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        int flags = 0;
        if (packet.isOnGround()) {
            flags |= FLAG_ON_GROUND;
        }
        if (packet.isTeleported()) {
            flags |= FLAG_TELEPORTED;
        }
        buffer.writeByte(flags);
        helper.writeVector3f(buffer, packet.getPosition());
        helper.writeByteRotation(buffer, packet.getRotation());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MoveEntityAbsolutePacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        int flags = buffer.readUnsignedByte();
        packet.setOnGround((flags & FLAG_ON_GROUND) != 0);
        packet.setTeleported((flags & FLAG_TELEPORTED) != 0);
        packet.setPosition(helper.readVector3f(buffer));
        packet.setRotation(helper.readByteRotation(buffer));
    }
}
