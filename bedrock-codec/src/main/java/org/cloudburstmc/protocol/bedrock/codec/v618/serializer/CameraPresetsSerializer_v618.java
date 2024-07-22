package org.cloudburstmc.protocol.bedrock.codec.v618.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAudioListener;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraPreset;
import org.cloudburstmc.protocol.bedrock.packet.CameraPresetsPacket;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

public class CameraPresetsSerializer_v618 implements BedrockPacketSerializer<CameraPresetsPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraPresetsPacket packet) {
        helper.writeArray(buffer, packet.getPresets(), this::writePreset);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraPresetsPacket packet) {
        helper.readArray(buffer, packet.getPresets(), this::readPreset);
    }

    public void writePreset(ByteBuf buffer, BedrockCodecHelper helper, CameraPreset preset) {
        helper.writeString(buffer, preset.getIdentifier());
        helper.writeString(buffer, preset.getParentPreset());
        helper.writeOptionalNull(buffer, preset.getPos(), (buf, pos) -> buf.writeFloatLE(pos.getX()));
        helper.writeOptionalNull(buffer, preset.getPos(), (buf, pos) -> buf.writeFloatLE(pos.getY()));
        helper.writeOptionalNull(buffer, preset.getPos(), (buf, pos) -> buf.writeFloatLE(pos.getZ()));
        helper.writeOptionalNull(buffer, preset.getPitch(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, preset.getYaw(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, preset.getListener(), (buf, listener) -> buf.writeByte(listener.ordinal()));
        helper.writeOptional(buffer, OptionalBoolean::isPresent, preset.getPlayEffect(),
                (buf, optional) -> buf.writeBoolean(optional.getAsBoolean()));
    }

    public CameraPreset readPreset(ByteBuf buffer, BedrockCodecHelper helper) {
        String identifier = helper.readString(buffer);
        String parentPreset = helper.readString(buffer);

        Float x = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Float y = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Float z = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Vector3f pos = x == null || y == null || z == null ? null : Vector3f.from(x, y, z);

        Float pitch = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Float yaw = helper.readOptional(buffer, null, ByteBuf::readFloatLE);

        CameraAudioListener listener = helper.readOptional(buffer, null, buf -> CameraAudioListener.values()[buf.readUnsignedByte()]);
        OptionalBoolean effects = helper.readOptional(buffer, OptionalBoolean.empty(), buf -> OptionalBoolean.of(buf.readBoolean()));
        return new CameraPreset(identifier, parentPreset, pos, yaw, pitch, null, null, listener, effects);
    }
}
