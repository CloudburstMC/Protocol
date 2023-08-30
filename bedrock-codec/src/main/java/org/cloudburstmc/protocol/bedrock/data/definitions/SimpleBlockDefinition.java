package org.cloudburstmc.protocol.bedrock.data.definitions;

import lombok.Data;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.common.NamedDefinition;

import java.util.TreeMap;

@Data
public class SimpleBlockDefinition implements BlockDefinition, NamedDefinition {
    private final String identifier;
    private final int runtimeId;
    private final NbtMap state;

    // Cache identifier as this implementation is immutable
    private transient String persistentIdentifier;

    public String getPersistentIdentifier() {
        if (this.persistentIdentifier == null) {
            // This is not most performant solution,
            // but will make sure the identifier is always equal for the block state
            StringBuilder builder = new StringBuilder(this.getIdentifier());
            if (!this.getState().isEmpty()) {
                TreeMap<String, String> properties = new TreeMap<>();
                NbtMap states = getState().getCompound("states");
                for (String stateName : states.keySet()) {
                    String value = states.get(stateName).toString();
                    properties.put(stateName, value);
                }
                properties.forEach((name, state) -> builder.append("|").append(name).append("=").append(state));
            }
            this.persistentIdentifier = builder.toString();
        }
        return this.persistentIdentifier;
    }
}
