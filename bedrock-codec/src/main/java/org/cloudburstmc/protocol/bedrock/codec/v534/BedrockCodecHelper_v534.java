package org.cloudburstmc.protocol.bedrock.codec.v534;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v503.BedrockCodecHelper_v503;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.AbilityLayer;
import org.cloudburstmc.protocol.bedrock.data.PlayerAbilityHolder;
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission;
import org.cloudburstmc.protocol.bedrock.data.command.CommandPermission;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.common.util.TypeMap;

import java.util.Set;

public class BedrockCodecHelper_v534 extends BedrockCodecHelper_v503 {

    private final TypeMap<Ability> abilities;
    private final Object2IntMap<Ability> abilityFlagsToBits;

    public BedrockCodecHelper_v534(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes,
                                   TypeMap<ItemStackRequestActionType> stackRequestActionTypes, TypeMap<ContainerSlotType> containerSlotTypes, TypeMap<Ability> abilities) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes);
        this.abilities = abilities;

        Object2IntMap<Ability> flags = new Object2IntOpenHashMap<>();
        abilities.forEach((index, flag) -> flags.put(flag, (1 << index)));
        this.abilityFlagsToBits = Object2IntMaps.unmodifiable(flags);
    }

    @Override
    public void readPlayerAbilities(ByteBuf buffer, PlayerAbilityHolder abilityHolder) {
        abilityHolder.setUniqueEntityId(buffer.readLongLE());
        abilityHolder.setPlayerPermission(PlayerPermission.values()[buffer.readUnsignedByte()]);
        abilityHolder.setCommandPermission(CommandPermission.values()[buffer.readUnsignedByte()]);
        this.readArray(buffer, abilityHolder.getAbilityLayers(), this::readAbilityLayer);
    }

    protected AbilityLayer readAbilityLayer(ByteBuf buffer) {
        AbilityLayer abilityLayer = new AbilityLayer();
        abilityLayer.setLayerType(AbilityLayer.Type.values()[buffer.readUnsignedShortLE()]);
        readAbilitiesFromNumber(buffer.readIntLE(), abilityLayer.getAbilitiesSet());
        readAbilitiesFromNumber(buffer.readIntLE(), abilityLayer.getAbilityValues());
        abilityLayer.setFlySpeed(buffer.readFloatLE());
        abilityLayer.setWalkSpeed(buffer.readFloatLE());
        return abilityLayer;
    }

    @Override
    public void writePlayerAbilities(ByteBuf buffer, PlayerAbilityHolder abilityHolder) {
        buffer.writeLongLE(abilityHolder.getUniqueEntityId());
        buffer.writeByte(abilityHolder.getPlayerPermission().ordinal());
        buffer.writeByte(abilityHolder.getCommandPermission().ordinal());
        this.writeArray(buffer, abilityHolder.getAbilityLayers(), this::writeAbilityLayer);
    }

    protected void writeAbilityLayer(ByteBuf buffer, AbilityLayer abilityLayer) {
        buffer.writeShortLE(abilityLayer.getLayerType().ordinal());
        buffer.writeIntLE(getAbilitiesNumber(abilityLayer.getAbilitiesSet()));
        buffer.writeIntLE(getAbilitiesNumber(abilityLayer.getAbilityValues()));
        buffer.writeFloatLE(abilityLayer.getFlySpeed());
        buffer.writeFloatLE(abilityLayer.getWalkSpeed());
    }

    protected int getAbilitiesNumber(Set<Ability> abilities) {
        int number = 0;
        for (Ability ability : abilities) {
            number |= this.abilityFlagsToBits.getInt(ability);
        }
        return number;
    }

    protected void readAbilitiesFromNumber(int number, Set<Ability> abilities) {
        this.abilityFlagsToBits.forEach((ability, index) -> {
            if ((number & index) != 0) {
                abilities.add(ability);
            }
        });
    }
}
