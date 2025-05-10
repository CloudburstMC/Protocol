package org.cloudburstmc.protocol.bedrock.codec.v800.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v776.serializer.CameraAimAssistPresetsSerializer_v776;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAimAssistOperation;
import org.cloudburstmc.protocol.bedrock.packet.CameraAimAssistPresetsPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CameraAimAssistPresetsSerializer_v800 extends CameraAimAssistPresetsSerializer_v776 {

    public static final CameraAimAssistPresetsSerializer_v800 INSTANCE = new CameraAimAssistPresetsSerializer_v800();

    private static final CameraAimAssistOperation[] OPERATIONS = CameraAimAssistOperation.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistPresetsPacket packet) {
        helper.writeArray(buffer, packet.getCategoryDefinitions(), this::writeCategory);
        helper.writeArray(buffer, packet.getPresets(), this::writePreset);
        buffer.writeByte(packet.getOperation().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistPresetsPacket packet) {
        helper.readArray(buffer, packet.getCategoryDefinitions(), this::readCategory);
        helper.readArray(buffer, packet.getPresets(), this::readPreset);
        packet.setOperation(OPERATIONS[buffer.readUnsignedByte()]);
    }
}
