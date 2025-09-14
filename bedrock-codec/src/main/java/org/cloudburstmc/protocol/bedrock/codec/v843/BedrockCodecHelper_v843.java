package org.cloudburstmc.protocol.bedrock.codec.v843;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v776.BedrockCodecHelper_v776;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.GameRuleData;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.common.util.Preconditions;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

public class BedrockCodecHelper_v843 extends BedrockCodecHelper_v776 {

    public BedrockCodecHelper_v843(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes, TypeMap<ItemStackRequestActionType> stackRequestActionTypes,
                                   TypeMap<ContainerSlotType> containerSlotTypes, TypeMap<Ability> abilities, TypeMap<TextProcessingEventOrigin> textProcessingEventOrigins) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes, abilities, textProcessingEventOrigins);
    }

    @Override
    public void writeGameRule(ByteBuf buffer, GameRuleData<?> gameRule) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(gameRule, "gameRule");

        Object value = gameRule.getValue();
        int id = this.gameRuleType.getId(value.getClass());

        writeString(buffer, gameRule.getName());
        buffer.writeBoolean(gameRule.isEditable());
        VarInts.writeUnsignedInt(buffer, id);
        switch (id) {
            case 1:
                buffer.writeBoolean((boolean) value);
                break;
            case 2:
                buffer.writeIntLE((int) value);
                break;
            case 3:
                buffer.writeFloatLE((float) value);
                break;
        }
    }

    @Override
    public GameRuleData<?> readGameRule(ByteBuf buffer) {
        Preconditions.checkNotNull(buffer, "buffer");

        String name = readString(buffer);
        boolean editable = buffer.readBoolean();
        int type = VarInts.readUnsignedInt(buffer);

        switch (type) {
            case 1:
                return new GameRuleData<>(name, editable, buffer.readBoolean());
            case 2:
                return new GameRuleData<>(name, editable, buffer.readIntLE());
            case 3:
                return new GameRuleData<>(name, editable, buffer.readFloatLE());
        }
        throw new IllegalStateException("Invalid gamerule type received");
    }

    @Override
    public void writeGameRuleInStartGame(ByteBuf buffer, GameRuleData<?> gameRule) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(gameRule, "gameRule");

        Object value = gameRule.getValue();
        int id = this.gameRuleType.getId(value.getClass());

        writeString(buffer, gameRule.getName());
        buffer.writeBoolean(gameRule.isEditable());
        VarInts.writeUnsignedInt(buffer, id);
        switch (id) {
            case 1:
                buffer.writeBoolean((boolean) value);
                break;
            case 2:
                VarInts.writeInt(buffer, (int) value);
                break;
            case 3:
                buffer.writeFloatLE((float) value);
                break;
        }
    }

    @Override
    public GameRuleData<?> readGameRuleInStartGame(ByteBuf buffer) {
        Preconditions.checkNotNull(buffer, "buffer");

        String name = readString(buffer);
        boolean editable = buffer.readBoolean();
        int type = VarInts.readUnsignedInt(buffer);

        switch (type) {
            case 1:
                return new GameRuleData<>(name, editable, buffer.readBoolean());
            case 2:
                return new GameRuleData<>(name, editable, VarInts.readInt(buffer));
            case 3:
                return new GameRuleData<>(name, editable, buffer.readFloatLE());
        }
        throw new IllegalStateException("Invalid gamerule type received");
    }
}
