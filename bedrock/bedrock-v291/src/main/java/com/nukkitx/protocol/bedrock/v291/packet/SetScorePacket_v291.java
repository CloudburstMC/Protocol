package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.ScoreInfo;
import com.nukkitx.protocol.bedrock.packet.SetScorePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class SetScorePacket_v291 extends SetScorePacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(action.ordinal());

        BedrockUtils.writeArray(buffer, infos, (buf, scoreInfo) -> {
            VarInts.writeLong(buf, scoreInfo.getScoreboardId());
            BedrockUtils.writeString(buf, scoreInfo.getObjectiveId());
            buf.writeIntLE(scoreInfo.getScore());
            if (action == Action.SET) {
                buf.writeByte(scoreInfo.getType().ordinal());
                switch (scoreInfo.getType()) {
                    case ENTITY:
                    case PLAYER:
                        VarInts.writeLong(buf, scoreInfo.getEntityId());
                        break;
                    case FAKE:
                        BedrockUtils.writeString(buf, scoreInfo.getName());
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid score info received");
                }
            }
        });
    }

    @Override
    public void decode(ByteBuf buffer) {
        action = Action.values()[buffer.readUnsignedByte()];

        BedrockUtils.readArray(buffer, infos, buf -> {
            long scoreboardId = VarInts.readLong(buf);
            String objectiveId = BedrockUtils.readString(buf);
            int score = buf.readIntLE();
            if (action == Action.SET) {
                ScoreInfo.ScorerType type = ScoreInfo.ScorerType.values()[buf.readUnsignedByte()];
                switch (type) {
                    case ENTITY:
                    case PLAYER:
                        long entityId = VarInts.readLong(buf);
                        return new ScoreInfo(scoreboardId, objectiveId, score, type, entityId);
                    case FAKE:
                        String name = BedrockUtils.readString(buf);
                        return new ScoreInfo(scoreboardId, objectiveId, score, name);
                    default:
                        throw new IllegalArgumentException("Invalid score info received");
                }
            } else {
                return new ScoreInfo(scoreboardId, objectiveId, score);
            }
        });
    }

}
