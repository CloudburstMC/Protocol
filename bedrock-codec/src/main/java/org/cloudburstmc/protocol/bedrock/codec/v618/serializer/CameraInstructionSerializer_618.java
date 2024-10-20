package org.cloudburstmc.protocol.bedrock.codec.v618.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraFadeInstruction;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSetInstruction;
import org.cloudburstmc.protocol.bedrock.packet.CameraInstructionPacket;
import org.cloudburstmc.protocol.common.NamedDefinition;
import org.cloudburstmc.protocol.common.util.DefinitionUtils;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;
import org.cloudburstmc.protocol.common.util.Preconditions;

import java.awt.*;

public class CameraInstructionSerializer_618 implements BedrockPacketSerializer<CameraInstructionPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraInstructionPacket packet) {
        helper.writeOptionalNull(buffer, packet.getSetInstruction(), (buf, set) -> this.writeSetInstruction(helper, buf, set));
        helper.writeOptional(buffer, OptionalBoolean::isPresent, packet.getClear(),
                (b, optional) -> b.writeBoolean(optional.getAsBoolean()));

        helper.writeOptionalNull(buffer, packet.getFadeInstruction(), (buf, fade) -> {
            helper.writeOptionalNull(buf, fade.getTimeData(), this::writeTimeData);
            helper.writeOptionalNull(buf, fade.getColor(), this::writeColor);
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraInstructionPacket packet) {
        CameraSetInstruction set = helper.readOptional(buffer, null, buf -> this.readSetInstruction(buf, helper));

        packet.setSetInstruction(set);
        packet.setClear(helper.readOptional(buffer, OptionalBoolean.empty(), buf -> OptionalBoolean.of(buf.readBoolean())));

        CameraFadeInstruction fade = helper.readOptional(buffer, null, buf -> {
            CameraFadeInstruction.TimeData time = helper.readOptional(buf, null, this::readTimeData);
            Color color = helper.readOptional(buf, null, this::readColor);
            return new CameraFadeInstruction(time, color);
        });

        packet.setFadeInstruction(fade);
    }

    protected void writeEase(ByteBuf buffer, CameraSetInstruction.EaseData ease) {
        buffer.writeByte(ease.getEaseType().ordinal());
        buffer.writeFloatLE(ease.getTime());
    }

    protected CameraSetInstruction.EaseData readEase(ByteBuf buffer) {
        CameraEase type = CameraEase.values()[buffer.readUnsignedByte()];
        float time = buffer.readFloatLE();
        return new CameraSetInstruction.EaseData(type, time);
    }

    protected void writeTimeData(ByteBuf buffer, CameraFadeInstruction.TimeData timeData) {
        buffer.writeFloatLE(timeData.getFadeInTime());
        buffer.writeFloatLE(timeData.getWaitTime());
        buffer.writeFloatLE(timeData.getFadeOutTime());
    }

    protected CameraFadeInstruction.TimeData readTimeData(ByteBuf buffer) {
        float fadeIn = buffer.readFloatLE();
        float wait = buffer.readFloatLE();
        float fadeOut = buffer.readFloatLE();
        return new CameraFadeInstruction.TimeData(fadeIn, wait, fadeOut);
    }

    protected void writeColor(ByteBuf buffer, Color color) {
        buffer.writeFloatLE(color.getRed() / 255F);
        buffer.writeFloatLE(color.getGreen() / 255F);
        buffer.writeFloatLE(color.getBlue() / 255F);
    }

    protected Color readColor(ByteBuf buffer) {
        return new Color(
                (int) (buffer.readFloatLE() * 255),
                (int) (buffer.readFloatLE() * 255),
                (int) (buffer.readFloatLE() * 255)
        );
    }

    protected void writeSetInstruction(BedrockCodecHelper helper, ByteBuf buf, CameraSetInstruction set) {
        DefinitionUtils.checkDefinition(helper.getCameraPresetDefinitions(), set.getPreset());
        buf.writeIntLE(set.getPreset().getRuntimeId());

        helper.writeOptionalNull(buf, set.getEase(), this::writeEase);
        helper.writeOptionalNull(buf, set.getPos(), helper::writeVector3f);
        helper.writeOptionalNull(buf, set.getRot(), helper::writeVector2f);
        helper.writeOptionalNull(buf, set.getFacing(), helper::writeVector3f);

        helper.writeOptional(buf, OptionalBoolean::isPresent, set.getDefaultPreset(),
                (b, optional) -> b.writeBoolean(optional.getAsBoolean()));
    }

    protected CameraSetInstruction readSetInstruction(ByteBuf buf, BedrockCodecHelper helper) {
        int runtimeId = buf.readIntLE();
        NamedDefinition definition = helper.getCameraPresetDefinitions().getDefinition(runtimeId);
        Preconditions.checkNotNull(definition, "Unknown camera preset " + runtimeId);

        CameraSetInstruction.EaseData ease = helper.readOptional(buf, null, this::readEase);
        Vector3f pos = helper.readOptional(buf, null, helper::readVector3f);
        Vector2f rot = helper.readOptional(buf, null, helper::readVector2f);
        Vector3f facing = helper.readOptional(buf, null, helper::readVector3f);
        OptionalBoolean defaultPreset = helper.readOptional(buf, OptionalBoolean.empty(), b -> OptionalBoolean.of(b.readBoolean()));
        return new CameraSetInstruction(definition, ease, pos, rot, facing, null, null, defaultPreset);
    }
}
