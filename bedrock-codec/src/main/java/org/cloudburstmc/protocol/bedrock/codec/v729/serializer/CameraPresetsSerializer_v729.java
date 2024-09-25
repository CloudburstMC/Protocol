package org.cloudburstmc.protocol.bedrock.codec.v729.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v712.serializer.CameraPresetsSerializer_v712;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAudioListener;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraPreset;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CameraPresetsSerializer_v729 extends CameraPresetsSerializer_v712 {
    public static final CameraPresetsSerializer_v729 INSTANCE = new CameraPresetsSerializer_v729();

    @Override
    public void writePreset(ByteBuf buffer, BedrockCodecHelper helper, CameraPreset preset) {
        helper.writeString(buffer, preset.getIdentifier());
        helper.writeString(buffer, preset.getParentPreset());
        helper.writeOptionalNull(buffer, preset.getPos(), (buf, pos) -> buf.writeFloatLE(pos.getX()));
        helper.writeOptionalNull(buffer, preset.getPos(), (buf, pos) -> buf.writeFloatLE(pos.getY()));
        helper.writeOptionalNull(buffer, preset.getPos(), (buf, pos) -> buf.writeFloatLE(pos.getZ()));
        helper.writeOptionalNull(buffer, preset.getPitch(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, preset.getYaw(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, preset.getRotationSpeed(), ByteBuf::writeFloatLE);
        helper.writeOptional(buffer, OptionalBoolean::isPresent, preset.getSnapToTarget(),
                (b, optional) -> b.writeBoolean(optional.getAsBoolean()));
        helper.writeOptionalNull(buffer, preset.getViewOffset(), helper::writeVector2f);
        helper.writeOptionalNull(buffer, preset.getEntityOffset(), helper::writeVector3f);
        helper.writeOptionalNull(buffer, preset.getRadius(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, preset.getListener(), (buf, listener) -> buf.writeByte(listener.ordinal()));
        helper.writeOptional(buffer, OptionalBoolean::isPresent, preset.getPlayEffect(),
                (buf, optional) -> buf.writeBoolean(optional.getAsBoolean()));
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

        Vector2f viewOffset = helper.readOptional(buffer, null, helper::readVector2f);
        Vector3f entityOffset = helper.readOptional(buffer, null, helper::readVector3f);
        Float radius = helper.readOptional(buffer, null, ByteBuf::readFloatLE);

        CameraAudioListener listener = helper.readOptional(buffer, null, buf -> CameraAudioListener.values()[buf.readUnsignedByte()]);
        OptionalBoolean effects = helper.readOptional(buffer, OptionalBoolean.empty(), buf -> OptionalBoolean.of(buf.readBoolean()));
        return new CameraPreset(identifier, parentPreset, pos, yaw, pitch, viewOffset, radius, listener, effects, rotationSpeed, snapToTarget, entityOffset, null, null, null, null);
    }
}