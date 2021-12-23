package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingType;
import com.nukkitx.protocol.bedrock.packet.CraftingEventPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingEventSerializer_v291 implements BedrockPacketSerializer<CraftingEventPacket> {
    public static final CraftingEventSerializer_v291 INSTANCE = new CraftingEventSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingEventPacket packet, BedrockSession session) {
        buffer.writeByte(packet.getContainerId());
        VarInts.writeInt(buffer, packet.getType().ordinal());
        helper.writeUuid(buffer, packet.getUuid());

        helper.writeArray(buffer, packet.getInputs(), (buf, item) -> helper.writeItem(buf, item, session));
        helper.writeArray(buffer, packet.getOutputs(), (buf, item) -> helper.writeItem(buf, item, session));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingEventPacket packet, BedrockSession session) {
        packet.setContainerId(buffer.readByte());
        packet.setType(CraftingType.values()[VarInts.readInt(buffer)]);
        packet.setUuid(helper.readUuid(buffer));

        helper.readArray(buffer, packet.getInputs(), buf -> helper.readItem(buf, session));
        helper.readArray(buffer, packet.getOutputs(), buf -> helper.readItem(buf, session));
    }
}
