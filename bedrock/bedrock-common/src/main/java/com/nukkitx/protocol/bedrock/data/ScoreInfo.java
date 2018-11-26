package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.network.util.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.NonFinal;

@Getter
@EqualsAndHashCode
@ToString
public class ScoreInfo {
    private final long scoreboardId;
    private final String objectiveId;
    private final int score;
    private final ScorerType type;
    @NonFinal
    private String name;
    @NonFinal
    private long entityId;

    public ScoreInfo(long scoreboardId, String objectiveId, int score) {
        this.scoreboardId = scoreboardId;
        this.objectiveId = objectiveId;
        this.score = score;
        this.type = ScorerType.INVALID;
    }

    public ScoreInfo(long scoreboardId, String objectiveId, int score, String name) {
        this.scoreboardId = scoreboardId;
        this.objectiveId = objectiveId;
        this.score = score;
        this.type = ScorerType.FAKE;
        this.name = name;
    }

    public ScoreInfo(long scoreboardId, String objectiveId, int score, ScorerType type, long entityId) {
        Preconditions.checkArgument(type == ScorerType.ENTITY || type == ScorerType.PLAYER, "Must be player or entity");
        this.scoreboardId = scoreboardId;
        this.objectiveId = objectiveId;
        this.score = score;
        this.type = type;
        this.entityId = entityId;
    }

    public enum ScorerType {
        INVALID,
        PLAYER,
        ENTITY,
        FAKE
    }
}
