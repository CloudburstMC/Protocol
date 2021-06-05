package org.cloudburstmc.protocol.bedrock.codec.v419;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v408.Bedrock_v408;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.*;
import org.cloudburstmc.protocol.bedrock.packet.*;

public class Bedrock_v419 extends Bedrock_v408 {
    public static BedrockCodec CODEC = Bedrock_v408.CODEC.toBuilder()
            .protocolVersion(419)
            .minecraftVersion("1.16.100")
            .helper(BedrockCodecHelper_v419.INSTANCE)
            .updateSerializer(ResourcePackStackPacket.class, ResourcePackStackSerializer_v419.INSTANCE)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v419.INSTANCE)
            .updateSerializer(MovePlayerPacket.class, MovePlayerSerializer_v419.INSTANCE)
            .updateSerializer(UpdateAttributesPacket.class, UpdateAttributesSerializer_v419.INSTANCE)
            .updateSerializer(SetEntityDataPacket.class, SetEntityDataSerializer_v419.INSTANCE)
            .updateSerializer(ContainerClosePacket.class, ContainerCloseSerializer_v419.INSTANCE)
            .updateSerializer(MoveEntityDeltaPacket.class, MoveEntityDeltaSerializer_v419.INSTANCE)
            .updateSerializer(PlayerAuthInputPacket.class, PlayerAuthInputSerializer_v419.INSTANCE)
            .updateSerializer(ItemStackResponsePacket.class, ItemStackResponseSerializer_v419.INSTANCE)
            .registerPacket(MotionPredictionHintsPacket.class, MotionPredictionHintsSerializer_v419.INSTANCE, 157)
            .registerPacket(AnimateEntityPacket.class, AnimateEntitySerializer_v419.INSTANCE, 158)
            .registerPacket(CameraShakePacket.class, CameraShakeSerializer_v419.INSTANCE, 159)
            .registerPacket(PlayerFogPacket.class, PlayerFogSerializer_v419.INSTANCE, 160)
            .registerPacket(CorrectPlayerMovePredictionPacket.class, CorrectPlayerMovePredictionSerializer_v419.INSTANCE, 161)
            .registerPacket(ItemComponentPacket.class, ItemComponentSerializer_v419.INSTANCE, 162)
            .build();
}
