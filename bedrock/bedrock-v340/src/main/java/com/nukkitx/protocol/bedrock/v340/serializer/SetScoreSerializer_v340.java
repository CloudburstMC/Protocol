package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.ScoreInfo;
import com.nukkitx.protocol.bedrock.packet.SetScorePacket;
import com.nukkitx.protocol.bedrock.v340.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.SetScorePacket.Action;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetScoreSerializer_v340 implements PacketSerializer<SetScorePacket> {
    public static final SetScoreSerializer_v340 INSTANCE = new SetScoreSerializer_v340();


    @Override
    public void serialize(ByteBuf buffer, SetScorePacket packet) {
        Action action = packet.getAction();
        buffer.writeByte(action.ordinal());

        BedrockUtils.writeArray(buffer, packet.getInfos(), (buf, scoreInfo) -> {
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
    public void deserialize(ByteBuf buffer, SetScorePacket packet) {
        Action action = Action.values()[buffer.readUnsignedByte()];
        packet.setAction(action);

        BedrockUtils.readArray(buffer, packet.getInfos(), buf -> {
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
