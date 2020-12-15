package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.PlayerListPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.PlayerListPacket.Action;
import static com.nukkitx.protocol.bedrock.packet.PlayerListPacket.Entry;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerListSerializer_v388 implements BedrockPacketSerializer<PlayerListPacket> {
    public static final PlayerListSerializer_v388 INSTANCE = new PlayerListSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerListPacket packet) {
        buffer.writeByte(packet.getAction().ordinal());
        VarInts.writeUnsignedInt(buffer, packet.getEntries().size());

        for (Entry entry : packet.getEntries()) {
            helper.writeUuid(buffer, entry.getUuid());

            if (packet.getAction() == Action.ADD) {
                VarInts.writeLong(buffer, entry.getEntityId());
                helper.writeString(buffer, entry.getName());
                helper.writeString(buffer, entry.getXuid());
                helper.writeString(buffer, entry.getPlatformChatId());
                buffer.writeIntLE(entry.getBuildPlatform());
                helper.writeSkin(buffer, entry.getSkin());
                buffer.writeBoolean(entry.isTeacher());
                buffer.writeBoolean(entry.isHost());
            }
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerListPacket packet) {
        Action action = Action.values()[buffer.readUnsignedByte()];
        packet.setAction(action);
        int length = VarInts.readUnsignedInt(buffer);

        for (int i = 0; i < length; i++) {
            Entry entry = new Entry(helper.readUuid(buffer));

            if (action == PlayerListPacket.Action.ADD) {
                entry.setEntityId(VarInts.readLong(buffer));
                entry.setName(helper.readString(buffer));
                entry.setXuid(helper.readString(buffer));
                entry.setPlatformChatId(helper.readString(buffer));
                entry.setBuildPlatform(buffer.readIntLE());
                entry.setSkin(helper.readSkin(buffer));
                entry.setTeacher(buffer.readBoolean());
                entry.setHost(buffer.readBoolean());
            }
            packet.getEntries().add(entry);
        }
    }
}
