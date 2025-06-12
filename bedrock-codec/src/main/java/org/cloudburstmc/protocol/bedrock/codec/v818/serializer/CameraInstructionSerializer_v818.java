package org.cloudburstmc.protocol.bedrock.codec.v818.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v748.serializer.CameraInstructionSerializer_v748;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSetInstruction;
import org.cloudburstmc.protocol.common.NamedDefinition;
import org.cloudburstmc.protocol.common.util.DefinitionUtils;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;
import org.cloudburstmc.protocol.common.util.Preconditions;

public class CameraInstructionSerializer_v818 extends CameraInstructionSerializer_v748 {

    @Override
    protected void writeSetInstruction(BedrockCodecHelper helper, ByteBuf buf, CameraSetInstruction set) {
        super.writeSetInstruction(helper, buf, set);

        buf.writeBoolean(set.isRemoveIgnoreStartingValues());
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
        boolean removeIgnoreStartingValues = buf.readBoolean();
        return new CameraSetInstruction(definition, ease, pos, rot, facing, viewOffset, entityOffset, defaultPreset,
                removeIgnoreStartingValues);
    }
}
