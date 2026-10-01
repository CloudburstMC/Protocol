package org.cloudburstmc.protocol.bedrock.codec.v827.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v818.serializer.CameraInstructionSerializer_v818;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraFovInstruction;
import org.cloudburstmc.protocol.bedrock.packet.CameraInstructionPacket;

public class CameraInstructionSerializer_v827 extends CameraInstructionSerializer_v818 {

    public static final CameraInstructionSerializer_v827 INSTANCE = new CameraInstructionSerializer_v827();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraInstructionPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeOptionalNull(buffer, packet.getFovInstruction(), this::writeFovInstruction);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraInstructionPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setFovInstruction(helper.readOptional(buffer, null, this::readFovInstruction));
    }

    protected void writeFovInstruction(ByteBuf buffer, BedrockCodecHelper helper, CameraFovInstruction fovInstruction) {
        buffer.writeFloatLE(fovInstruction.getFov());
        buffer.writeFloatLE(fovInstruction.getEaseTime());
        buffer.writeByte(fovInstruction.getEaseType().getId());
        buffer.writeBoolean(fovInstruction.isClear());
    }

    protected CameraFovInstruction readFovInstruction(ByteBuf buffer, BedrockCodecHelper helper) {
        float fow = buffer.readFloatLE();
        float easeTime = buffer.readFloatLE();
        CameraEase easeType = CameraEase.byId(buffer.readUnsignedByte());
        boolean fovClear = buffer.readBoolean();
        return new CameraFovInstruction(fow, easeTime, easeType, fovClear);
    }
}
