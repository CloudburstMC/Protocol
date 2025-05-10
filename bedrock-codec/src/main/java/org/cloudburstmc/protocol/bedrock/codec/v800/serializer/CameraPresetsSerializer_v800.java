package org.cloudburstmc.protocol.bedrock.codec.v800.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v776.serializer.CameraPresetsSerializer_v776;
import org.cloudburstmc.protocol.bedrock.data.ControlScheme;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAimAssistPreset;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAudioListener;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraPreset;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

public class CameraPresetsSerializer_v800 extends CameraPresetsSerializer_v776 {

    public static final CameraPresetsSerializer_v800 INSTANCE = new CameraPresetsSerializer_v800();

    private static final ControlScheme[] VALUES = ControlScheme.values();

    @Override
    public void writePreset(ByteBuf buffer, BedrockCodecHelper helper, CameraPreset preset) {
        super.writePreset(buffer, helper, preset);
        helper.writeOptionalNull(buffer, preset.getControlScheme(), (buf, scheme) -> buf.writeByte(scheme.ordinal()));
    }

    @Override
    public CameraPreset readPreset(ByteBuf buffer, BedrockCodecHelper helper) {
        String identifier = helper.readString(buffer);
        String parentPreset = helper.readString(buffer);

        Float x = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Float y = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Float z = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Vector3f pos = x == null || y == null || z == null ? null : Vector3f.from(x, y, z);

        Float pitch = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Float yaw = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Float rotationSpeed = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        OptionalBoolean snapToTarget = helper.readOptional(buffer, OptionalBoolean.empty(), buf -> OptionalBoolean.of(buf.readBoolean()));
        Vector2f horizontalRotationLimit = helper.readOptional(buffer, null, helper::readVector2f);
        Vector2f verticalRotationLimit = helper.readOptional(buffer, null, helper::readVector2f);
        OptionalBoolean continueTargeting = helper.readOptional(buffer, OptionalBoolean.empty(), buf -> OptionalBoolean.of(buf.readBoolean()));
        Float blockListeningRadius = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Vector2f viewOffset = helper.readOptional(buffer, null, helper::readVector2f);
        Vector3f entityOffset = helper.readOptional(buffer, null, helper::readVector3f);
        Float radius = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Float minYawLimit = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Float maxYawLimit = helper.readOptional(buffer, null, ByteBuf::readFloatLE);

        CameraAudioListener listener = helper.readOptional(buffer, null, buf -> CameraAudioListener.values()[buf.readUnsignedByte()]);
        OptionalBoolean effects = helper.readOptional(buffer, OptionalBoolean.empty(), buf -> OptionalBoolean.of(buf.readBoolean()));
        OptionalBoolean alignTargetAndCameraForward = helper.readOptional(buffer, OptionalBoolean.empty(), buf -> OptionalBoolean.of(buf.readBoolean()));
        CameraAimAssistPreset aimAssist = helper.readOptional(buffer, null, buf -> readCameraAimAssist(buf, helper));
        ControlScheme controlScheme = helper.readOptional(buffer, null, buf -> VALUES[buf.readUnsignedByte()]);

        return new CameraPreset(identifier, parentPreset, pos, yaw, pitch, viewOffset, radius, minYawLimit, maxYawLimit, listener, effects, rotationSpeed, snapToTarget, entityOffset, horizontalRotationLimit, verticalRotationLimit, continueTargeting, alignTargetAndCameraForward, blockListeningRadius, aimAssist, controlScheme);
    }
}
