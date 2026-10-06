package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.PlayerListSerializer_v2168;
import org.cloudburstmc.protocol.bedrock.data.BuildPlatform;
import org.cloudburstmc.protocol.bedrock.packet.PlayerListPacket;
import org.cloudburstmc.protocol.common.util.TextConverter;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.awt.Color;

public class PlayerListSerializer_v2225 extends PlayerListSerializer_v2168 {

    public static final PlayerListSerializer_v2225 INSTANCE = new PlayerListSerializer_v2225();

    @Override
    protected void writeEntryBase(ByteBuf buffer, BedrockCodecHelper helper, PlayerListPacket.Entry entry) {
        helper.writeUuid(buffer, entry.getUuid());
        VarInts.writeLong(buffer, entry.getEntityId());
        TextConverter converter = helper.getTextConverter();
        helper.writeString(buffer, converter.serialize(entry.getName(CharSequence.class)));
        helper.writeString(buffer, entry.getXuid());
        helper.writeString(buffer, entry.getSkin().getPlayFabId()); //new
        helper.writeString(buffer, entry.getPlatformChatId());
        buffer.writeIntLE(entry.getBuildPlatform().getId());
        helper.writeSkin(buffer, entry.getSkin());
        buffer.writeBoolean(entry.isTeacher());
        buffer.writeBoolean(entry.isHost());
        buffer.writeBoolean(entry.isSubClient());
        buffer.writeIntLE(entry.getColor().getRGB());
    }

    @Override
    protected PlayerListPacket.Entry readEntryBase(ByteBuf buffer, BedrockCodecHelper helper) {
        PlayerListPacket.Entry entry = new PlayerListPacket.Entry(helper.readUuid(buffer));
        entry.setEntityId(VarInts.readLong(buffer));
        TextConverter converter = helper.getTextConverter();
        entry.setName(converter.deserialize(helper.readString(buffer)));
        entry.setXuid(helper.readString(buffer));

        String playFabId = helper.readString(buffer);

        entry.setPlatformChatId(helper.readString(buffer));
        entry.setBuildPlatform(BuildPlatform.from(buffer.readIntLE()));
        entry.setSkin(helper.readSkin(buffer));
        entry.setTeacher(buffer.readBoolean());
        entry.setHost(buffer.readBoolean());
        entry.setSubClient(buffer.readBoolean());
        entry.setColor(new Color(buffer.readIntLE(), true));

        entry.getSkin().updatePlayFabId(playFabId);
        return entry;
    }
}
