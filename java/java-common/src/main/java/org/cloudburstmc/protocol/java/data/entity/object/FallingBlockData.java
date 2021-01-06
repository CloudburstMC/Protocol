package org.cloudburstmc.protocol.java.data.entity.object;

import lombok.Value;

@Value
public class FallingBlockData implements ObjectData {
    int id;
    int metadata;
}
