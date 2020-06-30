package com.nukkitx.protocol.bedrock.compat;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.GameRuleData;
import com.nukkitx.protocol.bedrock.data.command.CommandEnumData;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import com.nukkitx.protocol.bedrock.data.entity.EntityDataMap;
import com.nukkitx.protocol.bedrock.data.entity.EntityLinkData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemInstance;
import com.nukkitx.protocol.bedrock.data.skin.ImageData;
import com.nukkitx.protocol.bedrock.data.skin.SerializedSkin;
import com.nukkitx.protocol.bedrock.data.structure.StructureSettings;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class NoopBedrockPacketHelper extends BedrockPacketHelper {
    public static final NoopBedrockPacketHelper INSTANCE = new NoopBedrockPacketHelper();

    @Override
    protected void registerEntityData() {

    }

    @Override
    protected void registerEntityFlags() {

    }

    @Override
    protected void registerEntityDataTypes() {

    }

    @Override
    protected void registerEntityEvents() {

    }

    @Override
    protected void registerGameRuleTypes() {

    }

    @Override
    protected void registerSoundEvents() {

    }

    @Override
    protected void registerCommandParams() {

    }

    @Override
    protected void registerLevelEvents() {

    }

    @Override
    public EntityLinkData readEntityLink(ByteBuf buffer) {
        return null;
    }

    @Override
    public void writeEntityLink(ByteBuf buffer, EntityLinkData link) {

    }

    @Override
    public ItemInstance readItemInstance(ByteBuf buffer) {
        return null;
    }

    @Override
    public void writeItemInstance(ByteBuf buffer, ItemInstance itemInstance) {

    }

    @Override
    public ItemData readItem(ByteBuf buffer) {
        return null;
    }

    @Override
    public void writeItem(ByteBuf buffer, ItemData item) {

    }

    @Override
    public CommandOriginData readCommandOrigin(ByteBuf buffer) {
        return null;
    }

    @Override
    public void writeCommandOrigin(ByteBuf buffer, CommandOriginData commandOrigin) {

    }

    @Override
    public GameRuleData<?> readGameRule(ByteBuf buffer) {
        return null;
    }

    @Override
    public void writeGameRule(ByteBuf buffer, GameRuleData<?> gameRule) {

    }

    @Override
    public void readEntityData(ByteBuf buffer, EntityDataMap entityData) {

    }

    @Override
    public void writeEntityData(ByteBuf buffer, EntityDataMap entityData) {

    }

    @Override
    public CommandEnumData readCommandEnum(ByteBuf buffer, boolean soft) {
        return null;
    }

    @Override
    public void writeCommandEnum(ByteBuf buffer, CommandEnumData commandEnum) {

    }

    @Override
    public StructureSettings readStructureSettings(ByteBuf buffer) {
        return null;
    }

    @Override
    public void writeStructureSettings(ByteBuf buffer, StructureSettings settings) {

    }

    @Override
    public SerializedSkin readSkin(ByteBuf buffer) {
        return null;
    }

    @Override
    public void writeSkin(ByteBuf buffer, SerializedSkin skin) {

    }

    @Override
    public ImageData readImage(ByteBuf buffer) {
        return null;
    }

    @Override
    public void writeImage(ByteBuf buffer, ImageData image) {

    }
}
