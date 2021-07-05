package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.serverbound.SetStructureBlockPacket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetStructureBlockSerializer_v754 implements JavaPacketSerializer<SetStructureBlockPacket> {
    public static final SetStructureBlockSerializer_v754 INSTANCE = new SetStructureBlockSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetStructureBlockPacket packet) throws PacketSerializeException {
        helper.writeBlockPosition(buffer, packet.getPosition());
        helper.writeVarInt(buffer, packet.getUpdateType().ordinal());
        helper.writeVarInt(buffer, packet.getStructureMode().ordinal());
        helper.writeString(buffer, packet.getName());
        buffer.writeByte(packet.getOffset().getX());
        buffer.writeByte(packet.getOffset().getY());
        buffer.writeByte(packet.getOffset().getZ());
        buffer.writeByte(packet.getSize().getX());
        buffer.writeByte(packet.getSize().getY());
        buffer.writeByte(packet.getSize().getZ());
        helper.writeVarInt(buffer, packet.getStructureMirror().ordinal());
        helper.writeVarInt(buffer, packet.getStructureRotation().ordinal());
        helper.writeString(buffer, packet.getData());
        buffer.writeFloat(packet.getIntegrity());
        helper.writeVarLong(buffer, packet.getSeed());
        byte flags = 0;
        if (packet.isIgnoreEntities()) {
            flags |= 0x01;
        }
        if (packet.isShowAir()) {
            flags |= 0x02;
        }
        if (packet.isShowBoundingBox()) {
            flags |= 0x04;
        }
        buffer.writeByte(flags);
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetStructureBlockPacket packet) throws PacketSerializeException {
        packet.setPosition(helper.readBlockPosition(buffer));
        packet.setUpdateType(SetStructureBlockPacket.UpdateType.getById(helper.readVarInt(buffer)));
        packet.setStructureMode(SetStructureBlockPacket.StructureMode.getById(helper.readVarInt(buffer)));
        packet.setName(helper.readString(buffer));
        packet.setOffset(Vector3i.from(buffer.readByte(), buffer.readByte(), buffer.readByte()));
        packet.setSize(Vector3i.from(buffer.readByte(), buffer.readByte(), buffer.readByte()));
        packet.setStructureMirror(SetStructureBlockPacket.Mirror.getById(helper.readVarInt(buffer)));
        packet.setStructureRotation(SetStructureBlockPacket.Rotation.getById(helper.readVarInt(buffer)));
        packet.setData(helper.readString(buffer));
        packet.setIntegrity(buffer.readFloat());
        packet.setSeed(helper.readVarLong(buffer));
        byte flags = buffer.readByte();

        packet.setIgnoreEntities((flags & 0x01) == 0x01);
        packet.setShowAir((flags & 0x02) == 0x02);
        packet.setShowBoundingBox((flags & 0x04) == 0x04);

    }
}
