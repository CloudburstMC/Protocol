package org.cloudburstmc.protocol.bedrock.codec.v800.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v649.serializer.PlayerListSerializer_v649;
import org.cloudburstmc.protocol.bedrock.packet.PlayerListPacket;

import java.awt.*;

public class PlayerListSerializer_v800 extends PlayerListSerializer_v649 {

    public static final PlayerListSerializer_v800 INSTANCE = new PlayerListSerializer_v800();

    @Override
    protected void writeEntryBase(ByteBuf buffer, BedrockCodecHelper helper, PlayerListPacket.Entry entry) {
        super.writeEntryBase(buffer, helper, entry);
        buffer.writeIntLE(entry.getColor().getRGB());
    }

    @Override
    protected PlayerListPacket.Entry readEntryBase(ByteBuf buffer, BedrockCodecHelper helper) {
        PlayerListPacket.Entry entry = super.readEntryBase(buffer, helper);
        entry.setColor(new Color(buffer.readIntLE(), true));
        return entry;
    }
}
