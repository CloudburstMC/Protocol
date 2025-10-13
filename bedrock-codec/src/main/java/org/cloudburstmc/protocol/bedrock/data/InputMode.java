package org.cloudburstmc.protocol.bedrock.data;

public enum InputMode {
    UNDEFINED,
    MOUSE,
    TOUCH,
    GAMEPAD,
    /**
     * @deprecated since v859
     */
    MOTION_CONTROLLER;

    private static final InputMode[] VALUES = values();

    public static InputMode from(int id) {
        return VALUES[id];
    }
}
