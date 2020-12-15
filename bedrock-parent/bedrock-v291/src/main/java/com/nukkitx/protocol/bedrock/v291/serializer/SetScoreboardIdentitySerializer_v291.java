package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SetScoreboardIdentityPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.nukkitx.protocol.bedrock.packet.SetScoreboardIdentityPacket.Action;
import static com.nukkitx.protocol.bedrock.packet.SetScoreboardIdentityPacket.Entry;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetScoreboardIdentitySerializer_v291 implements BedrockPacketSerializer<SetScoreboardIdentityPacket> {
    public static final SetScoreboardIdentitySerializer_v291 INSTANCE = new SetScoreboardIdentitySerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetScoreboardIdentityPacket packet) {
        SetScoreboardIdentityPacket.Action action = packet.getAction();
        buffer.writeByte(action.ordinal());
        helper.writeArray(buffer, packet.getEntries(), (buf, entry) -> {
            VarInts.writeLong(buffer, entry.getScoreboardId());
            if (action == Action.ADD) {
                helper.writeUuid(buffer, entry.getUuid());
            }
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetScoreboardIdentityPacket packet) {
        SetScoreboardIdentityPacket.Action action = Action.values()[buffer.readUnsignedByte()];
        packet.setAction(action);
        helper.readArray(buffer, packet.getEntries(), buf -> {
            long scoreboardId = VarInts.readLong(buffer);
            UUID uuid = null;
            if (action == Action.ADD) {
                uuid = helper.readUuid(buffer);
            }
            return new Entry(scoreboardId, uuid);
        });
    }
}
