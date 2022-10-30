package org.cloudburstmc.protocol.bedrock.codec.v544.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector2i;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.NetworkChunkPublisherUpdateSerializer_v313;
import org.cloudburstmc.protocol.bedrock.packet.NetworkChunkPublisherUpdatePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class NetworkChunkPublisherUpdateSerializer_v544 extends NetworkChunkPublisherUpdateSerializer_v313 {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, NetworkChunkPublisherUpdatePacket packet) {
        super.serialize(buffer, helper, packet);

        helper.writeArray(buffer, packet.getSavedChunks(), ByteBuf::writeIntLE, this::writeSavedChunk);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, NetworkChunkPublisherUpdatePacket packet) {
        super.deserialize(buffer, helper, packet);

        helper.readArray(buffer, packet.getSavedChunks(), ByteBuf::readIntLE, this::readSavedChunk);
    }

    protected void writeSavedChunk(ByteBuf buffer, BedrockCodecHelper helper, Vector2i savedChunk) {
        VarInts.writeInt(buffer, savedChunk.getX());
        VarInts.writeInt(buffer, savedChunk.getY());
    }

    protected Vector2i readSavedChunk(ByteBuf buffer, BedrockCodecHelper helper) {
        return Vector2i.from(VarInts.readInt(buffer), VarInts.readInt(buffer));
    }
}
