package org.cloudburstmc.protocol.bedrock.codec.v2225;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v2193.BedrockCodecHelper_v2193;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.PassengerOfBlockArguments;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.data.skin.*;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public class BedrockCodecHelper_v2225 extends BedrockCodecHelper_v2193 {

    public BedrockCodecHelper_v2225(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes, TypeMap<ItemStackRequestActionType> stackRequestActionTypes,
                                    TypeMap<ContainerSlotType> containerSlotTypes, TypeMap<Ability> abilities, TypeMap<TextProcessingEventOrigin> textProcessingEventOrigins) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes, abilities, textProcessingEventOrigins);
    }

    @Override
    public void writePassengerOfBlockArguments(ByteBuf buf, BedrockCodecHelper bedrockCodecHelper, PassengerOfBlockArguments passengerOfBlockArguments) {
        writeVector3i(buf, passengerOfBlockArguments.getBlockPos());
        writeVector3f(buf, passengerOfBlockArguments.getOffset());
        buf.writeFloatLE(passengerOfBlockArguments.getRotation());
        buf.writeFloatLE(passengerOfBlockArguments.getRotationLimit());
        buf.writeByte(passengerOfBlockArguments.getEmoteType().ordinal());
    }

    @Override
    public PassengerOfBlockArguments readPassengerOfBlockArguments(ByteBuf buf, BedrockCodecHelper bedrockCodecHelper) {
        return new PassengerOfBlockArguments(
                readVector3i(buf),
                readVector3f(buf),
                buf.readFloatLE(),
                buf.readFloatLE(),
                PassengerOfBlockArguments.EmoteType.values()[buf.readUnsignedByte()]
        );
    }

    @Override
    public SerializedSkin readSkin(ByteBuf buffer) {
        String skinId = this.readString(buffer);
        // playFabId removed
        String skinResourcePatch = this.readString(buffer);
        ImageData skinData = this.readImage(buffer, ImageData.SKIN_PERSONA_SIZE);

        List<AnimationData> animations = new ObjectArrayList<>();
        this.readArray(buffer, animations, (b, h) -> this.readAnimationData(b));

        ImageData capeData = this.readImage(buffer, ImageData.SINGLE_SKIN_SIZE);
        String geometryData = this.readStringMaxLen(buffer, this.encodingSettings.maxGeometryDataSize());
        String geometryDataEngineVersion = this.readString(buffer);
        String animationData = this.readString(buffer);
        String capeId = this.readString(buffer);
        String fullSkinId = this.readString(buffer);

        String armSize = buffer.readUnsignedByte() == 1 ? "wide" : "slim";
        Color color = new Color(buffer.readIntLE(), true);

        List<PersonaPieceData> personaPieces = new ObjectArrayList<>();
        this.readArray(buffer, personaPieces, (buf, h) -> {
            String pieceId = this.readString(buf);
            PersonaPieceType pieceType = PersonaPieceType.values()[buf.readIntLE()];
            UUID packId = this.readUuid(buf);
            boolean isDefault = buf.readBoolean();
            String productId = this.readString(buf);
            return new PersonaPieceData(pieceId, pieceType, packId, isDefault, productId);
        });

        List<PersonaPieceTintData> tintColors = new ObjectArrayList<>();
        this.readArray(buffer, tintColors, (buf, h) -> {
            PersonaPieceType pieceType = PersonaPieceType.fromName(this.readString(buf));
            List<Color> colors = new ArrayList<>(4);
            for (int i = 0; i < 4; i++) {
                colors.add(new Color(buf.readIntLE(), true));
            }
            return new PersonaPieceTintData(pieceType, colors);
        });

        boolean premium = buffer.readBoolean();
        boolean persona = buffer.readBoolean();
        boolean capeOnClassic = buffer.readBoolean();
        boolean primaryUser = buffer.readBoolean();
        boolean overridingPlayerAppearance = buffer.readBoolean();

        boolean trusted = "true".equalsIgnoreCase(this.readString(buffer));
        String profileHash = this.readString(buffer);

        return SerializedSkin.of(skinId, "", skinResourcePatch, skinData, animations, capeData, geometryData, geometryDataEngineVersion,
                animationData, premium, persona, capeOnClassic, primaryUser, capeId, fullSkinId, armSize, color, personaPieces, tintColors,
                overridingPlayerAppearance, trusted, profileHash);
    }

    @Override
    public void writeSkin(ByteBuf buffer, SerializedSkin skin) {
        requireNonNull(skin, "Skin is null");

        this.writeString(buffer, skin.getSkinId());
        // playFabId removed
        this.writeString(buffer, skin.getSkinResourcePatch());
        this.writeImage(buffer, skin.getSkinData());

        List<AnimationData> animations = skin.getAnimations();
        VarInts.writeUnsignedInt(buffer, animations.size());
        for (AnimationData animation : animations) {
            this.writeAnimationData(buffer, animation);
        }

        this.writeImage(buffer, skin.getCapeData());
        this.writeString(buffer, skin.getGeometryData());
        this.writeString(buffer, skin.getGeometryDataEngineVersion());
        this.writeString(buffer, skin.getAnimationData());
        this.writeString(buffer, skin.getCapeId());
        this.writeString(buffer, skin.getFullSkinId());

        buffer.writeByte("slim".equalsIgnoreCase(skin.getArmSize()) ? 0 : 1);
        buffer.writeIntLE(skin.getColor().getRGB());

        List<PersonaPieceData> pieces = skin.getPersonaPieces();
        VarInts.writeUnsignedInt(buffer, pieces.size());
        for (PersonaPieceData piece : pieces) {
            this.writeString(buffer, piece.getId());
            buffer.writeIntLE(piece.getPieceType().ordinal());
            this.writeUuid(buffer, piece.getPackUuid());
            buffer.writeBoolean(piece.isDefault());
            this.writeString(buffer, piece.getProductId());
        }

        List<PersonaPieceTintData> tints = skin.getTintColors();
        VarInts.writeUnsignedInt(buffer, tints.size());
        for (PersonaPieceTintData tint : tints) {
            this.writeString(buffer, tint.getType());
            List<Color> colors = tint.getColorsNew();
            if (colors.size() != 4) {
                throw new IllegalArgumentException("Expected 4 colors in PersonaPieceTintData");
            }
            for (Color color : colors) {
                buffer.writeIntLE(color.getRGB());
            }
        }

        buffer.writeBoolean(skin.isPremium());
        buffer.writeBoolean(skin.isPersona());
        buffer.writeBoolean(skin.isCapeOnClassic());
        buffer.writeBoolean(skin.isPrimaryUser());

        buffer.writeBoolean(skin.isOverridingPlayerAppearance());

        this.writeString(buffer, Boolean.toString(skin.isTrusted()));
        this.writeString(buffer, skin.getProfileHash());
    }

    private static final Pattern AUX_SUFFIX = Pattern.compile(":[0-9]+$");

    @Override
    protected ItemDescriptor readItemDescriptor(ByteBuf buffer, ItemDescriptorType type) {
        ItemDescriptor descriptor;
        if (type != ItemDescriptorType.INVALID) {
            String desc = this.readString(buffer);
            type = ItemDescriptorType.fromName(desc);
        }

        switch (type) {
            case INVALID:
                int aux_ = VarInts.readInt(buffer);
                descriptor = InvalidDescriptor.INSTANCE;
                break;
            case DEFAULT:
                String id = AUX_SUFFIX.matcher(this.readString(buffer)).replaceFirst(""); // wtf why does it put aux here
                int aux = VarInts.readInt(buffer);
                ItemDefinition definition = this.itemDefinitions.getDefinition(id);
                if (definition == null && log.isDebugEnabled()) {
                    log.debug("No ItemDefinition for id {} aux {}, did proxy not set itemDefinitions?", id, aux);
                }
                descriptor = new DefaultDescriptor(definition, aux);
                break;
            case MOLANG:
                descriptor = new MolangDescriptor(this.readString(buffer), buffer.readShortLE());
                break;
            case ITEM_TAG:
                descriptor = new ItemTagDescriptor(this.readString(buffer));
                int aux__ = VarInts.readInt(buffer);
                break;
            default:
                throw new UnsupportedOperationException();
        }

        return descriptor;
    }
}

