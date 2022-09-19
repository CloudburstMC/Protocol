package org.cloudburstmc.protocol.bedrock.codec.v534.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.AbilityLayer;
import org.cloudburstmc.protocol.bedrock.data.PlayerAbilityHolder;
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission;
import org.cloudburstmc.protocol.bedrock.data.command.CommandPermission;
import org.cloudburstmc.protocol.bedrock.packet.UpdateAbilitiesPacket;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAbilitiesSerializer_v534 implements BedrockPacketSerializer<UpdateAbilitiesPacket> {
    public static final UpdateAbilitiesSerializer_v534 INSTANCE = new UpdateAbilitiesSerializer_v534();

    private static final Ability[] VALID_FLAGS = {
            Ability.BUILD,
            Ability.MINE,
            Ability.DOORS_AND_SWITCHES,
            Ability.OPEN_CONTAINERS,
            Ability.ATTACK_PLAYERS,
            Ability.ATTACK_MOBS,
            Ability.OPERATOR_COMMANDS,
            Ability.TELEPORT,
            Ability.INVULNERABLE,
            Ability.FLYING,
            Ability.MAY_FLY,
            Ability.INSTABUILD,
            Ability.LIGHTNING,
            Ability.FLY_SPEED,
            Ability.WALK_SPEED,
            Ability.MUTED,
            Ability.WORLD_BUILDER,
            Ability.NO_CLIP
    };
    private static final Object2IntMap<Ability> FLAGS_TO_BITS = new Object2IntOpenHashMap<>();

    static {
        for (int i = 0; i < VALID_FLAGS.length; i++) {
            FLAGS_TO_BITS.put(VALID_FLAGS[i], (1 << i));
        }
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateAbilitiesPacket packet) {
        this.writePlayerAbilities(buffer, helper, packet);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateAbilitiesPacket packet) {
        this.readPlayerAbilities(buffer, helper, packet);
    }

    public void writePlayerAbilities(ByteBuf buffer, BedrockCodecHelper helper, PlayerAbilityHolder abilityHolder) {
        buffer.writeLongLE(abilityHolder.getUniqueEntityId());
        buffer.writeByte(abilityHolder.getPlayerPermission().ordinal());
        buffer.writeByte(abilityHolder.getCommandPermission().ordinal());
        helper.writeArray(buffer, abilityHolder.getAbilityLayers(), this::writeAbilityLayer);
    }

    public void readPlayerAbilities(ByteBuf buffer, BedrockCodecHelper helper, PlayerAbilityHolder abilityHolder) {
        abilityHolder.setUniqueEntityId(buffer.readLongLE());
        abilityHolder.setPlayerPermission(PlayerPermission.values()[buffer.readUnsignedByte()]);
        abilityHolder.setCommandPermission(CommandPermission.values()[buffer.readUnsignedByte()]);
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
        abilityLayer.setLayerType(AbilityLayer.Type.values()[buffer.readUnsignedShortLE()]);
        readAbilitiesFromNumber(buffer.readIntLE(), abilityLayer.getAbilitiesSet());
        readAbilitiesFromNumber(buffer.readIntLE(), abilityLayer.getAbilityValues());
        abilityLayer.setFlySpeed(buffer.readFloatLE());
        abilityLayer.setWalkSpeed(buffer.readFloatLE());
        return abilityLayer;
    }
    
    private static int getAbilitiesNumber(Set<Ability> abilities) {
        int number = 0;
        for (Ability ability : abilities) {
            number |= FLAGS_TO_BITS.getInt(ability);
        }
        return number;
    }
    
    private static void readAbilitiesFromNumber(int number, Set<Ability> abilities) {
        FLAGS_TO_BITS.forEach((ability, index) -> {
            if ((number & index) != 0) {
                abilities.add(ability);
            }
        });
    }
}
