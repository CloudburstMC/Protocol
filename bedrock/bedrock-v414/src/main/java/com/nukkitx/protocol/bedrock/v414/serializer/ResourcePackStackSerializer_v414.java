package com.nukkitx.protocol.bedrock.v414.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.ResourcePackStackPacket;
import com.nukkitx.protocol.bedrock.v291.serializer.ResourcePackStackSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePackStackSerializer_v414 extends ResourcePackStackSerializer_v291 {

    public static final ResourcePackStackSerializer_v414 INSTANCE = new ResourcePackStackSerializer_v414();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
        super.serialize(buffer, helper, packet);

        helper.writeString(buffer, packet.getGameVersion());

        helper.writeExperiments(buffer, packet.getExperiments());
        buffer.writeBoolean(packet.isExperimentsPreviouslyToggled());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setGameVersion(helper.readString(buffer));

        helper.readExperiments(buffer, packet.getExperiments());
        packet.setExperimentsPreviouslyToggled(buffer.readBoolean());
    }
}
