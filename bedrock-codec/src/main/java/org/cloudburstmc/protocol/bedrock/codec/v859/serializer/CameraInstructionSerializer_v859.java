package org.cloudburstmc.protocol.bedrock.codec.v859.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v827.serializer.CameraInstructionSerializer_v827;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAttachToEntityInstruction;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSplineInstruction;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSplineType;
import org.cloudburstmc.protocol.bedrock.packet.CameraInstructionPacket;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

import java.util.ArrayList;
import java.util.List;

public class CameraInstructionSerializer_v859 extends CameraInstructionSerializer_v827 {

    public static final CameraInstructionSerializer_v859 INSTANCE = new CameraInstructionSerializer_v859();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraInstructionPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeOptionalNull(buffer, packet.getSplineInstruction(), (buf, splineInstruction) -> {
            buf.writeFloatLE(splineInstruction.getTotalTime());
            buf.writeByte(splineInstruction.getType().ordinal());
            helper.writeArray(buf, splineInstruction.getCurve(), helper::writeVector3f);
            helper.writeArray(buf, splineInstruction.getProgressKeyFrames(), helper::writeVector2f);
            helper.writeArray(buf, splineInstruction.getRotationOption(), (buf2, rotationOption) -> {
                helper.writeVector3f(buf2, rotationOption.getKeyFrameValues());
                buf2.writeFloatLE(rotationOption.getKeyFrameTimes());
            });
        });
        helper.writeOptionalNull(buffer, packet.getAttachInstruction(), (buf, attachInstruction) -> buf.writeLongLE(attachInstruction.getUniqueEntityId()));
        helper.writeOptional(buffer, OptionalBoolean::isPresent, packet.getDetachFromEntity(), (buf, optional) -> buf.writeBoolean(optional.getAsBoolean()));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraInstructionPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setSplineInstruction(helper.readOptional(buffer, null, buf -> {
            float totalTime = buf.readFloatLE();
            CameraSplineType type = CameraSplineType.values()[buf.readUnsignedByte()];
            List<Vector3f> curve = new ArrayList<>();
            helper.readArray(buf, curve, helper::readVector3f);
            List<Vector2f> progressKeyFrames = new ArrayList<>();
            helper.readArray(buf, progressKeyFrames, helper::readVector2f);
            List<CameraSplineInstruction.SplineRotationOption> rotationOption = new ArrayList<>();
            helper.readArray(buf, rotationOption, buf2 -> {
                Vector3f keyFrameValues = helper.readVector3f(buf2);
                float keyFrameTimes = buf2.readFloatLE();
                return new CameraSplineInstruction.SplineRotationOption(keyFrameValues, keyFrameTimes);
            });
            return new CameraSplineInstruction(totalTime, type, curve, progressKeyFrames, rotationOption);
        }));
        packet.setAttachInstruction(helper.readOptional(buffer, null, buf -> new CameraAttachToEntityInstruction(buf.readLongLE())));
        packet.setDetachFromEntity(helper.readOptional(buffer, OptionalBoolean.empty(), buf -> OptionalBoolean.of(buf.readBoolean())));
    }
}
