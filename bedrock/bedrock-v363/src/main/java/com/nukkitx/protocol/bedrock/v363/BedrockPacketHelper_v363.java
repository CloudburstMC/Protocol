package com.nukkitx.protocol.bedrock.v363;

import com.nukkitx.protocol.bedrock.v354.BedrockPacketHelper_v354;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.data.command.CommandParamType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v363 extends BedrockPacketHelper_v354 {

    @Override
    protected void registerCommandParams() {
        this.addCommandParam(1, INT);
        this.addCommandParam(2, FLOAT);
        this.addCommandParam(3, VALUE);
        this.addCommandParam(4, WILDCARD_INT);
        this.addCommandParam(5, OPERATOR);
        this.addCommandParam(6, TARGET);
        this.addCommandParam(7, WILDCARD_TARGET);
        this.addCommandParam(14, FILE_PATH);
        this.addCommandParam(27, STRING);
        this.addCommandParam(29, POSITION);
        this.addCommandParam(32, MESSAGE);
        this.addCommandParam(34, TEXT);
        this.addCommandParam(37, JSON);
        this.addCommandParam(44, COMMAND);
    }
}
