package org.cloudburstmc.protocol.common.serializer;

import io.netty.buffer.ByteBuf;

public interface PacketSerializer<P, H> {

    void serialize(ByteBuf buffer, H helper, P packet);

    void deserialize(ByteBuf buffer, H helper, P packet);
}
