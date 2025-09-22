package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class BookEditPacket implements BedrockPacket {
    private Action action;
    private int inventorySlot;
    private int pageNumber;
    private int secondaryPageNumber;
    private String text;
    private String photoName;
    private String title;
    private String author;
    private String xuid;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.BOOK_EDIT;
    }

    public enum Action {
        REPLACE_PAGE,
        ADD_PAGE,
        DELETE_PAGE,
        SWAP_PAGES,
        SIGN_BOOK
    }

    @Override
    public BookEditPacket clone() {
        try {
            return (BookEditPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

