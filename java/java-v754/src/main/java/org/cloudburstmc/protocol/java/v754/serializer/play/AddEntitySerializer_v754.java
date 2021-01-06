package org.cloudburstmc.protocol.java.v754.serializer.play;

import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.entity.EntityType;
import org.cloudburstmc.protocol.java.data.entity.object.*;
import org.cloudburstmc.protocol.java.packet.play.AddEntityPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddEntitySerializer_v754 implements JavaPacketSerializer<AddEntityPacket> {
    public static final AddEntitySerializer_v754 INSTANCE = new AddEntitySerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, AddEntityPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getEntityId());
        helper.writeUUID(buffer, packet.getUuid());
        VarInts.writeUnsignedInt(buffer, helper.getEntityId(packet.getEntityType()));
        helper.writePosition(buffer, packet.getPosition());
        helper.writeRotation2f(buffer, packet.getRotation());

        int data = 0;
        if (packet.getObjectData() instanceof MinecartType) {
            data = ((MinecartType) packet.getObjectData()).ordinal();
        } else if (packet.getObjectData() instanceof HangingDirection) {
            data = ((HangingDirection) packet.getObjectData()).ordinal();
        } else if (packet.getObjectData() instanceof FallingBlockData) {
            data = ((FallingBlockData) packet.getObjectData()).getId() | ((FallingBlockData) packet.getObjectData()).getMetadata() << 16;
        } else if (packet.getObjectData() instanceof ProjectileData) {
            data = ((ProjectileData) packet.getObjectData()).getOwnerId();
        } else if (packet.getObjectData() instanceof GenericObjectData) {
            data = ((GenericObjectData) packet.getObjectData()).getValue();
        }

        buffer.writeInt(data);

        helper.writeVelocity(buffer, packet.getVelocity());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, AddEntityPacket packet) {
        packet.setEntityId(VarInts.readUnsignedInt(buffer));
        packet.setUuid(helper.readUUID(buffer));
        packet.setEntityType(helper.getEntityType(VarInts.readUnsignedInt(buffer)));
        packet.setPosition(helper.readPosition(buffer));
        packet.setRotation(helper.readRotation2f(buffer));

        int data = buffer.readInt();
        if (packet.getEntityType() == EntityType.MINECART) {
            packet.setObjectData(MinecartType.getById(data));
        } else if (packet.getEntityType() == EntityType.ITEM_FRAME) {
            packet.setObjectData(HangingDirection.getById(data));
        } else if (packet.getEntityType() == EntityType.FALLING_BLOCK) {
            packet.setObjectData(new FallingBlockData(data & 65535, data >> 16)); //I think this should be 12 but we'll go with it for now
        } else if (packet.getEntityType() == EntityType.FISHING_BOBBER || packet.getEntityType() == EntityType.ARROW
                || packet.getEntityType() == EntityType.SPECTRAL_ARROW || packet.getEntityType() == EntityType.FIREBALL
                || packet.getEntityType() == EntityType.SMALL_FIREBALL || packet.getEntityType() == EntityType.DRAGON_FIREBALL
                || packet.getEntityType() == EntityType.WITHER_SKULL) {
            packet.setObjectData(new ProjectileData(data));
        } else {
            packet.setObjectData(new GenericObjectData(data));
        }

        packet.setVelocity(helper.readVelocity(buffer));
    }
}
