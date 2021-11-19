package org.cloudburstmc.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.HeightMapDataType;
import org.cloudburstmc.protocol.bedrock.data.SubChunkRequestResult;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class SubChunkPacket extends AbstractReferenceCounted implements BedrockPacket {
    private int dimension;
    private Vector3i subChunkPosition;
    private ByteBuf data;
    private SubChunkRequestResult result;
    private HeightMapDataType heightMapType;
    private ByteBuf heightMapData;
    private boolean cacheEnabled;
    private long blobId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SUB_CHUNK;
    }

    @Override
    public SubChunkPacket touch(Object o) {
        this.data.touch(o);
        this.heightMapData.touch(o);
        return this;
    }

    @Override
    protected void deallocate() {
        this.data.release();
        this.heightMapData.release();
    }
}
