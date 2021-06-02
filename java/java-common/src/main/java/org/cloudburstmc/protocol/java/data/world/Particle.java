package org.cloudburstmc.protocol.java.data.world;

import lombok.Value;

import javax.annotation.Nullable;

@Value
public class Particle {
    ParticleType type;
    @Nullable Object data;
}
