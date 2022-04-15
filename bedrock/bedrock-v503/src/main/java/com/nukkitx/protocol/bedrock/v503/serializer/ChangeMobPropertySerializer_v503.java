package com.nukkitx.protocol.bedrock.v503.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ChangeMobPropertyPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeMobPropertySerializer_v503 implements BedrockPacketSerializer<ChangeMobPropertyPacket> {

    public static final ChangeMobPropertySerializer_v503 INSTANCE = new ChangeMobPropertySerializer_v503();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ChangeMobPropertyPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        helper.writeString(buffer, packet.getProperty());
        buffer.writeBoolean(packet.isBoolValue());
        helper.writeString(buffer, packet.getStringValue());
        VarInts.writeInt(buffer, packet.getIntValue());
        buffer.writeFloatLE(packet.getFloatValue());
    }

    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ChangeMobPropertyPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setProperty(helper.readString(buffer));
        packet.setBoolValue(buffer.readBoolean());
        packet.setStringValue(helper.readString(buffer));
        packet.setIntValue(VarInts.readInt(buffer));
        packet.setFloatValue(buffer.readFloatLE());
    }
}