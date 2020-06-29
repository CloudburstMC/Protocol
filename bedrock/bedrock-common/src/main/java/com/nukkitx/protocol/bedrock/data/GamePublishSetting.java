package com.nukkitx.protocol.bedrock.data;

public enum GamePublishSetting {
    FRIENDS_OF_FRIENDS,
    FRIENDS_ONLY,
    INVITE_ONLY,
    NO_MULTI_PLAY,
    PUBLIC;

    public static GamePublishSetting byId(int id) {
        return values()[id];
    }
}
