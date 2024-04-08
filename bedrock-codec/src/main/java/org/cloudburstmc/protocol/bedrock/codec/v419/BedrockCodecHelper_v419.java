package org.cloudburstmc.protocol.bedrock.codec.v419;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v407.BedrockCodecHelper_v407;
import org.cloudburstmc.protocol.bedrock.data.ExperimentData;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.data.skin.AnimatedTextureType;
import org.cloudburstmc.protocol.bedrock.data.skin.AnimationData;
import org.cloudburstmc.protocol.bedrock.data.skin.AnimationExpressionType;
import org.cloudburstmc.protocol.bedrock.data.skin.ImageData;
import org.cloudburstmc.protocol.common.util.TypeMap;

import java.util.List;

public class BedrockCodecHelper_v419 extends BedrockCodecHelper_v407 {

    protected static final AnimationExpressionType[] EXPRESSION_TYPES = AnimationExpressionType.values();

    public BedrockCodecHelper_v419(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes,
                                   TypeMap<ItemStackRequestActionType> stackRequestActionTypes, TypeMap<ContainerSlotType> containerSlotTypes) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes);
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
        ImageData image = this.readImage(buffer, ImageData.SKIN_128_128_SIZE);
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
