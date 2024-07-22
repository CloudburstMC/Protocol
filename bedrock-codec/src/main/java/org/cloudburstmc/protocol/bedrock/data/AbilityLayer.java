package org.cloudburstmc.protocol.bedrock.data;

import lombok.Data;

import java.util.EnumSet;
import java.util.Set;

@Data
public class AbilityLayer {
    private Type layerType;
    private final Set<Ability> abilitiesSet = EnumSet.noneOf(Ability.class);
    private final Set<Ability> abilityValues = EnumSet.noneOf(Ability.class);
    private float flySpeed;
    private float walkSpeed;

    public enum Type {
        CACHE,
        BASE,
        SPECTATOR,
        COMMANDS,
        /**
         * @since v557
         */
        EDITOR,
        /**
         * @since v712
         */
        LOADING_SCREEN
    }
}
