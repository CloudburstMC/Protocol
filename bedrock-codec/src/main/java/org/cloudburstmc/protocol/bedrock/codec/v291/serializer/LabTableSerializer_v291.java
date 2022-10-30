package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.inventory.LabTableReactionType;
import org.cloudburstmc.protocol.bedrock.data.inventory.LabTableType;
import org.cloudburstmc.protocol.bedrock.packet.LabTablePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LabTableSerializer_v291 implements BedrockPacketSerializer<LabTablePacket> {
    public static final LabTableSerializer_v291 INSTANCE = new LabTableSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, LabTablePacket packet) {
        buffer.writeByte(packet.getType().ordinal());
        helper.writeVector3i(buffer, packet.getPosition());
        buffer.writeByte(packet.getReactionType().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, LabTablePacket packet) {
        packet.setType(LabTableType.values()[buffer.readUnsignedByte()]);
        packet.setPosition(helper.readVector3i(buffer));
        packet.setReactionType(LabTableReactionType.values()[buffer.readUnsignedByte()]);
    }
}
