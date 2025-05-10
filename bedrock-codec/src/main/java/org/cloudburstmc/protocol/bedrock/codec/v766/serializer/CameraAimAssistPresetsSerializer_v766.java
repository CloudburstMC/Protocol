package org.cloudburstmc.protocol.bedrock.codec.v766.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.camera.*;
import org.cloudburstmc.protocol.bedrock.packet.CameraAimAssistPresetsPacket;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CameraAimAssistPresetsSerializer_v766 implements BedrockPacketSerializer<CameraAimAssistPresetsPacket> {
    public static final CameraAimAssistPresetsSerializer_v766 INSTANCE = new CameraAimAssistPresetsSerializer_v766();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistPresetsPacket packet) {
        helper.writeArray(buffer, packet.getCategories(), this::writeCategories);
        helper.writeArray(buffer, packet.getPresets(), this::writePreset);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistPresetsPacket packet) {
        helper.readArray(buffer, packet.getCategories(), this::readCategories);
        helper.readArray(buffer, packet.getPresets(), this::readPreset);
    }

    protected void writeCategories(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistCategories categories) {
        helper.writeString(buffer, categories.getIdentifier());
        helper.writeArray(buffer, categories.getCategories(), this::writeCategory);
    }

    protected CameraAimAssistCategories readCategories(ByteBuf buffer, BedrockCodecHelper helper) {
        final CameraAimAssistCategories categories = new CameraAimAssistCategories();
        categories.setIdentifier(helper.readString(buffer));
        helper.readArray(buffer, categories.getCategories(), this::readCategory);
        return categories;
    }

    protected void writeCategory(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistCategory category) {
        helper.writeString(buffer, category.getName());

        helper.writeArray(buffer, category.getEntityPriorities(), this::writePriority);
        helper.writeArray(buffer, category.getBlockPriorities(), this::writePriority);

        helper.writeOptionalNull(buffer, category.getEntityDefaultPriorities(), ByteBuf::writeIntLE);
        helper.writeOptionalNull(buffer, category.getBlockDefaultPriorities(), ByteBuf::writeIntLE);
    }

    protected CameraAimAssistCategory readCategory(ByteBuf buffer, BedrockCodecHelper helper) {
        CameraAimAssistCategory category = new CameraAimAssistCategory();
        category.setName(helper.readString(buffer));

        helper.readArray(buffer, category.getEntityPriorities(), this::readPriority);
        helper.readArray(buffer, category.getBlockPriorities(), this::readPriority);

        category.setEntityDefaultPriorities(helper.readOptional(buffer, null, ByteBuf::readIntLE));
        category.setBlockDefaultPriorities(helper.readOptional(buffer, null, ByteBuf::readIntLE));
        return category;
    }

    protected void writePreset(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistPresetDefinition preset) {
        helper.writeString(buffer, preset.getIdentifier());
        helper.writeString(buffer, preset.getCategories());
        helper.writeArray(buffer, preset.getExclusionList(), helper::writeString);
        helper.writeArray(buffer, preset.getLiquidTargetingList(), helper::writeString);
        helper.writeArray(buffer, preset.getItemSettings(), this::writeItemSetting);
        helper.writeOptionalNull(buffer, preset.getDefaultItemSettings(), helper::writeString);
        helper.writeOptionalNull(buffer, preset.getHandSettings(), helper::writeString);
    }

    protected CameraAimAssistPresetDefinition readPreset(ByteBuf buffer, BedrockCodecHelper helper) {
        final CameraAimAssistPresetDefinition preset = new CameraAimAssistPresetDefinition();
        preset.setIdentifier(helper.readString(buffer));
        preset.setCategories(helper.readString(buffer));
        helper.readArray(buffer, preset.getExclusionList(), helper::readString);
        helper.readArray(buffer, preset.getLiquidTargetingList(), helper::readString);
        helper.readArray(buffer, preset.getItemSettings(), this::readItemSetting);
        preset.setDefaultItemSettings(helper.readOptional(buffer, null, helper::readString));
        preset.setHandSettings(helper.readOptional(buffer, null, helper::readString));
        return preset;
    }

    protected void writePriority(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistPriority priority) {
        helper.writeString(buffer, priority.getName());
        buffer.writeIntLE(priority.getPriority());
    }

    protected CameraAimAssistPriority readPriority(ByteBuf buffer, BedrockCodecHelper helper) {
        return new CameraAimAssistPriority(helper.readString(buffer), buffer.readIntLE());
    }

    protected void writeItemSetting(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistItemSettings settings) {
        helper.writeString(buffer, settings.getItemId());
        helper.writeString(buffer, settings.getCategory());
    }

    protected CameraAimAssistItemSettings readItemSetting(ByteBuf buffer, BedrockCodecHelper helper) {
        return new CameraAimAssistItemSettings(helper.readString(buffer), helper.readString(buffer));
    }
}