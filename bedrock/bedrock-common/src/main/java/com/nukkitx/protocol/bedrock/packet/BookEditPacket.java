package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookEditPacket extends BedrockPacket {
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
    public final boolean handle(BedrockPacketHandler handler) {
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
}
