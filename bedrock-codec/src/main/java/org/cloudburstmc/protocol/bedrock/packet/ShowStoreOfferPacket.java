package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.StoreOfferRedirectType;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ShowStoreOfferPacket implements BedrockPacket {
    private String offerId;
    /**
     * @since v630 deprecated
     */
    @Deprecated
    private boolean shownToAll;

    /**
     * @since v630
     */
    private StoreOfferRedirectType redirectType;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SHOW_STORE_OFFER;
    }

    @Override
    public ShowStoreOfferPacket clone() {
        try {
            return (ShowStoreOfferPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

