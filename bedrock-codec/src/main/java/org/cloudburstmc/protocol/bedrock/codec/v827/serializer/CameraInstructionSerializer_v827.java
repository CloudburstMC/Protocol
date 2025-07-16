package org.cloudburstmc.protocol.bedrock.codec.v827.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v748.serializer.CameraInstructionSerializer_v748;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraFovInstruction;
import org.cloudburstmc.protocol.bedrock.packet.CameraInstructionPacket;

public class CameraInstructionSerializer_v827 extends CameraInstructionSerializer_v748 {

    public static final CameraInstructionSerializer_v827 INSTANCE = new CameraInstructionSerializer_v827();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraInstructionPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeOptionalNull(buffer, packet.getFovInstruction(), (buf, fovInstruction) -> {
            buf.writeFloatLE(fovInstruction.getFov());
            buf.writeFloatLE(fovInstruction.getEaseTime());
            buf.writeByte(fovInstruction.getEaseType().ordinal());
            buf.writeBoolean(fovInstruction.isClear());
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraInstructionPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setFovInstruction(helper.readOptional(buffer, null, buf -> {
            float fow = buf.readFloatLE();
            float easeTime = buf.readFloatLE();
            CameraEase easeType = CameraEase.values()[buf.readUnsignedByte()];
            boolean fovClear = buf.readBoolean();
            return new CameraFovInstruction(fow, easeTime, easeType, fovClear);
        }));
    }
}
