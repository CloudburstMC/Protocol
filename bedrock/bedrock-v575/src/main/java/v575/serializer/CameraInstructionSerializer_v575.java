package v575.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.CameraInstructionPacket;
import io.netty.buffer.ByteBuf;

public class CameraInstructionSerializer_v575 implements BedrockPacketSerializer<CameraInstructionPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CameraInstructionPacket packet) {
        helper.writeTag(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CameraInstructionPacket packet) {
        packet.setData(helper.readTag(buffer));
    }
}

