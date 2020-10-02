package com.nukkitx.protocol.bedrock.v417;

import com.nukkitx.protocol.bedrock.data.ExperimentData;
import com.nukkitx.protocol.bedrock.v407.BedrockPacketHelper_v407;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v417 extends BedrockPacketHelper_v407 {

    public static final BedrockPacketHelper_v417 INSTANCE = new BedrockPacketHelper_v417();

    @Override
    public boolean isBlockingItem(int id) {
        return id == 401; // FIXME: Come up with a solution for this hack
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
}
