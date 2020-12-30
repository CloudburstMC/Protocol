package org.cloudburstmc.protocol.java;

import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;

public interface JavaPacketSerializer<T extends JavaPacket<?>> extends PacketSerializer<T, JavaPacketHelper> {

    @Override
    default void serialize(ByteBuf buffer, JavaPacketHelper helper, T packet){
    }

    default void serialize(ByteBuf buffer, JavaPacketHelper helper, T packet, JavaSession session){
        this.serialize(buffer, helper, packet);
    }

    @Override
    default void deserialize(ByteBuf buffer, JavaPacketHelper helper, T packet){
    }

    default void deserialize(ByteBuf buffer, JavaPacketHelper helper, T packet, JavaSession session){
        this.deserialize(buffer, helper, packet);
    }
}
