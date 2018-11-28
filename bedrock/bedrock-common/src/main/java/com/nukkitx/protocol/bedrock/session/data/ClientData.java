package com.nukkitx.protocol.bedrock.session.data;

import java.util.UUID;

public interface ClientData {

    String getTenantId();

    String getActiveDirectoryRole();

    byte[] getCapeData();

    long getClientRandomId();

    int getCurrentInputMode();

    int getDefaultInputMode();

    String getDeviceId();

    String getDeviceModel();

    int getDeviceOs();

    String getGameVersion();

    int getGuiScale();

    boolean isEduMode();

    String getLanguageCode();

    String getPlatformOfflineId();

    String getPlatformOnlineId();

    boolean isPremiumSkin();

    UUID getSelfSignedId();

    String getServerAddress();

    byte[] getSkinData();

    byte[] getSkinGeometry();

    String getSkinGeometryName();

    String getSkinId();

    String getThirdPartyName();

    int getUiProfile();
}
