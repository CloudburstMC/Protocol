package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.math.vector.Vector3i;
import lombok.Data;

@Data
public class SubChunkData {
    private Vector3i position;
    private byte[] data;
    private SubChunkRequestResult result;
    private HeightMapDataType heightMapType;
    private byte[] heightMapData;
    private boolean cacheEnabled;
    private long blobId;
}
