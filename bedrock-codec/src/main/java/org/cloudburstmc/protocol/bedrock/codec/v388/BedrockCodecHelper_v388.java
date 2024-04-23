package org.cloudburstmc.protocol.bedrock.codec.v388;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v361.BedrockCodecHelper_v361;
import org.cloudburstmc.protocol.bedrock.data.skin.AnimatedTextureType;
import org.cloudburstmc.protocol.bedrock.data.skin.AnimationData;
import org.cloudburstmc.protocol.bedrock.data.skin.ImageData;
import org.cloudburstmc.protocol.bedrock.data.skin.SerializedSkin;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureAnimationMode;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureMirror;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureRotation;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureSettings;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class BedrockCodecHelper_v388 extends BedrockCodecHelper_v361 {

    protected static final AnimatedTextureType[] TEXTURE_TYPES = AnimatedTextureType.values();

    public BedrockCodecHelper_v388(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes) {
        super(entityData, gameRulesTypes);
    }

    @Override
    public SerializedSkin readSkin(ByteBuf buffer) {
        String skinId = this.readString(buffer);
        String skinResourcePatch = this.readString(buffer);
        ImageData skinData = this.readImage(buffer, ImageData.SKIN_PERSONA_SIZE);

        List<AnimationData> animations = new ObjectArrayList<>();
        this.readArray(buffer, animations, ByteBuf::readIntLE, (b, h) -> this.readAnimationData(b));

        ImageData capeData = this.readImage(buffer, ImageData.SINGLE_SKIN_SIZE);
        String geometryData = this.readString(buffer);
        String animationData = this.readString(buffer);
        boolean premium = buffer.readBoolean();
        boolean persona = buffer.readBoolean();
        boolean capeOnClassic = buffer.readBoolean();
        String capeId = this.readString(buffer);
        String fullSkinId = this.readString(buffer);

        return SerializedSkin.of(skinId, "", skinResourcePatch, skinData, animations, capeData, geometryData, animationData,
                premium, persona, capeOnClassic, capeId, fullSkinId);
    }

    @Override
    public void writeSkin(ByteBuf buffer, SerializedSkin skin) {
        requireNonNull(skin, "Skin is null");

        this.writeString(buffer, skin.getSkinId());
        this.writeString(buffer, skin.getSkinResourcePatch());
        this.writeImage(buffer, skin.getSkinData());

        List<AnimationData> animations = skin.getAnimations();
        buffer.writeIntLE(animations.size());
        for (AnimationData animation : animations) {
            this.writeAnimationData(buffer, animation);
        }

        this.writeImage(buffer, skin.getCapeData());
        this.writeString(buffer, skin.getGeometryData());
        this.writeString(buffer, skin.getAnimationData());
        buffer.writeBoolean(skin.isPremium());
        buffer.writeBoolean(skin.isPersona());
        buffer.writeBoolean(skin.isCapeOnClassic());
        this.writeString(buffer, skin.getCapeId());
        this.writeString(buffer, skin.getFullSkinId());
    }

    @Override
    public AnimationData readAnimationData(ByteBuf buffer) {
        ImageData image = this.readImage(buffer, ImageData.SKIN_128_128_SIZE);
        AnimatedTextureType type = TEXTURE_TYPES[buffer.readIntLE()];
        float frames = buffer.readFloatLE();
        return new AnimationData(image, type, frames);
    }

    @Override
    public void writeAnimationData(ByteBuf buffer, AnimationData animation) {
        this.writeImage(buffer, animation.getImage());
        buffer.writeIntLE(animation.getTextureType().ordinal());
        buffer.writeFloatLE(animation.getFrames());
    }

    @Override
    public ImageData readImage(ByteBuf buffer, int maxSize) {
        int width = buffer.readIntLE();
        int height = buffer.readIntLE();
        byte[] image = readByteArray(buffer, maxSize);
        return ImageData.of(width, height, image);
    }

    @Override
    public void writeImage(ByteBuf buffer, ImageData image) {
        requireNonNull(image, "image is null");

        buffer.writeIntLE(image.getWidth());
        buffer.writeIntLE(image.getHeight());
        writeByteArray(buffer, image.getImage());
    }

    @Override
    public StructureSettings readStructureSettings(ByteBuf buffer) {
        String paletteName = this.readString(buffer);
        boolean ignoringEntities = buffer.readBoolean();
        boolean ignoringBlocks = buffer.readBoolean();
        Vector3i size = this.readBlockPosition(buffer);
        Vector3i offset = this.readBlockPosition(buffer);
        long lastEditedByEntityId = VarInts.readLong(buffer);
        StructureRotation rotation = StructureRotation.from(buffer.readByte());
        StructureMirror mirror = StructureMirror.from(buffer.readByte());
        float integrityValue = buffer.readFloatLE();
        int integritySeed = buffer.readIntLE();
        Vector3f pivot = this.readVector3f(buffer);

        return new StructureSettings(paletteName, ignoringEntities, ignoringBlocks, true, size, offset, lastEditedByEntityId,
                rotation, mirror, StructureAnimationMode.NONE, 0f, integrityValue, integritySeed, pivot);
    }

    @Override
    public void writeStructureSettings(ByteBuf buffer, StructureSettings settings) {
        super.writeStructureSettings(buffer, settings);
        this.writeVector3f(buffer, settings.getPivot());
    }
}
