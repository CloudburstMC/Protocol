package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v618.serializer.CameraInstructionSerializer_618;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSetInstruction;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraTargetInstruction;
import org.cloudburstmc.protocol.bedrock.packet.CameraInstructionPacket;
import org.cloudburstmc.protocol.common.NamedDefinition;
import org.cloudburstmc.protocol.common.util.DefinitionUtils;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;
import org.cloudburstmc.protocol.common.util.Preconditions;
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

    @Override
    protected void writeSetInstruction(BedrockCodecHelper helper, ByteBuf buf, CameraSetInstruction set) {
        DefinitionUtils.checkDefinition(helper.getCameraPresetDefinitions(), set.getPreset());
        buf.writeIntLE(set.getPreset().getRuntimeId());

        helper.writeOptionalNull(buf, set.getEase(), this::writeEase);
        helper.writeOptionalNull(buf, set.getPos(), helper::writeVector3f);
        helper.writeOptionalNull(buf, set.getRot(), helper::writeVector2f);
        helper.writeOptionalNull(buf, set.getFacing(), helper::writeVector3f);
        helper.writeOptionalNull(buf, set.getViewOffset(), helper::writeVector2f);

        helper.writeOptional(buf, OptionalBoolean::isPresent, set.getDefaultPreset(),
                (b, optional) -> b.writeBoolean(optional.getAsBoolean()));
    }

    @Override
    protected CameraSetInstruction readSetInstruction(ByteBuf buf, BedrockCodecHelper helper) {
        int runtimeId = buf.readIntLE();
        NamedDefinition definition = helper.getCameraPresetDefinitions().getDefinition(runtimeId);
        Preconditions.checkNotNull(definition, "Unknown camera preset " + runtimeId);

        CameraSetInstruction.EaseData ease = helper.readOptional(buf, null, this::readEase);
        Vector3f pos = helper.readOptional(buf, null, helper::readVector3f);
        Vector2f rot = helper.readOptional(buf, null, helper::readVector2f);
        Vector3f facing = helper.readOptional(buf, null, helper::readVector3f);
        Vector2f viewOffset = helper.readOptional(buf, null, helper::readVector2f);
        OptionalBoolean defaultPreset = helper.readOptional(buf, OptionalBoolean.empty(), b -> OptionalBoolean.of(b.readBoolean()));
        return new CameraSetInstruction(definition, ease, pos, rot, facing, viewOffset, defaultPreset);
    }
}