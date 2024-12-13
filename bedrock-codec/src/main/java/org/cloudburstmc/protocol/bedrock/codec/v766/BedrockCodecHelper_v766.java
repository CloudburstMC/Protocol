package org.cloudburstmc.protocol.bedrock.codec.v766;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v729.BedrockCodecHelper_v729;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.math.BigInteger;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class BedrockCodecHelper_v766 extends BedrockCodecHelper_v729 {

    public BedrockCodecHelper_v766(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes, TypeMap<ItemStackRequestActionType> stackRequestActionTypes,
                                   TypeMap<ContainerSlotType> containerSlotTypes, TypeMap<Ability> abilities, TypeMap<TextProcessingEventOrigin> textProcessingEventOrigins) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes, abilities, textProcessingEventOrigins);
    }

    @Override
    public <T extends Enum<?>> void readLargeVarIntFlags(ByteBuf buffer, Set<T> flags, Class<T> clazz) {
        BigInteger flagsInt = VarInts.readUnsignedBigVarInt(buffer, clazz.getEnumConstants().length);
        for (T flag : clazz.getEnumConstants()) {
            if (flagsInt.testBit(flag.ordinal())) {
                flags.add(flag);
            }
        }
    }

    @Override
    public <T extends Enum<?>> void writeLargeVarIntFlags(ByteBuf buffer, Set<T> flags, Class<T> clazz) {
        BigInteger flagsInt = BigInteger.ZERO;
        for (T flag : flags) {
            flagsInt = flagsInt.setBit(flag.ordinal());
        }
        VarInts.writeUnsignedBigVarInt(buffer, flagsInt);
    }

    @Override
    protected ItemStackResponseSlot readItemEntry(ByteBuf buffer) {
        int slot = buffer.readUnsignedByte();
        int hotbarSlot = buffer.readUnsignedByte();
        int count = buffer.readUnsignedByte();
        int stackNetworkId = VarInts.readInt(buffer);
        String customName = this.readString(buffer);
        String filteredCustomName = this.readString(buffer);
        int durabilityCorrection = VarInts.readInt(buffer);
        return new ItemStackResponseSlot(slot, hotbarSlot, count, stackNetworkId,
                customName, durabilityCorrection, filteredCustomName);

    }

    @Override
    protected void writeItemEntry(ByteBuf buffer, ItemStackResponseSlot itemEntry) {
        buffer.writeByte(itemEntry.getSlot());
        buffer.writeByte(itemEntry.getHotbarSlot());
        buffer.writeByte(itemEntry.getCount());
        VarInts.writeInt(buffer, itemEntry.getStackNetworkId());
        this.writeString(buffer, itemEntry.getCustomName());
        this.writeString(buffer, itemEntry.getFilteredCustomName());
        VarInts.writeInt(buffer, itemEntry.getDurabilityCorrection());
    }
}
