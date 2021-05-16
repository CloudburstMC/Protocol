package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.math.vector.Vector3i;
import lombok.Data;

@Data
public class PlayerBlockActionData {
    PlayerActionType action;
    /**
     * Only used if {@link #action} is set to {@link PlayerActionType#START_BREAK}, {@link PlayerActionType#ABORT_BREAK},
     * {@link PlayerActionType#CONTINUE_BREAK}, {@link PlayerActionType#BLOCK_PREDICT_DESTROY} or {@link PlayerActionType#BLOCK_CONTINUE_DESTROY}
     */
    Vector3i blockPosition;
    /**
     * Only used if {@link #action} is set to {@link PlayerActionType#START_BREAK}, {@link PlayerActionType#ABORT_BREAK},
     * {@link PlayerActionType#CONTINUE_BREAK}, {@link PlayerActionType#BLOCK_PREDICT_DESTROY} or {@link PlayerActionType#BLOCK_CONTINUE_DESTROY}
     */
    int face;
}
