package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.packet.CreativeContentPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreativeContentSerializer_v407 implements BedrockPacketSerializer<CreativeContentPacket> {

    public static final CreativeContentSerializer_v407 INSTANCE = new CreativeContentSerializer_v407();

    private static final ItemData[] EMPTY = new ItemData[0];

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CreativeContentPacket packet, BedrockSession session) {
        helper.writeArray(buffer, packet.getContents(), session, this::writeCreativeItem);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CreativeContentPacket packet, BedrockSession session) {
        packet.setContents(helper.readArray(buffer, EMPTY, session, this::readCreativeItem));
    }

    protected ItemData readCreativeItem(ByteBuf buffer, BedrockPacketHelper helper, BedrockSession session) {
        int netId = VarInts.readUnsignedInt(buffer);
        ItemData item = helper.readItemInstance(buffer, session);
        item.setNetId(netId);
        return item;
    }

    protected void writeCreativeItem(ByteBuf buffer, BedrockPacketHelper helper, BedrockSession session, ItemData item) {
        VarInts.writeUnsignedInt(buffer, item.getNetId());
        helper.writeItemInstance(buffer, item, session);
    }
}
