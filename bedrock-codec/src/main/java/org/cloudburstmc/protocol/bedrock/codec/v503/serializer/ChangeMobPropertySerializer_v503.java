package org.cloudburstmc.protocol.bedrock.codec.v503.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ChangeMobPropertyPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class ChangeMobPropertySerializer_v503 implements BedrockPacketSerializer<ChangeMobPropertyPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ChangeMobPropertyPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        helper.writeString(buffer, packet.getProperty());
        buffer.writeBoolean(packet.isBoolValue());
        helper.writeString(buffer, packet.getStringValue());
        VarInts.writeInt(buffer, packet.getIntValue());
        buffer.writeFloatLE(packet.getFloatValue());
    }

    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ChangeMobPropertyPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setProperty(helper.readString(buffer));
        packet.setBoolValue(buffer.readBoolean());
        packet.setStringValue(helper.readString(buffer));
        packet.setIntValue(VarInts.readInt(buffer));
        packet.setFloatValue(buffer.readFloatLE());
    }
}
