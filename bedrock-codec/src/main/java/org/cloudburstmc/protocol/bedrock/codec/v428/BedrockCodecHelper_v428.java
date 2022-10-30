package org.cloudburstmc.protocol.bedrock.codec.v428;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v422.BedrockCodecHelper_v422;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.MineBlockStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import org.cloudburstmc.protocol.bedrock.data.skin.*;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class BedrockCodecHelper_v428 extends BedrockCodecHelper_v422 {

    public BedrockCodecHelper_v428(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes,
                                   TypeMap<StackRequestActionType> stackRequestActionTypes) {
        super(entityData, gameRulesTypes, stackRequestActionTypes);
    }

    @Override
    protected StackRequestActionData readRequestActionData(ByteBuf byteBuf, StackRequestActionType type) {
        StackRequestActionData action;
        if (type == StackRequestActionType.MINE_BLOCK) {
            action = new MineBlockStackRequestActionData(VarInts.readInt(byteBuf), VarInts.readInt(byteBuf), VarInts.readInt(byteBuf));
        } else {
            action = super.readRequestActionData(byteBuf, type);
        }
        return action;
    }

    @Override
    protected void writeRequestActionData(ByteBuf byteBuf, StackRequestActionData action) {
        if (action.getType() == StackRequestActionType.MINE_BLOCK) {
            VarInts.writeInt(byteBuf, ((MineBlockStackRequestActionData) action).getHotbarSlot());
            VarInts.writeInt(byteBuf, ((MineBlockStackRequestActionData) action).getPredictedDurability());
            VarInts.writeInt(byteBuf, ((MineBlockStackRequestActionData) action).getStackNetworkId());
        } else {
            super.writeRequestActionData(byteBuf, action);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public SerializedSkin readSkin(ByteBuf buffer) {
        String skinId = this.readString(buffer);
        String playFabId = this.readString(buffer); // new for v428
        String skinResourcePatch = this.readString(buffer);
        ImageData skinData = this.readImage(buffer);

        int animationCount = buffer.readIntLE();
        List<AnimationData> animations = new ObjectArrayList<>();
        for (int i = 0; i < animationCount; i++) {
            animations.add(this.readAnimationData(buffer));
        }

        ImageData capeData = this.readImage(buffer);
        String geometryData = this.readString(buffer);
        String animationData = this.readString(buffer);
        boolean premium = buffer.readBoolean();
        boolean persona = buffer.readBoolean();
        boolean capeOnClassic = buffer.readBoolean();
        String capeId = this.readString(buffer);
        String fullSkinId = this.readString(buffer);
        String armSize = this.readString(buffer);
        String skinColor = this.readString(buffer);

        List<PersonaPieceData> personaPieces = new ObjectArrayList<>();
        int piecesLength = buffer.readIntLE();
        for (int i = 0; i < piecesLength; i++) {
            String pieceId = this.readString(buffer);
            String pieceType = this.readString(buffer);
            String packId = this.readString(buffer);
            boolean isDefault = buffer.readBoolean();
            String productId = this.readString(buffer);
            personaPieces.add(new PersonaPieceData(pieceId, pieceType, packId, isDefault, productId));
        }

        List<PersonaPieceTintData> tintColors = new ObjectArrayList<>();
        int tintsLength = buffer.readIntLE();
        for (int i = 0; i < tintsLength; i++) {
            String pieceType = this.readString(buffer);
            List<String> colors = new ObjectArrayList<>();
            int colorsLength = buffer.readIntLE();
            for (int i2 = 0; i2 < colorsLength; i2++) {
                colors.add(this.readString(buffer));
            }
            tintColors.add(new PersonaPieceTintData(pieceType, colors));
        }

        return SerializedSkin.of(skinId, playFabId, skinResourcePatch, skinData, animations, capeData, geometryData, animationData,
                premium, persona, capeOnClassic, capeId, fullSkinId, armSize, skinColor, personaPieces, tintColors);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void writeSkin(ByteBuf buffer, SerializedSkin skin) {
        requireNonNull(skin, "Skin is null");

        this.writeString(buffer, skin.getSkinId());
        this.writeString(buffer, skin.getPlayFabId()); // new for v428
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
        this.writeString(buffer, skin.getArmSize());
        this.writeString(buffer, skin.getSkinColor());
        List<PersonaPieceData> pieces = skin.getPersonaPieces();
        buffer.writeIntLE(pieces.size());
        for (PersonaPieceData piece : pieces) {
            this.writeString(buffer, piece.getId());
            this.writeString(buffer, piece.getType());
            this.writeString(buffer, piece.getPackId());
            buffer.writeBoolean(piece.isDefault());
            this.writeString(buffer, piece.getProductId());
        }

        List<PersonaPieceTintData> tints = skin.getTintColors();
        buffer.writeIntLE(tints.size());
        for (PersonaPieceTintData tint : tints) {
            this.writeString(buffer, tint.getType());
            List<String> colors = tint.getColors();
            buffer.writeIntLE(colors.size());
            for (String color : colors) {
                this.writeString(buffer, color);
            }
        }
    }
}
