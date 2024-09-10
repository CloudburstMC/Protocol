package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UnlockedRecipesPacket implements BedrockPacket {
    private ActionType action;
    private final List<String> unlockedRecipes = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UNLOCKED_RECIPES;
    }

    public enum ActionType {
        EMPTY,
        INITIALLY_UNLOCKED,
        NEWLY_UNLOCKED,
        REMOVE_UNLOCKED,
        REMOVE_ALL
    }

    @Override
    public UnlockedRecipesPacket clone() {
        try {
            return (UnlockedRecipesPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

