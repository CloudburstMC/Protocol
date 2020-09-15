package com.nukkitx.protocol.bedrock.data;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MoveEntityDeltaFlags {
    public static final int HAS_X = 0x01;
    public static final int HAS_Y = 0x02;
    public static final int HAS_Z = 0x4;
    public static final int HAS_PITCH = 0x8;
    public static final int HAS_YAW = 0x10;
    public static final int HAS_ROLL = 0x20;
    public static final int ON_GROUND = 0x40;
    public static final int TELEPORT = 0x80;
    public static final int FORCE_MOVE = 0x100;

    public static final int HAS_FLOAT_POSITION = 0xFE00;
}
