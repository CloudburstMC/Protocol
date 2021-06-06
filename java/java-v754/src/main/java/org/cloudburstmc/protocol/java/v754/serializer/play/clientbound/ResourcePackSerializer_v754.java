package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.ResourcePackPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePackSerializer_v754 implements JavaPacketSerializer<ResourcePackPacket> {
    public static final ResourcePackSerializer_v754 INSTANCE = new ResourcePackSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, ResourcePackPacket packet) throws PacketSerializeException {
        helper.writeString(buffer, packet.getUrl());
        helper.writeString(buffer, packet.getHash());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, ResourcePackPacket packet) throws PacketSerializeException {
        packet.setUrl(helper.readString(buffer));
        packet.setHash(helper.readString(buffer));
    }
}
