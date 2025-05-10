package org.cloudburstmc.protocol.bedrock.codec.v776.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.codec.v766.serializer.CameraAimAssistPresetsSerializer_v766;
import org.cloudburstmc.protocol.bedrock.data.camera.*;
import org.cloudburstmc.protocol.bedrock.packet.CameraAimAssistPresetsPacket;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CameraAimAssistPresetsSerializer_v776 extends CameraAimAssistPresetsSerializer_v766 {
    public static final CameraAimAssistPresetsSerializer_v776 INSTANCE = new CameraAimAssistPresetsSerializer_v776();

    private static final CameraAimAssistOperation[] OPERATIONS = CameraAimAssistOperation.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistPresetsPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeByte(packet.getOperation().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistPresetsPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setOperation(OPERATIONS[buffer.readUnsignedByte()]);
    }

    @Override
    protected void writePreset(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistPresetDefinition preset) {
        helper.writeString(buffer, preset.getIdentifier());
        helper.writeArray(buffer, preset.getExclusionList(), helper::writeString);
        helper.writeArray(buffer, preset.getLiquidTargetingList(), helper::writeString);
        helper.writeArray(buffer, preset.getItemSettings(), this::writeItemSetting);
        helper.writeOptionalNull(buffer, preset.getDefaultItemSettings(), helper::writeString);
        helper.writeOptionalNull(buffer, preset.getHandSettings(), helper::writeString);
    }

    @Override
    protected CameraAimAssistPresetDefinition readPreset(ByteBuf buffer, BedrockCodecHelper helper) {
        final CameraAimAssistPresetDefinition preset = new CameraAimAssistPresetDefinition();
        preset.setIdentifier(helper.readString(buffer));
        helper.readArray(buffer, preset.getExclusionList(), helper::readString);
        helper.readArray(buffer, preset.getLiquidTargetingList(), helper::readString);
        helper.readArray(buffer, preset.getItemSettings(), this::readItemSetting);
        preset.setDefaultItemSettings(helper.readOptional(buffer, null, helper::readString));
        preset.setHandSettings(helper.readOptional(buffer, null, helper::readString));
        return preset;
    }
}