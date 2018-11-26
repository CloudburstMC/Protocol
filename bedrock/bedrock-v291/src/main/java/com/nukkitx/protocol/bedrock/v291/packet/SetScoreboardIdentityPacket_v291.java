package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetScoreboardIdentityPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class SetScoreboardIdentityPacket_v291 extends SetScoreboardIdentityPacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(type.ordinal());
        BedrockUtils.writeArray(buffer, entries, (buf, entry) -> {
            VarInts.writeLong(buffer, entry.getScoreboardId());
            if (type == Type.ADD) {
                BedrockUtils.writeUuid(buffer, entry.getUuid());
            }
        });
    }

    @Override
    public void decode(ByteBuf buffer) {
        type = Type.values()[buffer.readUnsignedByte()];
        BedrockUtils.readArray(buffer, entries, buf -> {
            long scoreboardId = VarInts.readLong(buffer);
            UUID uuid = null;
            if (type == Type.ADD) {
                uuid = BedrockUtils.readUuid(buffer);
            }
            return new Entry(scoreboardId, uuid);
        });
    }
}
