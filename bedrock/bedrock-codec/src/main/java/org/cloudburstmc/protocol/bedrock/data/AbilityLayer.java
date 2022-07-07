package org.cloudburstmc.protocol.bedrock.data;

import lombok.Data;

@Data
public class AbilityLayer {
    private Type layerType;
    private int abilitiesSet;
    private int abilityValues;
    private float flySpeed;
    private float walkSpeed;

    public enum Type {
        CACHE,
        BASE,
        SPECTATOR,
        COMMANDS
    }
}
