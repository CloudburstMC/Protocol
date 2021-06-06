package org.cloudburstmc.protocol.bedrock.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

@Getter
@EqualsAndHashCode
@ToString
public class ScoreInfo {
    private final long scoreboardId;
    private final String objectiveId;
    private final int score;
    private final ScorerType type;
    private final String name;
    private final long entityId;

    public ScoreInfo(long scoreboardId, String objectiveId, int score) {
        this.scoreboardId = scoreboardId;
        this.objectiveId = objectiveId;
        this.score = score;
        this.type = ScorerType.INVALID;
        this.name = null;
        this.entityId = -1;
    }

    public ScoreInfo(long scoreboardId, String objectiveId, int score, String name) {
        this.scoreboardId = scoreboardId;
        this.objectiveId = objectiveId;
        this.score = score;
        this.type = ScorerType.FAKE;
        this.name = name;
        this.entityId = -1;
    }

    public ScoreInfo(long scoreboardId, String objectiveId, int score, ScorerType type, long entityId) {
        checkArgument(type == ScorerType.ENTITY || type == ScorerType.PLAYER, "Must be player or entity");
        this.scoreboardId = scoreboardId;
        this.objectiveId = objectiveId;
        this.score = score;
        this.type = type;
        this.entityId = entityId;
        this.name = null;
    }

    public enum ScorerType {
        INVALID,
        PLAYER,
        ENTITY,
        FAKE
    }
}
