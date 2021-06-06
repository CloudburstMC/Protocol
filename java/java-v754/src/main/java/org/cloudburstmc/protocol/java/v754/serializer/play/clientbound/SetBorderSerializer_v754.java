package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.SetBorderPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetBorderSerializer_v754 implements JavaPacketSerializer<SetBorderPacket> {
    public static final SetBorderSerializer_v754 INSTANCE = new SetBorderSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetBorderPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getType().ordinal());
        switch (packet.getType()) {
            case SET_CENTER:
                buffer.writeDouble(packet.getSize());
                break;
            case LERP_SIZE:
                buffer.writeDouble(packet.getOldSize());
                buffer.writeDouble(packet.getSize());
                helper.writeVarLong(buffer, packet.getLerpTime());
                break;
            case SET_SIZE:
                buffer.writeDouble(packet.getCenterX());
                buffer.writeDouble(packet.getCenterX());
                break;
            case SET_WARNING_TIME:
                helper.writeVarInt(buffer, packet.getWarningTime());
                break;
            case SET_WARNING_BLOCKS:
                helper.writeVarInt(buffer, packet.getWarningBlocks());
                break;
            case INITIALIZE:
                buffer.writeDouble(packet.getCenterX());
                buffer.writeDouble(packet.getCenterZ());
                buffer.writeDouble(packet.getOldSize());
                buffer.writeDouble(packet.getSize());
                helper.writeVarLong(buffer, packet.getLerpTime());
                helper.writeVarInt(buffer, packet.getNewAbsoluteMaxSize());
                helper.writeVarInt(buffer, packet.getWarningBlocks());
                helper.writeVarInt(buffer, packet.getWarningTime());
                break;
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetBorderPacket packet) throws PacketSerializeException {
        packet.setType(SetBorderPacket.Type.getById(helper.readVarInt(buffer)));
        switch (packet.getType()) {
            case SET_CENTER:
                packet.setSize(buffer.readDouble());
                break;
            case LERP_SIZE:
                packet.setOldSize(buffer.readDouble());
                packet.setSize(buffer.readDouble());
                packet.setLerpTime(helper.readVarLong(buffer));
                break;
            case SET_SIZE:
                packet.setCenterX(buffer.readDouble());
                packet.setCenterZ(buffer.readDouble());
                break;
            case SET_WARNING_TIME:
                packet.setWarningTime(helper.readVarInt(buffer));
                break;
            case SET_WARNING_BLOCKS:
                packet.setWarningBlocks(helper.readVarInt(buffer));
                break;
            case INITIALIZE:
                packet.setCenterX(buffer.readDouble());
                packet.setCenterZ(buffer.readDouble());
                packet.setOldSize(buffer.readDouble());
                packet.setSize(buffer.readDouble());
                packet.setLerpTime(helper.readVarLong(buffer));
                packet.setNewAbsoluteMaxSize(helper.readVarInt(buffer));
                packet.setWarningBlocks(helper.readVarInt(buffer));
                packet.setWarningTime(helper.readVarInt(buffer));
        }
    }
}
