package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ItemStackResponsePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemStackResponseSerializer_v407 implements BedrockPacketSerializer<ItemStackResponsePacket> {

    public static final ItemStackResponseSerializer_v407 INSTANCE = new ItemStackResponseSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackResponsePacket packet) {
        helper.writeArray(buffer, packet.getEntries(), (buf, response) -> {
            buf.writeBoolean(response.isHasContainers());

            helper.writeArray(buf, response.getContainers(), (buf2, containerEntry) -> {
                buf2.writeByte(containerEntry.getWindowsId());

                helper.writeArray(buf2, containerEntry.getItems(), (byteBuf, itemEntry) -> {
                    byteBuf.writeByte(itemEntry.getSlot());
                    byteBuf.writeByte(itemEntry.getHotbarSlot());
                    byteBuf.writeByte(itemEntry.getCount());
                    VarInts.writeInt(byteBuf, itemEntry.getItemStackId());
                });
            });
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackResponsePacket packet) {
        List<ItemStackResponsePacket.Response> entries = packet.getEntries();
        helper.readArray(buffer, entries, buf -> {
            boolean hasArray = buf.readBoolean();
            int requestId = VarInts.readInt(buf);

            List<ItemStackResponsePacket.ContainerEntry> containerEntries = null;
            if (hasArray) {
                containerEntries = new ArrayList<>();
                helper.readArray(buf, containerEntries, buf2 -> {
                    byte windowId = buf2.readByte();

                    List<ItemStackResponsePacket.ItemEntry> itemEntries = new ArrayList<>();
                    helper.readArray(buf2, itemEntries, byteBuf -> {
                        byte slot = byteBuf.readByte();
                        byte hotbarSlot = byteBuf.readByte();
                        byte count = byteBuf.readByte();
                        int unknownVarint0 = VarInts.readInt(byteBuf);
                        return new ItemStackResponsePacket.ItemEntry(slot, hotbarSlot, count, unknownVarint0);
                    });
                    return new ItemStackResponsePacket.ContainerEntry(windowId, itemEntries);
                });
            }
            return new ItemStackResponsePacket.Response(hasArray, requestId, containerEntries);
        });
    }
}
