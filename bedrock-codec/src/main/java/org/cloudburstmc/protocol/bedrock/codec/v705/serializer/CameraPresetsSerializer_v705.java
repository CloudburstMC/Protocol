package org.cloudburstmc.protocol.bedrock.codec.v705.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v618.serializer.CameraPresetsSerializer_v618;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAudioListener;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraPreset;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

import java.util.Optional;

public class CameraPresetsSerializer_v705 extends CameraPresetsSerializer_v618 {
    public static final CameraPresetsSerializer_v705 INSTANCE = new CameraPresetsSerializer_v705();

    @Override
    public void writePreset(ByteBuf buffer, BedrockCodecHelper helper, CameraPreset preset) {
        helper.writeString(buffer, preset.getIdentifier());
        helper.writeString(buffer, preset.getParentPreset());
        helper.writeOptionalNull(buffer, preset.getPos(), (buf, pos) -> buf.writeFloatLE(pos.getX()));
        helper.writeOptionalNull(buffer, preset.getPos(), (buf, pos) -> buf.writeFloatLE(pos.getY()));
        helper.writeOptionalNull(buffer, preset.getPos(), (buf, pos) -> buf.writeFloatLE(pos.getZ()));
        helper.writeOptionalNull(buffer, preset.getPitch(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, preset.getYaw(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, preset.getViewOffset(), (buf, viewOffset) -> {
            buf.writeFloatLE(viewOffset.getX());
            buf.writeFloatLE(viewOffset.getY());
        });
        helper.writeOptional(buffer, Optional::isPresent, preset.getRadius(), (buf, radius) -> buf.writeFloatLE(radius.get()));
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
        Vector2f viewOffset = helper.readOptional(buffer, null, buf -> Vector2f.from(buf.readFloatLE(), buf.readFloatLE()));
        Optional<Float> radius = helper.readOptional(buffer, Optional.empty(), buf -> Optional.of(buf.readFloatLE()));

        CameraAudioListener listener = helper.readOptional(buffer, null, buf -> CameraAudioListener.values()[buf.readUnsignedByte()]);
        OptionalBoolean effects = helper.readOptional(buffer, OptionalBoolean.empty(), buf -> OptionalBoolean.of(buf.readBoolean()));
        return new CameraPreset(identifier, parentPreset, pos, yaw, pitch, viewOffset, radius, listener, effects);
    }
}