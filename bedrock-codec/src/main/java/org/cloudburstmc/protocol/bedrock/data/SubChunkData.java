package org.cloudburstmc.protocol.bedrock.data;

import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.math.vector.Vector3i;

@Data
@EqualsAndHashCode(callSuper = false)
public class SubChunkData extends AbstractReferenceCounted {
    private Vector3i position;
    private ByteBuf data;
    private SubChunkRequestResult result;
    private HeightMapDataType heightMapType;
    private ByteBuf heightMapData;
    private HeightMapDataType renderHeightMapType;
    private ByteBuf renderHeightMapData;
    private boolean cacheEnabled;
    private long blobId;

    @Override
    public SubChunkData touch(Object o) {
        if (this.data != null) {
            this.data.touch(o);
        }
        if (this.heightMapData != null) {
            this.heightMapData.touch(o);
        }
        return this;
    }

    @Override
    protected void deallocate() {
        if (this.data != null) {
            this.data.release();
        }
        if (this.heightMapData != null) {
            this.heightMapData.release();
        }
    }
}
