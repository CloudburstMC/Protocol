package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.EventPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.network.VarInts.readInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventSerializer_v291 implements PacketSerializer<EventPacket> {
    public static final EventSerializer_v291 INSTANCE = new EventSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, EventPacket packet) {
        //TODO
    }

    @Override
    public void deserialize(ByteBuf buffer, EventPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setData(readInt(buffer));
        packet.setType(buffer.readByte());

        switch (packet.getType()) {
            case 0:
                //acheivementId = VarInts.readInt(buffer);
                break;
            case 1:
                // TODO:
        }
    }
}
