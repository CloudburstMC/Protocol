package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerAuthInputPacket extends BedrockPacket {
    private Vector3f rotation; // head rot after motion
    private Vector3f position;
    private Vector2f motion;
    private long inputData;
    private int inputMode;
    private int playMode;

    private Vector3f vrGazeDirection;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_AUTH_INPUT;
    }
}
