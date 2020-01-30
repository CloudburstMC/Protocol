package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.PlayerListPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.PlayerListPacket.Action;
import static com.nukkitx.protocol.bedrock.packet.PlayerListPacket.Entry;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerListSerializer_v388 implements PacketSerializer<PlayerListPacket> {
    public static final PlayerListSerializer_v388 INSTANCE = new PlayerListSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, PlayerListPacket packet) {
        buffer.writeByte(packet.getAction().ordinal());
        VarInts.writeUnsignedInt(buffer, packet.getEntries().size());

        for (Entry entry : packet.getEntries()) {
            BedrockUtils.writeUuid(buffer, entry.getUuid());

            if (packet.getAction() == Action.ADD) {
                VarInts.writeLong(buffer, entry.getEntityId());
                BedrockUtils.writeString(buffer, entry.getName());
                BedrockUtils.writeString(buffer, entry.getXuid());
                BedrockUtils.writeString(buffer, entry.getPlatformChatId());
                buffer.writeIntLE(entry.getBuildPlatform());
                BedrockUtils.writeSkin(buffer, entry.getSkin());
                buffer.writeBoolean(entry.isTeacher());
                buffer.writeBoolean(entry.isHost());
            }
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, PlayerListPacket packet) {
        Action action = Action.values()[buffer.readUnsignedByte()];
        packet.setAction(action);
        int length = VarInts.readUnsignedInt(buffer);

        for (int i = 0; i < length; i++) {
            Entry entry = new Entry(BedrockUtils.readUuid(buffer));

            if (action == PlayerListPacket.Action.ADD) {
                entry.setEntityId(VarInts.readLong(buffer));
                entry.setName(BedrockUtils.readString(buffer));
                entry.setXuid(BedrockUtils.readString(buffer));
                entry.setPlatformChatId(BedrockUtils.readString(buffer));
                entry.setBuildPlatform(buffer.readIntLE());
                entry.setSkin(BedrockUtils.readSkin(buffer));
                entry.setTeacher(buffer.readBoolean());
                entry.setHost(buffer.readBoolean());
            }
            packet.getEntries().add(entry);
        }
    }
}
