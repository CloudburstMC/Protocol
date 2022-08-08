package org.cloudburstmc.protocol.bedrock.data.defintions;

import com.nukkitx.nbt.NbtMap;
import org.cloudburstmc.protocol.common.Definition;

import java.util.TreeMap;

public abstract class BlockDefinition implements Definition {
    public abstract NbtMap getState();

    @Override
    public String getPersistentIdentifier() {
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
        return builder.toString();
    }
}