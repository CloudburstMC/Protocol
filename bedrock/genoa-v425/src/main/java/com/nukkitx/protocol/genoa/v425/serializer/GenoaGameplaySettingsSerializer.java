package com.nukkitx.protocol.genoa.v425.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.genoa.packet.GenoaGameplaySettings;
import com.nukkitx.protocol.genoa.packet.GenoaNetworkOwnershipStatusPacket;
import io.netty.buffer.ByteBuf;

public class GenoaGameplaySettingsSerializer implements BedrockPacketSerializer<GenoaGameplaySettings> {
    public static final GenoaGameplaySettingsSerializer INSTANCE = new GenoaGameplaySettingsSerializer();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaGameplaySettings packet) {
        buffer.writeBoolean(packet.isMultiplePlayersOnline());
        if(packet.isMultiplePlayersOnline()) {
            buffer.writeLong(packet.getOwnerRuntimeId());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaGameplaySettings packet) {
        packet.setMultiplePlayersOnline(buffer.readBoolean());
        if (packet.isMultiplePlayersOnline()) {
            packet.setOwnerRuntimeId(buffer.readLong());
        }
    }
}
