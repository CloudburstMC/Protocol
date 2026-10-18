package org.cloudburstmc.protocol.bedrock.codec.v924.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSplineDefinition;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSplineInstruction;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSplineType;
import org.cloudburstmc.protocol.bedrock.packet.CameraSplinePacket;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CameraSplineSerializer_v924 implements BedrockPacketSerializer<CameraSplinePacket> {

    public static final CameraSplineSerializer_v924 INSTANCE = new CameraSplineSerializer_v924();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraSplinePacket packet) {
        helper.writeArray(buffer, packet.getSplines(), (buf, spline) -> {
            helper.writeString(buf, spline.getName());
            buf.writeFloatLE(spline.getInstruction().getTotalTime());
            helper.writeString(buf, spline.getInstruction().getType().getSerializeName());
            helper.writeArray(buf, spline.getInstruction().getCurve(), helper::writeVector3f);
            helper.writeArray(buf, spline.getInstruction().getProgressKeyFrames(), (buf2, frame) -> {
                buf2.writeFloatLE(frame.getValue());
                buf2.writeFloatLE(frame.getTime());
                helper.writeOptionalNull(buf2, frame.getEase(), (buf3, ease) -> helper.writeString(buf3, ease.getSerializeName()));
            });
            helper.writeArray(buf, spline.getInstruction().getRotationOption(), (buf2, rotationOption) -> {
                helper.writeVector3f(buf2, rotationOption.getKeyFrameValues());
                buf2.writeFloatLE(rotationOption.getKeyFrameTimes());
                helper.writeOptionalNull(buf2, rotationOption.getEase(), (buf3, ease) -> helper.writeString(buf3, ease.getSerializeName()));
            });
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraSplinePacket packet) {
        List<CameraSplineDefinition> splines = new ArrayList<>();

        helper.readArray(buffer, splines, (buf, h) -> {
            String name = helper.readString(buf);
            float totalTime = buf.readFloatLE();
            CameraSplineType type = CameraSplineType.fromName(helper.readString(buf));
            List<Vector3f> curve = new ArrayList<>();
            helper.readArray(buf, curve, helper::readVector3f);
            List<CameraSplineInstruction.SplineProgressOption> progressKeyFrames = new ArrayList<>();
            helper.readArray(buf, progressKeyFrames, buf2 -> {
                float value = buf2.readFloatLE();
                float time = buf2.readFloatLE();
                CameraEase ease = helper.readOptional(buf2, null, buf3 -> CameraEase.fromName(helper.readString(buf3)));
                return new CameraSplineInstruction.SplineProgressOption(value, time, ease);
            });
            List<CameraSplineInstruction.SplineRotationOption> rotationOption = new ArrayList<>();
            helper.readArray(buf, rotationOption, buf2 -> {
                Vector3f keyFrameValues = helper.readVector3f(buf2);
                float keyFrameTimes = buf2.readFloatLE();
                CameraEase ease = helper.readOptional(buf2, null, buf3 -> CameraEase.fromName(helper.readString(buf3)));
                return new CameraSplineInstruction.SplineRotationOption(keyFrameValues, keyFrameTimes, ease);
            });
            return new CameraSplineDefinition(name, new CameraSplineInstruction(totalTime, type, curve, progressKeyFrames, rotationOption));
        });

        packet.setSplines(splines);
    }
}
