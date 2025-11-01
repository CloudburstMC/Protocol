package org.cloudburstmc.protocol.bedrock.codec.v859;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.EntityEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v844.Bedrock_v844;
import org.cloudburstmc.protocol.bedrock.codec.v859.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v859 extends Bedrock_v844 {

    protected static final TypeMap<EntityEventType> ENTITY_EVENTS = Bedrock_v844.ENTITY_EVENTS.toBuilder()
            .insert(79, EntityEventType.SHAKE_WETNESS_STOP)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v844.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(859)
            .minecraftVersion("1.21.120")
            .updateSerializer(AnimatePacket.class, AnimateSerializer_v859.INSTANCE)
            .updateSerializer(BiomeDefinitionListPacket.class, BiomeDefinitionListSerializer_v859.INSTANCE)
            .updateSerializer(CameraInstructionPacket.class, CameraInstructionSerializer_v859.INSTANCE)
            .updateSerializer(DebugDrawerPacket.class, DebugDrawerSerializer_v859.INSTANCE)
            .updateSerializer(EntityEventPacket.class, new EntityEventSerializer_v291(ENTITY_EVENTS))
            .updateSerializer(ShowStoreOfferPacket.class, ShowStoreOfferSerializer_v859.INSTANCE)
            .registerPacket(GraphicsParameterOverridePacket::new, GraphicsParameterOverrideSerializer_v859.INSTANCE, 331, PacketRecipient.CLIENT)
            .build();
}
