package com.nukkitx.protocol.bedrock.v402.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ItemStackResponsePacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemStackResponseSerializer_v402 implements PacketSerializer<ItemStackResponsePacket> {

    public static final ItemStackResponseSerializer_v402 INSTANCE = new ItemStackResponseSerializer_v402();

    @Override
    public void serialize(ByteBuf buffer, ItemStackResponsePacket packet) {
        BedrockUtils.writeArray(buffer, packet.getEntries(), (buf, response) -> {
            buf.writeBoolean(response.isHasContainers());

            BedrockUtils.writeArray(buf, response.getContainers(), (buf2, containerEntry) -> {
                buf2.writeByte(containerEntry.getWindowsId());

                BedrockUtils.writeArray(buf2, containerEntry.getItems(), (byteBuf, itemEntry) -> {
                    byteBuf.writeByte(itemEntry.getSlot());
                    byteBuf.writeByte(itemEntry.getHotbarSlot());
                    byteBuf.writeByte(itemEntry.getCount());
                    VarInts.writeInt(byteBuf, itemEntry.getItemStackId());
                });
            });
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, ItemStackResponsePacket packet) {
        List<ItemStackResponsePacket.Response> entries = packet.getEntries();
        BedrockUtils.readArray(buffer, entries, buf -> {
            boolean hasArray = buf.readBoolean();
            int requestId = VarInts.readInt(buf);

            List<ItemStackResponsePacket.ContainerEntry> containerEntries = null;
            if (hasArray) {
                containerEntries = new ArrayList<>();
                BedrockUtils.readArray(buf, containerEntries, buf2 -> {
                    byte windowId = buf2.readByte();

                    List<ItemStackResponsePacket.ItemEntry> itemEntries = new ArrayList<>();
                    BedrockUtils.readArray(buf2, itemEntries, byteBuf -> {
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
