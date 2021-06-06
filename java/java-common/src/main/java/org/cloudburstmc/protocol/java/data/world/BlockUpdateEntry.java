package org.cloudburstmc.protocol.java.data.world;

import com.nukkitx.math.vector.Vector3i;
import lombok.Value;

@Value
public class BlockUpdateEntry {
    Vector3i position;
    int blockState;
}
