package com.nukkitx.protocol.bedrock.v414.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.MoveEntityDeltaPacket;
import com.nukkitx.protocol.bedrock.packet.MoveEntityDeltaPacket.Flag;
import com.nukkitx.protocol.bedrock.util.TriConsumer;
import com.nukkitx.protocol.bedrock.v388.serializer.MoveEntityDeltaSerializer_v388;
import io.netty.buffer.ByteBuf;

public class MoveEntityDeltaSerializer_v414 extends MoveEntityDeltaSerializer_v388 {

    protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> READER_X =
            (buffer, helper, packet) -> packet.setX(buffer.readFloatLE());
    protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> READER_Y =
            (buffer, helper, packet) -> packet.setY(buffer.readFloatLE());
    protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> READER_Z =
            (buffer, helper, packet) -> packet.setZ(buffer.readFloatLE());

    protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> WRITER_X =
            (buffer, helper, packet) -> buffer.writeFloatLE(packet.getX());
    protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> WRITER_Y =
            (buffer, helper, packet) -> buffer.writeFloatLE(packet.getY());
    protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> WRITER_Z =
            (buffer, helper, packet) -> buffer.writeFloatLE(packet.getZ());

    public static final MoveEntityDeltaSerializer_v414 INSTANCE = new MoveEntityDeltaSerializer_v414();

    protected MoveEntityDeltaSerializer_v414() {
        super();

        this.readers.put(Flag.HAS_X, READER_X);
        this.readers.put(Flag.HAS_Y, READER_Y);
        this.readers.put(Flag.HAS_Z, READER_Z);

        this.writers.put(Flag.HAS_X, WRITER_X);
        this.writers.put(Flag.HAS_Y, WRITER_Y);
        this.writers.put(Flag.HAS_Z, WRITER_Z);
    }
}
