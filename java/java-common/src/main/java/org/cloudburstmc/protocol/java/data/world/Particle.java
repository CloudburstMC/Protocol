package org.cloudburstmc.protocol.java.data.world;

import lombok.Data;

@Data
public class Particle {
    private ParticleType type;
    private Object data;

    @SuppressWarnings("unchecked")
    public <T extends Particle> T as() {
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <T extends Particle> T as(Class<T> particleType) {
        return (T) this;
    }
}
