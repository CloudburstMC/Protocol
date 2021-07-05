package org.cloudburstmc.protocol.java.v754.serializer.play;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.BidirectionalJavaPacketSerializer;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.packet.play.ResourcePackPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePackSerializer_v754 extends BidirectionalJavaPacketSerializer<ResourcePackPacket> {
    public static final ResourcePackSerializer_v754 INSTANCE = new ResourcePackSerializer_v754();

    @Override
    public void serializeClientbound(ByteBuf buffer, JavaPacketHelper helper, ResourcePackPacket packet) throws PacketSerializeException {
        helper.writeString(buffer, packet.getUrl());
        helper.writeString(buffer, packet.getHash());
    }

    @Override
    public void deserializeClientbound(ByteBuf buffer, JavaPacketHelper helper, ResourcePackPacket packet) throws PacketSerializeException {
        packet.setUrl(helper.readString(buffer));
        packet.setHash(helper.readString(buffer));
    }

    @Override
    public void serializeServerbound(ByteBuf buffer, JavaPacketHelper helper, ResourcePackPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getAction().ordinal());
    }

    @Override
    public void deserializeServerbound(ByteBuf buffer, JavaPacketHelper helper, ResourcePackPacket packet) throws PacketSerializeException {
        packet.setAction(ResourcePackPacket.Action.getById(helper.readVarInt(buffer)));
    }
}
