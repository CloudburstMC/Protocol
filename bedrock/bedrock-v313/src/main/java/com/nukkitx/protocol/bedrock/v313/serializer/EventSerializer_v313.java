package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.EventPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.network.VarInts.readInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventSerializer_v313 implements PacketSerializer<EventPacket> {
    public static final EventSerializer_v313 INSTANCE = new EventSerializer_v313();
    private static final InternalLogger log = InternalLoggerFactory.getInstance(EventSerializer_v313.class);

    @Override
    public void serialize(ByteBuf buffer, EventPacket packet) {
        //TODO
    }

    @Override
    public void deserialize(ByteBuf buffer, EventPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setData(readInt(buffer));
        packet.setType(buffer.readUnsignedByte());

        // TODO: 08/12/2018 More research into this packet
        /*switch (packet.getType()) {
            case 0:
                packet.setId(VarInts.readInt(buffer));
                break;
            case 1:
                packet.setCause(VarInts.readInt(buffer));
                packet.setId(VarInts.readInt(buffer));
                packet.setUnknown0(VarInts.readInt(buffer));
                packet.setUnknown1(buffer.readShortLE());
                break;
            case 2:
                packet.setId(VarInts.readInt(buffer));
                break;
            case 3:
                packet.setId(VarInts.readInt(buffer));
                packet.setCause(VarInts.readInt(buffer));
                break;
            case 4:
                packet.setUnknownEid(VarInts.readLong(buffer));
                packet.setMobEntityId(VarInts.readLong(buffer));
                packet.setCause(VarInts.readInt(buffer));
                break;
            case 5:
                packet.setCause(VarInts.readUnsignedInt(buffer));
                packet.setId(VarInts.readInt(buffer));
                packet.setUnknown0(VarInts.readInt(buffer));
                break;
            case 6:
                packet.setId(VarInts.readInt(buffer));
                packet.setCause(VarInts.readInt(buffer));
                break;
            case 7:
                packet.setMobEntityId(VarInts.readLong(buffer));
                packet.setId(VarInts.readInt(buffer));
                packet.setCause(VarInts.readInt(buffer));
                break;
            case 8:
                packet.setId(VarInts.readInt(buffer));
                packet.setCause(VarInts.readInt(buffer));
                packet.setUnknown2(BedrockUtils.readString(buffer));
                packet.setUnknown3(BedrockUtils.readString(buffer));
                packet.setUnknown4(BedrockUtils.readString(buffer));
                break;
            case 10:
                packet.setId(VarInts.readInt(buffer));
                packet.setCause(VarInts.readInt(buffer));
                packet.setUnknown0(VarInts.readInt(buffer));
                packet.setUnknown5(VarInts.readInt(buffer));
                packet.setUnknown6(VarInts.readInt(buffer));
                break;
            case 11:
                packet.setId(VarInts.readInt(buffer));
                packet.setCause(VarInts.readInt(buffer));
                packet.setUnknown2(BedrockUtils.readString(buffer));
                packet.setUnknown7(BedrockUtils.readString(buffer));
                break;
            case 12:
                packet.setId(VarInts.readInt(buffer));
                packet.setCause(VarInts.readInt(buffer));
                packet.setUnknown0(VarInts.readInt(buffer));
                packet.setUnknown8(buffer.readBoolean());
                break;
        }*/
    }
}
