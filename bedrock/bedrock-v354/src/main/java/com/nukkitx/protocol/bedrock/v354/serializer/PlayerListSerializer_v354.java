package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.PlayerListPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.PlayerListPacket.Entry;
import static com.nukkitx.protocol.bedrock.packet.PlayerListPacket.Type;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerListSerializer_v354 implements PacketSerializer<PlayerListPacket> {
    public static final PlayerListSerializer_v354 INSTANCE = new PlayerListSerializer_v354();


    @Override
    public void serialize(ByteBuf buffer, PlayerListPacket packet) {
        buffer.writeByte(packet.getType().ordinal());
        VarInts.writeUnsignedInt(buffer, packet.getEntries().size());

        for (Entry entry : packet.getEntries()) {
            BedrockUtils.writeUuid(buffer, entry.getUuid());

            if (packet.getType() == Type.ADD) {
                VarInts.writeLong(buffer, entry.getEntityId());
                BedrockUtils.writeString(buffer, entry.getName());
                BedrockUtils.writeString(buffer, entry.getSkinId());
                BedrockUtils.writeByteArray(buffer, entry.getSkinData());
                BedrockUtils.writeByteArray(buffer, entry.getCapeData());
                BedrockUtils.writeString(buffer, entry.getGeometryName());
                BedrockUtils.writeString(buffer, entry.getGeometryData());
                BedrockUtils.writeString(buffer, entry.getXuid());
                BedrockUtils.writeString(buffer, entry.getPlatformChatId());
            }
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, PlayerListPacket packet) {
        Type type = Type.values()[buffer.readUnsignedByte()];
        packet.setType(type);
        int length = VarInts.readUnsignedInt(buffer);

        for (int i = 0; i < length; i++) {
            Entry entry = new Entry(BedrockUtils.readUuid(buffer));

            if (type == Type.ADD) {
                entry.setEntityId(VarInts.readLong(buffer));
                entry.setName(BedrockUtils.readString(buffer));
                entry.setSkinId(BedrockUtils.readString(buffer));
                entry.setSkinData(BedrockUtils.readByteArray(buffer));
                entry.setCapeData(BedrockUtils.readByteArray(buffer));
                entry.setGeometryName(BedrockUtils.readString(buffer));
                entry.setGeometryData(BedrockUtils.readString(buffer));
                entry.setXuid(BedrockUtils.readString(buffer));
                entry.setPlatformChatId(BedrockUtils.readString(buffer));
            }
            packet.getEntries().add(entry);
        }
    }
}
