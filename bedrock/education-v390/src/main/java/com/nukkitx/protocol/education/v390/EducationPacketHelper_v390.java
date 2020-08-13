package com.nukkitx.protocol.education.v390;

import com.nukkitx.protocol.bedrock.v388.BedrockPacketHelper_v388;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.data.command.CommandParamType.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EducationPacketHelper_v390 extends BedrockPacketHelper_v388 {
    public static final EducationPacketHelper_v390 INSTANCE = new EducationPacketHelper_v390();


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
        this.addCommandParam(29, STRING);
        this.addCommandParam(37, BLOCK_POSITION);
        this.addCommandParam(38, POSITION);
        this.addCommandParam(41, MESSAGE);
        this.addCommandParam(43, TEXT);
        this.addCommandParam(47, JSON);
        this.addCommandParam(54, COMMAND);
    }


}
