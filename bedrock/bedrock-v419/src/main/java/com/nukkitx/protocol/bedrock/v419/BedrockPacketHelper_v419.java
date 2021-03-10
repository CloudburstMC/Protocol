package com.nukkitx.protocol.bedrock.v419;

import com.nukkitx.protocol.bedrock.data.ExperimentData;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.command.CommandParamType;
import com.nukkitx.protocol.bedrock.data.skin.AnimatedTextureType;
import com.nukkitx.protocol.bedrock.data.skin.AnimationData;
import com.nukkitx.protocol.bedrock.data.skin.AnimationExpressionType;
import com.nukkitx.protocol.bedrock.data.skin.ImageData;
import com.nukkitx.protocol.bedrock.v407.BedrockPacketHelper_v407;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v419 extends BedrockPacketHelper_v407 {

    public static final BedrockPacketHelper_v419 INSTANCE = new BedrockPacketHelper_v419();

    protected static final AnimationExpressionType[] EXPRESSION_TYPES = AnimationExpressionType.values();

    @Override
    protected void registerCommandParams() {
        this.addCommandParam(1, CommandParamType.INT);
        this.addCommandParam(2, CommandParamType.FLOAT);
        this.addCommandParam(3, CommandParamType.VALUE);
        this.addCommandParam(4, CommandParamType.WILDCARD_INT);
        this.addCommandParam(5, CommandParamType.OPERATOR);
        this.addCommandParam(6, CommandParamType.TARGET);
        this.addCommandParam(7, CommandParamType.WILDCARD_TARGET);

        this.addCommandParam(15, CommandParamType.FILE_PATH);

        this.addCommandParam(31, CommandParamType.STRING);
        this.addCommandParam(39, CommandParamType.BLOCK_POSITION);
        this.addCommandParam(40, CommandParamType.POSITION);
        this.addCommandParam(43, CommandParamType.MESSAGE);
        this.addCommandParam(45, CommandParamType.TEXT);
        this.addCommandParam(49, CommandParamType.JSON);
        this.addCommandParam(56, CommandParamType.COMMAND);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(317, SoundEvent.EQUIP_NETHERITE);
        this.addSoundEvent(318, SoundEvent.UNDEFINED);
    }

    @Override
    public void readExperiments(ByteBuf buffer, List<ExperimentData> experiments) {
        int count = buffer.readIntLE(); // Actually unsigned
        for (int i = 0; i < count; i++) {
            experiments.add(new ExperimentData(
                    this.readString(buffer),
                    buffer.readBoolean() // Hardcoded to true in 414
            ));
        }
    }

    @Override
    public void writeExperiments(ByteBuf buffer, List<ExperimentData> experiments) {
        buffer.writeIntLE(experiments.size());

        for (ExperimentData experiment : experiments) {
            this.writeString(buffer, experiment.getName());
            buffer.writeBoolean(experiment.isEnabled());
        }
    }

    @Override
    public AnimationData readAnimationData(ByteBuf buffer) {
        ImageData image = this.readImage(buffer);
        AnimatedTextureType textureType = TEXTURE_TYPES[buffer.readIntLE()];
        float frames = buffer.readFloatLE();
        AnimationExpressionType expressionType = EXPRESSION_TYPES[buffer.readIntLE()];
        return new AnimationData(image, textureType, frames, expressionType);
    }

    @Override
    public void writeAnimationData(ByteBuf buffer, AnimationData animation) {
        super.writeAnimationData(buffer, animation);
        buffer.writeIntLE(animation.getExpressionType().ordinal());
    }
}
