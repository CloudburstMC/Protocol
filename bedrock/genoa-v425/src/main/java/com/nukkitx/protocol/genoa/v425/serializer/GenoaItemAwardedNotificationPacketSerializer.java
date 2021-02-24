package com.nukkitx.protocol.genoa.v425.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.genoa.packet.GenoaItemAwardedNotificationPacket;
import com.nukkitx.protocol.genoa.packet.GenoaItemBrokeNotificationPacket;
import io.netty.buffer.ByteBuf;

public class GenoaItemAwardedNotificationPacketSerializer implements BedrockPacketSerializer<GenoaItemAwardedNotificationPacket> {
    public static final GenoaItemAwardedNotificationPacketSerializer INSTANCE = new GenoaItemAwardedNotificationPacketSerializer();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaItemAwardedNotificationPacket packet) {
        helper.writeString(buffer,packet.getString1());
        helper.writeString(buffer,packet.getString2());
        helper.writeString(buffer,packet.getString3());
        // TODO: Implement the loop function
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaItemAwardedNotificationPacket packet) {
        packet.setString1(helper.readString(buffer));
        packet.setString2(helper.readString(buffer));
        packet.setString3(helper.readString(buffer));

    }
}
