package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ContainerSetDataPacket;
import com.nukkitx.protocol.bedrock.util.TIntHashBiMap;
import io.netty.buffer.ByteBuf;

public class ContainerSetDataPacket_v291 extends ContainerSetDataPacket {
    private static final TIntHashBiMap<Property> properties = new TIntHashBiMap<>();

    static {
        properties.put(0, Property.FURNACE_TICK_COUNT);
        // TODO:
    }

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(windowId);
        VarInts.writeInt(buffer, properties.get(property));
        VarInts.writeInt(buffer, value);
    }

    @Override
    public void decode(ByteBuf buffer) {
        windowId = buffer.readByte();
        property = properties.get(VarInts.readInt(buffer));
        value = VarInts.readInt(buffer);
    }
}
