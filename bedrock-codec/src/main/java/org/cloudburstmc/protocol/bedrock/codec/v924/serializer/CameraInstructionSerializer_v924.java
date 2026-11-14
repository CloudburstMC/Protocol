package org.cloudburstmc.protocol.bedrock.codec.v924.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v859.serializer.CameraInstructionSerializer_v859;
import org.cloudburstmc.protocol.bedrock.data.camera.*;

import java.util.ArrayList;
import java.util.List;

public class CameraInstructionSerializer_v924 extends CameraInstructionSerializer_v859 {

    public static final CameraInstructionSerializer_v924 INSTANCE = new CameraInstructionSerializer_v924();

    @Override
    protected void writeSplineInstruction(ByteBuf buffer, BedrockCodecHelper helper, CameraSplineInstruction splineInstruction) {
        buffer.writeFloatLE(splineInstruction.getTotalTime());
        buffer.writeByte(splineInstruction.getType().ordinal());
        helper.writeArray(buffer, splineInstruction.getCurve(), helper::writeVector3f);
        helper.writeArray(buffer, splineInstruction.getProgressKeyFrames(), (buf2, frame) -> {
            buf2.writeFloatLE(frame.getValue());
            buf2.writeFloatLE(frame.getTime());
            buf2.writeByte(frame.getEase().getId());
        });
        helper.writeArray(buffer, splineInstruction.getRotationOption(), (buf2, rotationOption) -> {
            helper.writeVector3f(buf2, rotationOption.getKeyFrameValues());
            buf2.writeFloatLE(rotationOption.getKeyFrameTimes());
            buf2.writeByte(rotationOption.getEase().getId());
        });
        helper.writeString(buffer, splineInstruction.getSplineIdentifier());
        buffer.writeBoolean(splineInstruction.isLoadFromJson());
    }

    @Override
    protected CameraSplineInstruction readSplineInstruction(ByteBuf buffer, BedrockCodecHelper helper) {
        float totalTime = buffer.readFloatLE();
        CameraSplineType type = CameraSplineType.values()[buffer.readUnsignedByte()];
        List<Vector3f> curve = new ArrayList<>();
        helper.readArray(buffer, curve, helper::readVector3f);
        List<CameraSplineInstruction.SplineProgressOption> progressKeyFrames = new ArrayList<>();
        helper.readArray(buffer, progressKeyFrames, buf2 -> {
            float value = buf2.readFloatLE();
            float time = buf2.readFloatLE();
            CameraEase ease = CameraEase.byId(buf2.readUnsignedByte());
            return new CameraSplineInstruction.SplineProgressOption(value, time, ease);
        });
        List<CameraSplineInstruction.SplineRotationOption> rotationOption = new ArrayList<>();
        helper.readArray(buffer, rotationOption, buf2 -> {
            Vector3f keyFrameValues = helper.readVector3f(buf2);
            float keyFrameTimes = buf2.readFloatLE();
            CameraEase ease = CameraEase.byId(buf2.readUnsignedByte());
            return new CameraSplineInstruction.SplineRotationOption(keyFrameValues, keyFrameTimes, ease);
        });
        String splineIdentifier = helper.readString(buffer);
        boolean loadFromJson = buffer.readBoolean();
        return new CameraSplineInstruction(totalTime, type, curve, progressKeyFrames, rotationOption, splineIdentifier, loadFromJson);
    }
}
