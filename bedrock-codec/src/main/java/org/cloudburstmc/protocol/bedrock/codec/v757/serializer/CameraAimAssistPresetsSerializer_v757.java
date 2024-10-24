package org.cloudburstmc.protocol.bedrock.codec.v757.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.camera.aimassist.CameraAimAssistCategories;
import org.cloudburstmc.protocol.bedrock.data.camera.aimassist.CameraAimAssistPreset;
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

    protected void writeCategory(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistCategories.Category category) {
        helper.writeString(buffer, category.getName());
        this.writePriorities(buffer, helper, category.getPriorities());
    }

    protected CameraAimAssistCategories.Category readCategory(ByteBuf buffer, BedrockCodecHelper helper) {
        final CameraAimAssistCategories.Category category = new CameraAimAssistCategories.Category();
        category.setName(helper.readString(buffer));
        category.setPriorities(this.readPriorities(buffer, helper));
        return category;
    }

    protected void writePreset(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistPreset preset) {
        helper.writeString(buffer, preset.getIdentifier());
        helper.writeString(buffer, preset.getCategories());
        helper.writeArray(buffer, preset.getExclusionList(), helper::writeString);
        helper.writeArray(buffer, preset.getLiquidTargetingList(), helper::writeString);
        helper.writeArray(buffer, preset.getItemSettings(), (buf, h, itemSettings) -> {
            h.writeString(buf, itemSettings.getItemIdentifier());
            h.writeString(buf, itemSettings.getCategoryName());
        });
        helper.writeOptionalNull(buffer, preset.getDefaultItemSettings(), helper::writeString);
        helper.writeOptionalNull(buffer, preset.getHandSettings(), helper::writeString);
    }

    protected CameraAimAssistPreset readPreset(ByteBuf buffer, BedrockCodecHelper helper) {
        final CameraAimAssistPreset preset = new CameraAimAssistPreset();
        preset.setIdentifier(helper.readString(buffer));
        preset.setCategories(helper.readString(buffer));
        helper.readArray(buffer, preset.getExclusionList(), helper::readString);
        helper.readArray(buffer, preset.getLiquidTargetingList(), helper::readString);
        helper.readArray(buffer, preset.getItemSettings(), (buf, h) ->
                new CameraAimAssistPreset.ItemSettings(h.readString(buf), h.readString(buf)));
        preset.setDefaultItemSettings(helper.readOptional(buffer, null, helper::readString));
        preset.setHandSettings(helper.readOptional(buffer, null, helper::readString));
        return preset;
    }

    protected void writePriorities(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistCategories.Category.Priorities priorities) {
        helper.writeArray(buffer, priorities.getEntities(), (buf, h, entity) -> {
            h.writeString(buf, entity.getIdentifier());
            buf.writeIntLE(entity.getEntityDefault());
        });
        helper.writeArray(buffer, priorities.getBlocks(), (buf, h, block) -> {
            h.writeString(buf, block.getIdentifier());
            buf.writeIntLE(block.getBlockDefault());
        });
        helper.writeOptionalNull(buffer, priorities.getEntityDefault(), ByteBuf::writeIntLE);
        helper.writeOptionalNull(buffer, priorities.getBlockDefault(), ByteBuf::writeIntLE);
    }

    protected CameraAimAssistCategories.Category.Priorities readPriorities(ByteBuf buffer, BedrockCodecHelper helper) {
        final CameraAimAssistCategories.Category.Priorities priorities = new CameraAimAssistCategories.Category.Priorities();
        helper.readArray(buffer, priorities.getEntities(), (buf, h) -> {
            final String identifier = h.readString(buf);
            final int entityDefault = buf.readIntLE();
            return new CameraAimAssistCategories.Category.Priorities.Entity(identifier, entityDefault);
        });
        helper.readArray(buffer, priorities.getBlocks(), (buf, h) -> {
            final String identifier = h.readString(buf);
            final int blockDefault = buf.readIntLE();
            return new CameraAimAssistCategories.Category.Priorities.Block(identifier, blockDefault);
        });
        priorities.setEntityDefault(helper.readOptional(buffer, null, ByteBuf::readIntLE));
        priorities.setBlockDefault(helper.readOptional(buffer, null, ByteBuf::readIntLE));
        return priorities;
    }
}