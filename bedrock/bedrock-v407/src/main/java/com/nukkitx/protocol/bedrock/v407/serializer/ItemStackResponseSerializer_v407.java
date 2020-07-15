package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerSlotType;
import com.nukkitx.protocol.bedrock.packet.ItemStackResponsePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemStackResponseSerializer_v407 implements BedrockPacketSerializer<ItemStackResponsePacket> {

    public static final ItemStackResponseSerializer_v407 INSTANCE = new ItemStackResponseSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackResponsePacket packet) {
        helper.writeArray(buffer, packet.getEntries(), (buf, response) -> {
            buf.writeBoolean(response.isSuccess());
            VarInts.writeInt(buffer, response.getRequestId());

            if (!response.isSuccess())
                return;

            helper.writeArray(buf, response.getContainers(), (buf2, containerEntry) -> {
                buf2.writeByte(containerEntry.getContainer().ordinal());

                helper.writeArray(buf2, containerEntry.getItems(), (byteBuf, itemEntry) -> {
                    byteBuf.writeByte(itemEntry.getSlot());
                    byteBuf.writeByte(itemEntry.getHotbarSlot());
                    byteBuf.writeByte(itemEntry.getCount());
                    VarInts.writeInt(byteBuf, itemEntry.getStackNetworkId());
                });
            });
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackResponsePacket packet) {
        List<ItemStackResponsePacket.Response> entries = packet.getEntries();
        helper.readArray(buffer, entries, buf -> {
            boolean success = buf.readBoolean();
            int requestId = VarInts.readInt(buf);

            if (!success)
                return new ItemStackResponsePacket.Response(success, requestId, Collections.emptyList());

            List<ItemStackResponsePacket.ContainerEntry> containerEntries = new ArrayList<>();
            helper.readArray(buf, containerEntries, buf2 -> {
                ContainerSlotType container = ContainerSlotType.values()[buf2.readByte()];

                List<ItemStackResponsePacket.ItemEntry> itemEntries = new ArrayList<>();
                helper.readArray(buf2, itemEntries, byteBuf -> new ItemStackResponsePacket.ItemEntry(
                        byteBuf.readByte(),
                        byteBuf.readByte(),
                        byteBuf.readByte(),
                        VarInts.readInt(byteBuf)
                ));
                return new ItemStackResponsePacket.ContainerEntry(container, itemEntries);
            });
            return new ItemStackResponsePacket.Response(success, requestId, containerEntries);
        });
    }
}
