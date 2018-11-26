package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.EventPacket;
import io.netty.buffer.ByteBuf;

import static com.nukkitx.network.VarInts.readInt;

public class EventPacket_v291 extends EventPacket {

    @Override
    public void encode(ByteBuf buffer) {
    }

    @Override
    public void decode(ByteBuf buffer) {
        uniqueEntityId = VarInts.readLong(buffer);
        data = readInt(buffer);
        type = buffer.readByte();

        switch (type) {
            case 0:
                //acheivementId = VarInts.readInt(buffer);
                break;
            case 1:
                // TODO:
        }
    }
}
