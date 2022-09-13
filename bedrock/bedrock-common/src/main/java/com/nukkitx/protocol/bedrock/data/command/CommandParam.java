package com.nukkitx.protocol.bedrock.data.command;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CommandParam {

    public static final CommandParam INT = new CommandParam(CommandParamType.INT);
    public static final CommandParam FLOAT = new CommandParam(CommandParamType.FLOAT);
    public static final CommandParam VALUE = new CommandParam(CommandParamType.VALUE);
    public static final CommandParam WILDCARD_INT = new CommandParam(CommandParamType.WILDCARD_INT);
    public static final CommandParam OPERATOR = new CommandParam(CommandParamType.OPERATOR);
    public static final CommandParam COMPARE_OPERATOR = new CommandParam(CommandParamType.COMPARE_OPERATOR);
    public static final CommandParam TARGET = new CommandParam(CommandParamType.TARGET);
    public static final CommandParam WILDCARD_TARGET = new CommandParam(CommandParamType.WILDCARD_TARGET);
    public static final CommandParam FILE_PATH = new CommandParam(CommandParamType.FILE_PATH);
    public static final CommandParam INT_RANGE = new CommandParam(CommandParamType.INT_RANGE);
    public static final CommandParam STRING = new CommandParam(CommandParamType.STRING);
    public static final CommandParam POSITION = new CommandParam(CommandParamType.POSITION);
    public static final CommandParam BLOCK_POSITION = new CommandParam(CommandParamType.BLOCK_POSITION);
    public static final CommandParam MESSAGE = new CommandParam(CommandParamType.MESSAGE);
    public static final CommandParam TEXT = new CommandParam(CommandParamType.TEXT);
    public static final CommandParam JSON = new CommandParam(CommandParamType.JSON);
    public static final CommandParam BLOCK_STATES = new CommandParam(CommandParamType.BLOCK_STATES);
    public static final CommandParam COMMAND = new CommandParam(CommandParamType.COMMAND);

    private final CommandParamType paramType;
    private final int defaultValue;

    public CommandParam(CommandParamType paramType) {
        this.paramType = paramType;
        this.defaultValue = -1;
    }

    public CommandParam(int defaultValue) {
        this.defaultValue = defaultValue;
        this.paramType = null;
    }

    public int getValue(BedrockPacketHelper helper) {
        if (this.defaultValue > 0 || this.paramType == null) {
            return this.defaultValue;
        } else {
            return helper.getCommandParamId(this);
        }
    }

    @Override
    public String toString() {
        return "CommandParam(type=" + (this.paramType == null ? "UNKNOWN" : this.paramType.name()) + ", defaultValue=" + this.defaultValue + ")";
    }
}
