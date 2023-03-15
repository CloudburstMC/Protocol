package com.nukkitx.protocol.bedrock.v534;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.PlayerAbilityHolder;
import com.nukkitx.protocol.bedrock.data.Ability;
import com.nukkitx.protocol.bedrock.data.AbilityLayer;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.command.CommandPermission;
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType;
import com.nukkitx.protocol.bedrock.v527.BedrockPacketHelper_v527;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.util.Set;

public class BedrockPacketHelper_v534 extends BedrockPacketHelper_v527 {
    public static final BedrockPacketHelper_v534 INSTANCE = new BedrockPacketHelper_v534();

    protected final Int2ObjectMap<Ability> playerAbilities = new Int2ObjectOpenHashMap<>();
    private final Object2IntMap<Ability> abilityFlagsToBits;

    public BedrockPacketHelper_v534() {
        this.registerAbilities();
        Object2IntMap<Ability> flags = new Object2IntOpenHashMap<>();
        playerAbilities.forEach((index, flag) -> flags.put(flag, 1 << index));
        this.abilityFlagsToBits = Object2IntMaps.unmodifiable(flags);
    }

    @Override
    public void writePlayerAbilities(ByteBuf buffer, BedrockPacketHelper helper, PlayerAbilityHolder abilityHolder) {
        buffer.writeLongLE(abilityHolder.getUniqueEntityId());
        VarInts.writeUnsignedInt(buffer, abilityHolder.getPlayerPermission().ordinal());
        VarInts.writeUnsignedInt(buffer, abilityHolder.getCommandPermission().ordinal());
        helper.writeArray(buffer, abilityHolder.getAbilityLayers(), this::writeAbilityLayer);
    }

    @Override
    public void readPlayerAbilities(ByteBuf buffer, BedrockPacketHelper helper, PlayerAbilityHolder abilityHolder) {
        abilityHolder.setUniqueEntityId(buffer.readLongLE());
        abilityHolder.setPlayerPermission(PlayerPermission.values()[VarInts.readUnsignedInt(buffer)]);
        abilityHolder.setCommandPermission(CommandPermission.values()[VarInts.readUnsignedInt(buffer)]);
        helper.readArray(buffer, abilityHolder.getAbilityLayers(), this::readAbilityLayer);
    }

    private void writeAbilityLayer(ByteBuf buffer, AbilityLayer abilityLayer) {
        buffer.writeShortLE(abilityLayer.getLayerType().ordinal());
        buffer.writeIntLE(getAbilitiesNumber(abilityLayer.getAbilitiesSet()));
        buffer.writeIntLE(getAbilitiesNumber(abilityLayer.getAbilityValues()));
        buffer.writeFloatLE(abilityLayer.getFlySpeed());
        buffer.writeFloatLE(abilityLayer.getWalkSpeed());
    }

    private AbilityLayer readAbilityLayer(ByteBuf buffer) {
        AbilityLayer abilityLayer = new AbilityLayer();
        abilityLayer.setLayerType(AbilityLayer.Type.values()[buffer.readShortLE()]);
        readAbilitiesFromNumber(buffer.readIntLE(), abilityLayer.getAbilitiesSet());
        readAbilitiesFromNumber(buffer.readIntLE(), abilityLayer.getAbilityValues());
        abilityLayer.setFlySpeed(buffer.readFloatLE());
        abilityLayer.setWalkSpeed(buffer.readFloatLE());
        return abilityLayer;
    }

    private int getAbilitiesNumber(Set<Ability> abilities) {
        int number = 0;
        for (Ability ability : abilities) {
            number |= abilityFlagsToBits.getInt(ability);
        }
        return number;
    }

    private void readAbilitiesFromNumber(int number, Set<Ability> abilities) {
        playerAbilities.forEach((index, ability) -> {
            if ((number & index) != 0) {
                abilities.add(ability);
            }
        });
    }

    @Override
    protected void registerEntityEvents() {
        super.registerEntityEvents();
        this.addEntityEvent(78, EntityEventType.DRINK_MILK);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(432, SoundEvent.MILK_DRINK);
        this.addSoundEvent(441, SoundEvent.RECORD_PLAYING);
        this.addSoundEvent(442, SoundEvent.UNDEFINED);
    }

    protected void registerAbilities() {
        this.playerAbilities.put(0, Ability.BUILD);
        this.playerAbilities.put(1, Ability.MINE);
        this.playerAbilities.put(2, Ability.DOORS_AND_SWITCHES);
        this.playerAbilities.put(3, Ability.OPEN_CONTAINERS);
        this.playerAbilities.put(4, Ability.ATTACK_PLAYERS);
        this.playerAbilities.put(5, Ability.ATTACK_MOBS);
        this.playerAbilities.put(6, Ability.OPERATOR_COMMANDS);
        this.playerAbilities.put(7, Ability.TELEPORT);
        this.playerAbilities.put(8, Ability.INVULNERABLE);
        this.playerAbilities.put(9, Ability.FLYING);
        this.playerAbilities.put(10, Ability.MAY_FLY);
        this.playerAbilities.put(11, Ability.INSTABUILD);
        this.playerAbilities.put(12, Ability.LIGHTNING);
        this.playerAbilities.put(13, Ability.FLY_SPEED);
        this.playerAbilities.put(14, Ability.WALK_SPEED);
        this.playerAbilities.put(15, Ability.MUTED);
        this.playerAbilities.put(16, Ability.WORLD_BUILDER);
        this.playerAbilities.put(17, Ability.NO_CLIP);
    }
}
