package com.nukkitx.protocol.bedrock.v390.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.PlayerListPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.bedrock.v390.BedrockUtils_v390;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.PlayerListPacket.Action;
import static com.nukkitx.protocol.bedrock.packet.PlayerListPacket.Entry;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerListSerializer_v390 implements PacketSerializer<PlayerListPacket> {
    public static final PlayerListSerializer_v390 INSTANCE = new PlayerListSerializer_v390();

    @Override
    public void serialize(ByteBuf buffer, PlayerListPacket packet) {
        buffer.writeByte(packet.getAction().ordinal());
        VarInts.writeUnsignedInt(buffer, packet.getEntries().size());

        if (packet.getAction() == Action.ADD) {
            for (Entry entry : packet.getEntries()) {
                BedrockUtils.writeUuid(buffer, entry.getUuid());

                VarInts.writeLong(buffer, entry.getEntityId());
                BedrockUtils.writeString(buffer, entry.getName());
                BedrockUtils.writeString(buffer, entry.getXuid());
                BedrockUtils.writeString(buffer, entry.getPlatformChatId());
                buffer.writeIntLE(entry.getBuildPlatform());
                BedrockUtils_v390.writeSkin(buffer, entry.getSkin());
                buffer.writeBoolean(entry.isTeacher());
                buffer.writeBoolean(entry.isHost());
            }
            for (Entry entry : packet.getEntries()) {
                buffer.writeBoolean(entry.isTrustedSkin());
            }
        } else {
            for (Entry entry : packet.getEntries()) {
                BedrockUtils.writeUuid(buffer, entry.getUuid());
            }
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, PlayerListPacket packet) {
        Action action = Action.values()[buffer.readUnsignedByte()];
        packet.setAction(action);
        int length = VarInts.readUnsignedInt(buffer);

        if (action == Action.ADD) {
            for (int i = 0; i < length; i++) {
                Entry entry = new Entry(BedrockUtils.readUuid(buffer));
                entry.setEntityId(VarInts.readLong(buffer));
                entry.setName(BedrockUtils.readString(buffer));
                entry.setXuid(BedrockUtils.readString(buffer));
                entry.setPlatformChatId(BedrockUtils.readString(buffer));
                entry.setBuildPlatform(buffer.readIntLE());
                entry.setSkin(BedrockUtils_v390.readSkin(buffer));
                entry.setTeacher(buffer.readBoolean());
                entry.setHost(buffer.readBoolean());
                packet.getEntries().add(entry);
            }

            for (int i = 0; i < length; i++) {
                packet.getEntries().get(i).setTrustedSkin(buffer.readBoolean());
            }
        } else {
            for (int i = 0; i < length; i++) {
                packet.getEntries().add(new Entry(BedrockUtils.readUuid(buffer)));
            }
        }
    }
}
