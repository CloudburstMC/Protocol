package v575.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.CameraPresetsPacket;
import io.netty.buffer.ByteBuf;

public class CameraPresetsSerializer_v575 implements BedrockPacketSerializer<CameraPresetsPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CameraPresetsPacket packet) {
        helper.writeTag(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CameraPresetsPacket packet) {
        packet.setData(helper.readTag(buffer));
    }
}