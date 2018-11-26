package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.BookEditPacket;
import com.nukkitx.protocol.bedrock.util.TIntHashBiMap;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class BookEditPacket_v291 extends BookEditPacket {
    private static final TIntHashBiMap<Type> types = new TIntHashBiMap<>();

    static {
        types.put(0, Type.REPLACE_PAGE);
        types.put(1, Type.ADD_PAGE);
        types.put(2, Type.DELETE_PAGE);
        types.put(3, Type.SWAP_PAGES);
        types.put(4, Type.SIGN_BOOK);
    }

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(type.ordinal());
        buffer.writeByte(inventorySlot);
        switch (type) {
            case REPLACE_PAGE:
            case ADD_PAGE:
                buffer.writeByte(pageNumber);
                BedrockUtils.writeString(buffer, text);
                BedrockUtils.writeString(buffer, photoName);
                break;
            case DELETE_PAGE:
                buffer.writeByte(pageNumber);
                break;
            case SWAP_PAGES:
                buffer.writeByte(pageNumber);
                buffer.writeByte(secondaryPageNumber);
                break;
            case SIGN_BOOK:
                BedrockUtils.writeString(buffer, title);
                BedrockUtils.writeString(buffer, author);
                BedrockUtils.writeString(buffer, xuid);
                break;
        }
    }

    @Override
    public void decode(ByteBuf buffer) {
        type = types.get(buffer.readUnsignedByte());
        inventorySlot = buffer.readByte();
        switch (type) {
            case REPLACE_PAGE:
            case ADD_PAGE:
                pageNumber = buffer.readByte();
                text = BedrockUtils.readString(buffer);
                photoName = BedrockUtils.readString(buffer);
                break;
            case DELETE_PAGE:
                pageNumber = buffer.readByte();
                break;
            case SWAP_PAGES:
                pageNumber = buffer.readByte();
                secondaryPageNumber = buffer.readByte();
                break;
            case SIGN_BOOK:
                title = BedrockUtils.readString(buffer);
                author = BedrockUtils.readString(buffer);
                xuid = BedrockUtils.readString(buffer);
                break;
        }
    }
}
