package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateTradePacket implements BedrockPacket {
    private int containerId;
    private ContainerType containerType;
    private int size; // Hardcoded to 0
    private int tradeTier;
    private long traderUniqueEntityId;
    private long playerUniqueEntityId;
    private String displayName;
    private NbtMap offers;
    private boolean newTradingUi;
    private boolean recipeAddedOnUpdate;
    private boolean usingEconomyTrade;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_TRADE;
    }

    @Override
    public UpdateTradePacket clone() {
        try {
            return (UpdateTradePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

