package org.cloudburstmc.protocol.bedrock.codec.v757.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAimAssistCategoriesDefinition;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAimAssistCategoryDefinition;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAimAssistCategoryPriorities;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAimAssistPresetDefinition;
import org.cloudburstmc.protocol.bedrock.packet.CameraAimAssistPresetsPacket;

public class CameraAimAssistPresetsSerializer_v757 implements BedrockPacketSerializer<CameraAimAssistPresetsPacket> {
    public static final CameraAimAssistPresetsSerializer_v757 INSTANCE = new CameraAimAssistPresetsSerializer_v757();

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

    protected void writeCategories(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistCategoriesDefinition categories) {
        helper.writeString(buffer, categories.getIdentifier());
        helper.writeArray(buffer, categories.getCategories(), this::writeCategory);
    }

    protected CameraAimAssistCategoriesDefinition readCategories(ByteBuf buffer, BedrockCodecHelper helper) {
        final CameraAimAssistCategoriesDefinition categories = new CameraAimAssistCategoriesDefinition();
        categories.setIdentifier(helper.readString(buffer));
        helper.readArray(buffer, categories.getCategories(), this::readCategory);
        return categories;
    }

    protected void writeCategory(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistCategoryDefinition category) {
        helper.writeString(buffer, category.getName());
        helper.writeArray(buffer, category.getPriorities(), this::writePriorities);
    }

    protected CameraAimAssistCategoryDefinition readCategory(ByteBuf buffer, BedrockCodecHelper helper) {
        final CameraAimAssistCategoryDefinition category = new CameraAimAssistCategoryDefinition();
        category.setName(helper.readString(buffer));
        helper.readArray(buffer, category.getPriorities(), this::readPriorities);
        return category;
    }

    protected void writePreset(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistPresetDefinition preset) {
        helper.writeString(buffer, preset.getIdentifier());
        helper.writeString(buffer, preset.getCategories());
        helper.writeArray(buffer, preset.getExclusionList(), helper::writeString);
        helper.writeArray(buffer, preset.getLiquidTargetingList(), helper::writeString);
        helper.writeArray(buffer, preset.getItemSettings(), helper::writeString);
        helper.writeOptionalNull(buffer, preset.getDefaultItemSettings(), helper::writeString);
        helper.writeOptionalNull(buffer, preset.getHandSettings(), helper::writeString);
    }

    protected CameraAimAssistPresetDefinition readPreset(ByteBuf buffer, BedrockCodecHelper helper) {
        final CameraAimAssistPresetDefinition preset = new CameraAimAssistPresetDefinition();
        preset.setIdentifier(helper.readString(buffer));
        preset.setCategories(helper.readString(buffer));
        helper.readArray(buffer, preset.getExclusionList(), helper::readString);
        helper.readArray(buffer, preset.getLiquidTargetingList(), helper::readString);
        helper.readArray(buffer, preset.getItemSettings(), helper::readString);
        preset.setDefaultItemSettings(helper.readOptional(buffer, null, helper::readString));
        preset.setHandSettings(helper.readOptional(buffer, null, helper::readString));
        return preset;
    }

    protected void writePriorities(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistCategoryPriorities priorities) {
        buffer.writeByte(priorities.getEntities());
        buffer.writeByte(priorities.getBlocks());
        buffer.writeByte(priorities.getEntityDefault());
        buffer.writeByte(priorities.getBlockDefault());
    }

    protected CameraAimAssistCategoryPriorities readPriorities(ByteBuf buffer, BedrockCodecHelper helper) {
        final CameraAimAssistCategoryPriorities priorities = new CameraAimAssistCategoryPriorities();
        priorities.setEntities(buffer.readUnsignedByte());
        priorities.setBlocks(buffer.readUnsignedByte());
        priorities.setEntityDefault(buffer.readUnsignedByte());
        priorities.setBlockDefault(buffer.readUnsignedByte());
        return priorities;
    }
}