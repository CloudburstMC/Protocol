package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraFadeInstruction;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSetInstruction;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraTargetInstruction;
import org.cloudburstmc.protocol.common.PacketSignal;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CameraInstructionPacket implements BedrockPacket {
    private CameraSetInstruction setInstruction;
    private CameraFadeInstruction fadeInstruction;
    private OptionalBoolean clear = OptionalBoolean.empty();
    /**
     * @since v712
     */
    private CameraTargetInstruction targetInstruction;
    /**
     * @since v712
     */
    private OptionalBoolean removeTarget = OptionalBoolean.empty();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CAMERA_INSTRUCTION;
    }

    public void setClear(boolean value) {
        this.clear = OptionalBoolean.of(value);
    }

    public void setClear(OptionalBoolean clear) {
        this.clear = clear;
    }

    @Override
    public CameraInstructionPacket clone() {
        try {
            return (CameraInstructionPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

