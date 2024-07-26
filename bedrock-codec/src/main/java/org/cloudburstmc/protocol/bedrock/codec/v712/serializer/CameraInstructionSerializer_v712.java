package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v618.serializer.CameraInstructionSerializer_618;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraTargetInstruction;
import org.cloudburstmc.protocol.bedrock.packet.CameraInstructionPacket;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;
import org.cloudburstmc.protocol.common.util.VarInts;

public class CameraInstructionSerializer_v712 extends CameraInstructionSerializer_618 {
    public static final CameraInstructionSerializer_v712 INSTANCE = new CameraInstructionSerializer_v712();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraInstructionPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeOptionalNull(buffer, packet.getTargetInstruction(), (buf, targetInstruction) -> {
            helper.writeOptionalNull(buf, targetInstruction.getTargetCenterOffset(), helper::writeVector3f);
            buf.writeLongLE(targetInstruction.getUniqueEntityId());
        });
        helper.writeOptional(buffer, OptionalBoolean::isPresent, packet.getRemoveTarget(),
                (buf, removeTarget) -> buf.writeBoolean(removeTarget.getAsBoolean()));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraInstructionPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setTargetInstruction(helper.readOptional(buffer, null, buf -> {
            Vector3f targetCenterOffset = helper.readOptional(buffer, null, helper::readVector3f);
            long uniqueEntityId = buf.readLongLE();
            return new CameraTargetInstruction(targetCenterOffset, uniqueEntityId);
        }));
        packet.setRemoveTarget(helper.readOptional(buffer, OptionalBoolean.empty(), buf -> OptionalBoolean.of(buf.readBoolean())));
    }
}