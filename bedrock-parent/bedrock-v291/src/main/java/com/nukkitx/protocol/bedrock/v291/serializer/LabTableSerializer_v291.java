package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.LabTableReactionType;
import com.nukkitx.protocol.bedrock.data.inventory.LabTableType;
import com.nukkitx.protocol.bedrock.packet.LabTablePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LabTableSerializer_v291 implements BedrockPacketSerializer<LabTablePacket> {
    public static final LabTableSerializer_v291 INSTANCE = new LabTableSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LabTablePacket packet) {
        buffer.writeByte(packet.getType().ordinal());
        helper.writeVector3i(buffer, packet.getPosition());
        buffer.writeByte(packet.getReactionType().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LabTablePacket packet) {
        packet.setType(LabTableType.values()[buffer.readUnsignedByte()]);
        packet.setPosition(helper.readVector3i(buffer));
        packet.setReactionType(LabTableReactionType.values()[buffer.readUnsignedByte()]);
    }
}
