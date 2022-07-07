package org.cloudburstmc.protocol.bedrock.codec.v534;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v503.BedrockCodecHelper_v503;
import org.cloudburstmc.protocol.bedrock.data.AbilityLayer;
import org.cloudburstmc.protocol.bedrock.data.PlayerAbilityHolder;
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission;
import org.cloudburstmc.protocol.bedrock.data.command.CommandPermission;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

public class BedrockCodecHelper_v534 extends BedrockCodecHelper_v503 {

    public BedrockCodecHelper_v534(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes, TypeMap<StackRequestActionType> stackRequestActionTypes) {
        super(entityData, gameRulesTypes, stackRequestActionTypes);
    }

    @Override
    public void writePlayerAbilities(ByteBuf buffer, PlayerAbilityHolder abilityHolder) {
        buffer.writeLongLE(abilityHolder.getUniqueEntityId());
        VarInts.writeUnsignedInt(buffer, abilityHolder.getPlayerPermission().ordinal());
        VarInts.writeUnsignedInt(buffer, abilityHolder.getCommandPermission().ordinal());
        writeArray(buffer, abilityHolder.getAbilityLayers(), (buf, abilityLayer) -> {
            buf.writeShortLE(abilityLayer.getLayerType().ordinal());
            buf.writeIntLE(abilityLayer.getAbilitiesSet());
            buf.writeIntLE(abilityLayer.getAbilityValues());
            buffer.writeFloatLE(abilityLayer.getFlySpeed());
            buffer.writeFloatLE(abilityLayer.getWalkSpeed());
        });
    }

    @Override
    public void readPlayerAbilities(ByteBuf buffer, PlayerAbilityHolder abilityHolder) {
        abilityHolder.setUniqueEntityId(buffer.readLongLE());
        abilityHolder.setPlayerPermission(PlayerPermission.values()[VarInts.readUnsignedInt(buffer)]);
        abilityHolder.setCommandPermission(CommandPermission.values()[VarInts.readUnsignedInt(buffer)]);
        readArray(buffer, abilityHolder.getAbilityLayers(), buf -> {
            AbilityLayer abilityLayer = new AbilityLayer();
            abilityLayer.setLayerType(AbilityLayer.Type.values()[buf.readShortLE()]);
            abilityLayer.setAbilitiesSet(buf.readIntLE());
            abilityLayer.setAbilityValues(buf.readIntLE());
            abilityLayer.setFlySpeed(buf.readFloatLE());
            abilityLayer.setWalkSpeed(buf.readFloatLE());
            return abilityLayer;
        });
    }
}
