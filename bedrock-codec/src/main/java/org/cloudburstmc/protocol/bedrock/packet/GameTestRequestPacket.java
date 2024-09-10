package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class GameTestRequestPacket implements BedrockPacket {
    private int maxTestsPerBatch;
    private int repeatCount;
    private int rotation;
    private boolean stoppingOnFailure;
    private Vector3i testPos;
    private int testsPerRow;
    private String testName;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.GAME_TEST_REQUEST;
    }

    @Override
    public GameTestRequestPacket clone() {
        try {
            return (GameTestRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
