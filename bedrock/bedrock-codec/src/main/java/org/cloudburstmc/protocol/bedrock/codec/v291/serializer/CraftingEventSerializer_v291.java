package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.inventory.CraftingType;
import org.cloudburstmc.protocol.bedrock.packet.CraftingEventPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingEventSerializer_v291 implements BedrockPacketSerializer<CraftingEventPacket> {
    public static final CraftingEventSerializer_v291 INSTANCE = new CraftingEventSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingEventPacket packet) {
        buffer.writeByte(packet.getContainerId());
        VarInts.writeInt(buffer, packet.getType().ordinal());
        helper.writeUuid(buffer, packet.getUuid());

        helper.writeArray(buffer, packet.getInputs(), (buf, item) -> helper.writeItem(buf, item));
        helper.writeArray(buffer, packet.getOutputs(), (buf, item) -> helper.writeItem(buf, item));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CraftingEventPacket packet) {
        packet.setContainerId(buffer.readByte());
        packet.setType(CraftingType.values()[VarInts.readInt(buffer)]);
        packet.setUuid(helper.readUuid(buffer));

        helper.readArray(buffer, packet.getInputs(), buf -> helper.readItem(buf));
        helper.readArray(buffer, packet.getOutputs(), buf -> helper.readItem(buf));
    }
}
