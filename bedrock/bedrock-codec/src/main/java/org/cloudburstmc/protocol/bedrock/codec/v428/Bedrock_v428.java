package org.cloudburstmc.protocol.bedrock.codec.v428;

import lombok.experimental.UtilityClass;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v422.Bedrock_v422;
import org.cloudburstmc.protocol.bedrock.codec.v428.serializer.*;
import org.cloudburstmc.protocol.bedrock.packet.*;

@UtilityClass
public class Bedrock_v428 {
    public static BedrockCodec CODEC = Bedrock_v422.CODEC.toBuilder()
            .protocolVersion(428)
            .minecraftVersion("1.16.210")
            .helper(BedrockCodecHelper_v428.INSTANCE)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v428.INSTANCE)
            .updateSerializer(PlayerAuthInputPacket.class, PlayerAuthInputSerializer_v428.INSTANCE)
            .updateSerializer(ItemStackResponsePacket.class, ItemStackResponseSerializer_v428.INSTANCE)
            .updateSerializer(CameraShakePacket.class, CameraShakeSerializer_v428.INSTANCE)
            .registerPacket(ClientboundDebugRendererPacket.class, ClientboundDebugRendererSerializer_v428.INSTANCE, 164)
            .build();
}
