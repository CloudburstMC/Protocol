package org.cloudburstmc.protocol.bedrock.data.skin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.*;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.JSONValue;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

@Getter
@ToString(exclude = {"geometryData"})
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, builderClassName = "Builder")
public class SerializedSkin {
    private static final int PIXEL_SIZE = 4;

    public static final int SINGLE_SKIN_SIZE = 64 * 32 * PIXEL_SIZE;
    public static final int DOUBLE_SKIN_SIZE = 64 * 64 * PIXEL_SIZE;
    public static final int SKIN_128_64_SIZE = 128 * 64 * PIXEL_SIZE;
    public static final int SKIN_128_128_SIZE = 128 * 128 * PIXEL_SIZE;

    private static final Color DEFAULT_COLOR = new Color(0, true);

    private String skinId;
    /**
     * @since v428
     */
    @Builder.Default
    private String playFabId = "";
    private String geometryName;
    private String skinResourcePatch;
    private ImageData skinData;
    @Builder.Default
    private List<AnimationData> animations = Collections.emptyList();
    @Builder.Default
    private ImageData capeData = ImageData.EMPTY;
    private String geometryData;
    /**
     * @since v465
     */
    @Builder.Default
    private String geometryDataEngineVersion = "0.0.0";
    @Builder.Default
    private String animationData = "";
    private boolean premium;
    private boolean persona;
    private boolean capeOnClassic;
    /**
     * @since v465
     */
    private boolean primaryUser;
    @Builder.Default
    private String capeId = "";
    private String fullSkinId;
    @Builder.Default
    private String armSize = "wide";
    /**
     * @deprecated since v2168
     */
    private String skinColor;
    /**
     * @since v2168
     */
    @Builder.Default
    private Color color = DEFAULT_COLOR;
    @Builder.Default
    private List<PersonaPieceData> personaPieces = Collections.emptyList();
    @Builder.Default
    private List<PersonaPieceTintData> tintColors = Collections.emptyList();
    private boolean overridingPlayerAppearance;
    /**
     * @since v2168
     */
    @Builder.Default
    private boolean trusted = true;
    /**
     * @since v2168
     */
    @Builder.Default
    private String profileHash = "";

    public static SerializedSkin of(String skinId, String playFabId, ImageData skinData, ImageData capeData, String geometryName,
                                    String geometryData, boolean premiumSkin) {
        skinData.checkLegacySkinSize();
        capeData.checkLegacyCapeSize();

        return new SerializedSkin(skinId, playFabId, geometryName, null, skinData, Collections.emptyList(), capeData,
                geometryData, "0.0.0", "", premiumSkin, false, false, true, "", "",
                "wide", null, DEFAULT_COLOR, Collections.emptyList(), Collections.emptyList(), true, true, "");
    }

    public static SerializedSkin of(String skinId, String playFabId, String skinResourcePatch, ImageData skinData,
                                    List<AnimationData> animations, ImageData capeData, String geometryData,
                                    String animationData, boolean premium, boolean persona, boolean capeOnClassic,
                                    String capeId, String fullSkinId) {
        return of(skinId, playFabId, skinResourcePatch, skinData, Collections.unmodifiableList(new ObjectArrayList<>(animations)),
                capeData, geometryData, animationData, premium, persona, capeOnClassic, capeId, fullSkinId,
                "wide", "#0", Collections.emptyList(), Collections.emptyList());
    }

    public static SerializedSkin of(String skinId, String playFabId, String skinResourcePatch, ImageData skinData,
                                    List<AnimationData> animations, ImageData capeData, String geometryData,
                                    String animationData, boolean premium, boolean persona, boolean capeOnClassic,
                                    String capeId, String fullSkinId, String armSize, String skinColor,
                                    List<PersonaPieceData> personaPieces, List<PersonaPieceTintData> tintColors) {
        return of(skinId, playFabId, skinResourcePatch, skinData, animations, capeData, geometryData, animationData, premium, persona, capeOnClassic, true, capeId, fullSkinId, armSize, skinColor, personaPieces, tintColors);
    }

    public static SerializedSkin of(String skinId, String playFabId, String skinResourcePatch, ImageData skinData,
                                    List<AnimationData> animations, ImageData capeData, String geometryData,
                                    String animationData, boolean premium, boolean persona, boolean capeOnClassic,
                                    boolean primaryUser, String capeId, String fullSkinId, String armSize,
                                    String skinColor, List<PersonaPieceData> personaPieces,
                                    List<PersonaPieceTintData> tintColors) {
        return new SerializedSkin(skinId, playFabId, null, skinResourcePatch, skinData,
                Collections.unmodifiableList(new ObjectArrayList<>(animations)), capeData, geometryData, "0.0.0", animationData,
                premium, persona, capeOnClassic, primaryUser, capeId, fullSkinId, armSize, skinColor, null, personaPieces, tintColors, true, true, "");
    }

    public static SerializedSkin of(String skinId, String playFabId, String skinResourcePatch, ImageData skinData,
                                    List<AnimationData> animations, ImageData capeData, String geometryData,
                                    String geometryDataEngineVersion, String animationData, boolean premium,
                                    boolean persona, boolean capeOnClassic, boolean primaryUser, String capeId,
                                    String fullSkinId, String armSize, String skinColor, List<PersonaPieceData> personaPieces,
                                    List<PersonaPieceTintData> tintColors) {

        return new SerializedSkin(skinId, playFabId, null, skinResourcePatch, skinData,
                Collections.unmodifiableList(new ObjectArrayList<>(animations)), capeData, geometryData, geometryDataEngineVersion, animationData,
                premium, persona, capeOnClassic, primaryUser, capeId, fullSkinId, armSize, skinColor, null, personaPieces, tintColors, true, true, "");
    }

    public static SerializedSkin of(String skinId, String playFabId, String skinResourcePatch, ImageData skinData,
                                    List<AnimationData> animations, ImageData capeData, String geometryData,
                                    String geometryDataEngineVersion, String animationData, boolean premium,
                                    boolean persona, boolean capeOnClassic, boolean primaryUser, String capeId,
                                    String fullSkinId, String armSize, String skinColor, List<PersonaPieceData> personaPieces,
                                    List<PersonaPieceTintData> tintColors, boolean overridingPlayerAppearance) {

        return new SerializedSkin(skinId, playFabId, null, skinResourcePatch, skinData,
                Collections.unmodifiableList(new ObjectArrayList<>(animations)), capeData, geometryData, geometryDataEngineVersion, animationData,
                premium, persona, capeOnClassic, primaryUser, capeId, fullSkinId, armSize, skinColor, null, personaPieces, tintColors, overridingPlayerAppearance, true, "");
    }

    public static SerializedSkin of(String skinId, String playFabId, String skinResourcePatch, ImageData skinData,
                                    List<AnimationData> animations, ImageData capeData, String geometryData,
                                    String geometryDataEngineVersion, String animationData, boolean premium,
                                    boolean persona, boolean capeOnClassic, boolean primaryUser, String capeId,
                                    String fullSkinId, String armSize, Color color, List<PersonaPieceData> personaPieces,
                                    List<PersonaPieceTintData> tintColors, boolean overridingPlayerAppearance, boolean trusted, String profileHash) {

        return new SerializedSkin(skinId, playFabId, null, skinResourcePatch, skinData,
                Collections.unmodifiableList(new ObjectArrayList<>(animations)), capeData, geometryData, geometryDataEngineVersion, animationData,
                premium, persona, capeOnClassic, primaryUser, capeId, fullSkinId, armSize, null, color, personaPieces, tintColors, overridingPlayerAppearance, trusted, profileHash);
    }

    public boolean isValid() {
        return isValidSkin() && isValidResourcePatch();
    }

    private boolean isValidSkin() {
        return skinId != null && !skinId.trim().isEmpty() &&
                skinData != null && skinData.getWidth() >= 64 && skinData.getHeight() >= 32 &&
                skinData.getImage().length >= SINGLE_SKIN_SIZE;
    }

    public String getSkinResourcePatch() {
        if (skinResourcePatch == null && geometryName != null) {
            return convertLegacyGeometryName(geometryName);
        }
        return skinResourcePatch;
    }

    public String getGeometryName() {
        if (geometryName == null && skinResourcePatch != null) {
            return convertSkinPatchToLegacy(skinResourcePatch);
        }
        return geometryName;
    }

    private static String convertLegacyGeometryName(String geometryName) {
        return "{\"geometry\" : {\"default\" : \"" + JSONValue.escape(geometryName) + "\"}}";
    }

    private static String convertSkinPatchToLegacy(String skinResourcePatch) {
        checkArgument(validateSkinResourcePatch(skinResourcePatch), "Invalid skin resource patch");
        JSONObject object = (JSONObject) JSONValue.parse(skinResourcePatch);
        JSONObject geometry = (JSONObject) object.get("geometry");
        return (String) geometry.get("default");
    }

    private boolean isValidResourcePatch() {
        return skinResourcePatch != null && validateSkinResourcePatch(skinResourcePatch);
    }

    private static boolean validateSkinResourcePatch(String skinResourcePatch) {
        try {
            JSONObject object = (JSONObject) JSONValue.parse(skinResourcePatch);
            JSONObject geometry = (JSONObject) object.get("geometry");
            return geometry.containsKey("default") && geometry.get("default") instanceof String;
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }
    }

    /**
     * @deprecated since v2168, use color
     */
    public String getSkinColor() {
        if ((skinColor == null || skinColor.isEmpty()) && color != null) {
            if (color.getAlpha() == 0) {
                skinColor = "#0";
            } else {
                skinColor = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
            }
        }
        return skinColor;
    }

    /**
     * @since v2168
     */
    public Color getColor() {
        if ((color == null || color == DEFAULT_COLOR) && skinColor != null && !skinColor.isEmpty()) {
            if (skinColor.equals("#0")) {
                color = new Color(0, true);
            } else {
                color = new Color((int) Long.parseLong(skinColor.startsWith("#") ? skinColor.substring(1) : skinColor, 16), true);
            }
        }
        return color;
    }

    public String getFullSkinId() {
        if (fullSkinId == null) {
            fullSkinId = skinId + capeId;
        }
        return fullSkinId;
    }
}
