package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.packet.CreativeContentPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreativeContentSerializer_v407 implements BedrockPacketSerializer<CreativeContentPacket> {

    public static final CreativeContentSerializer_v407 INSTANCE = new CreativeContentSerializer_v407();

    private static final ItemData[] EMPTY = new ItemData[0];

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CreativeContentPacket packet) {
        helper.writeArray(buffer, packet.getContents(), this::writeCreativeItem);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CreativeContentPacket packet) {
        packet.setContents(helper.readArray(buffer, EMPTY, this::readCreativeItem));
    }

    protected ItemData readCreativeItem(ByteBuf buffer, BedrockCodecHelper helper) {
        int netId = VarInts.readUnsignedInt(buffer);
        ItemData item = helper.readItemInstance(buffer);
        item.setNetId(netId);
        return item;
    }

    protected void writeCreativeItem(ByteBuf buffer, BedrockCodecHelper helper, ItemData item) {
        VarInts.writeUnsignedInt(buffer, item.getNetId());
        helper.writeItemInstance(buffer, item);
    }
}
