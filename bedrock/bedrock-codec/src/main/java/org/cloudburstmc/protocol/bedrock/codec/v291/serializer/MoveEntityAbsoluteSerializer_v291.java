package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.util.Preconditions;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.MoveEntityAbsolutePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoveEntityAbsoluteSerializer_v291 implements BedrockPacketSerializer<MoveEntityAbsolutePacket> {
    public static final MoveEntityAbsoluteSerializer_v291 INSTANCE = new MoveEntityAbsoluteSerializer_v291();

    private static final int FLAG_ON_GROUND = 0x1;
    private static final int FLAG_TELEPORTED = 0x2;

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MoveEntityAbsolutePacket packet) {
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
        this.writeByteRotation(buffer, packet.getRotation());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MoveEntityAbsolutePacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        int flags = buffer.readUnsignedByte();
        packet.setOnGround((flags & FLAG_ON_GROUND) != 0);
        packet.setTeleported((flags & FLAG_TELEPORTED) != 0);
        packet.setPosition(helper.readVector3f(buffer));
        packet.setRotation(this.readByteRotation(buffer));
    }

    // Helpers

    protected Vector3f readByteRotation(ByteBuf buffer) {
        float pitch = readByteAngle(buffer);
        float yaw = readByteAngle(buffer);
        float roll = readByteAngle(buffer);
        return Vector3f.from(pitch, yaw, roll);
    }

    protected void writeByteRotation(ByteBuf buffer, Vector3f rotation) {
        Preconditions.checkNotNull(rotation, "rotation");
        writeByteAngle(buffer, rotation.getX());
        writeByteAngle(buffer, rotation.getY());
        writeByteAngle(buffer, rotation.getZ());
    }

    protected float readByteAngle(ByteBuf buffer) {
        return buffer.readByte() * (360f / 256f);
    }

    protected void writeByteAngle(ByteBuf buffer, float angle) {
        buffer.writeByte((byte) (angle / (360f / 256f)));
    }
}
