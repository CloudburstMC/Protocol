package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.PlayerListPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;


public class PlayerListPacket_v291 extends PlayerListPacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(type.ordinal());
        VarInts.writeUnsignedInt(buffer, entries.size());

        for (Entry entry : entries) {
            BedrockUtils.writeUuid(buffer, entry.getUuid());

            if (type == Type.ADD) {
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
    public void decode(ByteBuf buffer) {
        type = Type.values()[buffer.readUnsignedByte()];

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
        }
    }
}
