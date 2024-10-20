package org.cloudburstmc.protocol.bedrock.codec.v748.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v712.serializer.CameraInstructionSerializer_v712;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSetInstruction;
import org.cloudburstmc.protocol.common.NamedDefinition;
import org.cloudburstmc.protocol.common.util.DefinitionUtils;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;
import org.cloudburstmc.protocol.common.util.Preconditions;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CameraInstructionSerializer_v748 extends CameraInstructionSerializer_v712 {
    public static final CameraInstructionSerializer_v748 INSTANCE = new CameraInstructionSerializer_v748();

    @Override
    protected void writeSetInstruction(BedrockCodecHelper helper, ByteBuf buf, CameraSetInstruction set) {
        DefinitionUtils.checkDefinition(helper.getCameraPresetDefinitions(), set.getPreset());
        buf.writeIntLE(set.getPreset().getRuntimeId());

        helper.writeOptionalNull(buf, set.getEase(), this::writeEase);
        helper.writeOptionalNull(buf, set.getPos(), helper::writeVector3f);
        helper.writeOptionalNull(buf, set.getRot(), helper::writeVector2f);
        helper.writeOptionalNull(buf, set.getFacing(), helper::writeVector3f);
        helper.writeOptionalNull(buf, set.getViewOffset(), helper::writeVector2f);
        helper.writeOptionalNull(buf, set.getEntityOffset(), helper::writeVector3f);

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
        Vector3f entityOffset = helper.readOptional(buf, null, helper::readVector3f);
        OptionalBoolean defaultPreset = helper.readOptional(buf, OptionalBoolean.empty(), b -> OptionalBoolean.of(b.readBoolean()));
        return new CameraSetInstruction(definition, ease, pos, rot, facing, viewOffset, entityOffset, defaultPreset);
    }
}