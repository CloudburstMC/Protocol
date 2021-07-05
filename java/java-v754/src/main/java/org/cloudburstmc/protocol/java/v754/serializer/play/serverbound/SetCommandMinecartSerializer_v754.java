package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.serverbound.SetCommandMinecartPacket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetCommandMinecartSerializer_v754 implements JavaPacketSerializer<SetCommandMinecartPacket> {
    public static final SetCommandMinecartSerializer_v754 INSTANCE = new SetCommandMinecartSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetCommandMinecartPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getEntityId());
        helper.writeString(buffer, packet.getCommand());
        buffer.writeBoolean(packet.isTrackOutput());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetCommandMinecartPacket packet) throws PacketSerializeException {
        packet.setEntityId(helper.readVarInt(buffer));
        packet.setCommand(helper.readString(buffer));
        packet.setTrackOutput(buffer.readBoolean());
    }
}
