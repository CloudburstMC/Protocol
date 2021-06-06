package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.SetCameraPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetCameraSerializer_v754 implements JavaPacketSerializer<SetCameraPacket> {
    public static final SetCameraSerializer_v754 INSTANCE = new SetCameraSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetCameraPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getCameraId());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetCameraPacket packet) throws PacketSerializeException {
        packet.setCameraId(helper.readVarInt(buffer));
    }
}
