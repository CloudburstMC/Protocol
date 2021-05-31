package org.cloudburstmc.protocol.bedrock.codec.compat;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BaseBedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.data.GameRuleData;
import org.cloudburstmc.protocol.bedrock.data.command.CommandEnumData;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOriginData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityLinkData;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.skin.SerializedSkin;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureSettings;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class NoopBedrockCodecHelper extends BaseBedrockCodecHelper {
    public static final NoopBedrockCodecHelper INSTANCE = new NoopBedrockCodecHelper();

    @Override
    public EntityLinkData readEntityLink(ByteBuf buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeEntityLink(ByteBuf buffer, EntityLinkData link) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ItemData readNetItem(ByteBuf buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeNetItem(ByteBuf buffer, ItemData item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ItemData readItem(ByteBuf buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeItem(ByteBuf buffer, ItemData item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ItemData readItemInstance(ByteBuf buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeItemInstance(ByteBuf buffer, ItemData item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CommandOriginData readCommandOrigin(ByteBuf buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeCommandOrigin(ByteBuf buffer, CommandOriginData commandOrigin) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GameRuleData<?> readGameRule(ByteBuf buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeGameRule(ByteBuf buffer, GameRuleData<?> gameRule) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void readEntityData(ByteBuf buffer, EntityDataMap entityData) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeEntityData(ByteBuf buffer, EntityDataMap entityData) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CommandEnumData readCommandEnum(ByteBuf buffer, boolean soft) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeCommandEnum(ByteBuf buffer, CommandEnumData commandEnum) {
        throw new UnsupportedOperationException();
    }

    @Override
    public StructureSettings readStructureSettings(ByteBuf buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeStructureSettings(ByteBuf buffer, StructureSettings settings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SerializedSkin readSkin(ByteBuf buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeSkin(ByteBuf buffer, SerializedSkin skin) {
        throw new UnsupportedOperationException();
    }
}
