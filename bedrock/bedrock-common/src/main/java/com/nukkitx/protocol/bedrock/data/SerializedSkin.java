package com.nukkitx.protocol.bedrock.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@ToString(exclude = {"geometryData"})
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SerializedSkin {
    private final String skinId;
    private final String skinResourcePatch;
    private final ImageData skinData;
    private final List<AnimationData> animations;
    private final ImageData capeData;
    private final String geometryData;
    private final String animationData;
    private final boolean premium;
    private final boolean persona;
    private final boolean capeOnClassic;
    private final String capeId;
    private final String fullSkinId;

    public static SerializedSkin of(String skinId, ImageData skinData, ImageData capeData, String geometryName,
                                    String geometryData, boolean premiumSkin) {
        skinData.checkLegacySkinSize();
        capeData.checkLegacyCapeSize();

        return new SerializedSkin(skinId, geometryName, skinData, Collections.emptyList(), capeData, geometryData,
                "", premiumSkin, false, false, "", "");
    }

    public static SerializedSkin of(String skinId, String skinResourcePatch, ImageData skinData,
                                    List<AnimationData> animations, ImageData capeData, String geometryData,
                                    String animationData, boolean premium, boolean persona, boolean capeOnClassic,
                                    String capeId, String fullSkinId) {
        return new SerializedSkin(skinId, skinResourcePatch, skinData,
                Collections.unmodifiableList(new ArrayList<>(animations)), capeData, geometryData, animationData,
                premium, persona, capeOnClassic, capeId, fullSkinId);
    }
}
