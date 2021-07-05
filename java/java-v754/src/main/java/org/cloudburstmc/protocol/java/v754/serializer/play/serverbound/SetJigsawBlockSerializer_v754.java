package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.serverbound.SetJigsawBlockPacket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetJigsawBlockSerializer_v754 implements JavaPacketSerializer<SetJigsawBlockPacket> {
    public static final SetJigsawBlockSerializer_v754 INSTANCE = new SetJigsawBlockSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetJigsawBlockPacket packet) throws PacketSerializeException {
        helper.writeBlockPosition(buffer, packet.getPosition());
        helper.writeKey(buffer, packet.getName());
        helper.writeKey(buffer, packet.getTarget());
        helper.writeKey(buffer, packet.getPool());
        helper.writeString(buffer, packet.getFinalState());
        helper.writeString(buffer, packet.getJointType());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetJigsawBlockPacket packet) throws PacketSerializeException {
        packet.setPosition(helper.readBlockPosition(buffer));
        packet.setName(helper.readKey(buffer));
        packet.setTarget(helper.readKey(buffer));
        packet.setPool(helper.readKey(buffer));
        packet.setFinalState(helper.readString(buffer));
        packet.setJointType(helper.readString(buffer));
    }
}
