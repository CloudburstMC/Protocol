package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.CraftingEventPacket;
import com.nukkitx.protocol.bedrock.v361.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CraftingEventSerializer_v361 implements PacketSerializer<CraftingEventPacket> {
    public static final CraftingEventSerializer_v361 INSTANCE = new CraftingEventSerializer_v361();


    @Override
    public void serialize(ByteBuf buffer, CraftingEventPacket packet) {
        buffer.writeByte(packet.getWindowId());
        VarInts.writeInt(buffer, packet.getType());
        BedrockUtils.writeUuid(buffer, packet.getUuid());

        BedrockUtils.writeArray(buffer, packet.getInputs(), BedrockUtils::writeItemData);

        BedrockUtils.writeArray(buffer, packet.getOutputs(), BedrockUtils::writeItemData);
    }

    @Override
    public void deserialize(ByteBuf buffer, CraftingEventPacket packet) {
        packet.setWindowId(buffer.readByte());
        packet.setType(VarInts.readInt(buffer));
        packet.setUuid(BedrockUtils.readUuid(buffer));

        BedrockUtils.readArray(buffer, packet.getInputs(), BedrockUtils::readItemData);

        BedrockUtils.readArray(buffer, packet.getOutputs(), BedrockUtils::readItemData);
    }
}
