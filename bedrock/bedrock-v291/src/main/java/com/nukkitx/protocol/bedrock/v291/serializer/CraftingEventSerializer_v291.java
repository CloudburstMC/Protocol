package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingType;
import com.nukkitx.protocol.bedrock.packet.CraftingEventPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingEventSerializer_v291 implements BedrockPacketSerializer<CraftingEventPacket> {
    public static final CraftingEventSerializer_v291 INSTANCE = new CraftingEventSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingEventPacket packet) {
        buffer.writeByte(packet.getContainerId());
        VarInts.writeInt(buffer, packet.getType().ordinal());
        helper.writeUuid(buffer, packet.getUuid());

        helper.writeArray(buffer, packet.getInputs(), helper::writeItem);

        helper.writeArray(buffer, packet.getOutputs(), helper::writeItem);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingEventPacket packet) {
        packet.setContainerId(buffer.readByte());
        packet.setType(CraftingType.values()[VarInts.readInt(buffer)]);
        packet.setUuid(helper.readUuid(buffer));

        helper.readArray(buffer, packet.getInputs(), helper::readItem);

        helper.readArray(buffer, packet.getOutputs(), helper::readItem);
    }
}
