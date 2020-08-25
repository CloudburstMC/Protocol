package com.nukkitx.protocol.bedrock.v411;

import com.nukkitx.protocol.bedrock.v409.BedrockPacketHelper_v409;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.data.command.CommandParamType.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v411 extends BedrockPacketHelper_v409 {
    public static final BedrockPacketHelper_v411 INSTANCE = new BedrockPacketHelper_v411();

    @Override
    protected void registerCommandParams() {
        this.addCommandParam(1, INT);
        this.addCommandParam(2, FLOAT);
        this.addCommandParam(3, VALUE);
        this.addCommandParam(4, WILDCARD_INT);
        this.addCommandParam(5, OPERATOR);
        this.addCommandParam(6, TARGET);

        this.addCommandParam(8, WILDCARD_TARGET);
        this.addCommandParam(15, FILE_PATH);
        this.addCommandParam(30, STRING);
        this.addCommandParam(38, BLOCK_POSITION);
        this.addCommandParam(39, POSITION);
        this.addCommandParam(42, MESSAGE);
        this.addCommandParam(44, TEXT);
        this.addCommandParam(48, JSON);
        this.addCommandParam(55, COMMAND);
    }

}
