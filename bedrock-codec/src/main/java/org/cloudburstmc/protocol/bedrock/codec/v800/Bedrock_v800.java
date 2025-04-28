package org.cloudburstmc.protocol.bedrock.codec.v800;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v786.Bedrock_v786;
import org.cloudburstmc.protocol.bedrock.codec.v800.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataFormat;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.FlagTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v800 extends Bedrock_v786 {

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v786.ENTITY_FLAGS
            .toBuilder()
            .insert(123, EntityFlag.DOES_SERVER_AUTH_ONLY_DISMOUNT)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v786.ENTITY_DATA
            .toBuilder()
            .update(EntityDataTypes.FLAGS, new FlagTransformer(ENTITY_FLAGS, 0))
            .update(EntityDataTypes.FLAGS_2, new FlagTransformer(ENTITY_FLAGS, 1))
            .insert(EntityDataTypes.SEAT_THIRD_PERSON_CAMERA_RADIUS, 134, EntityDataFormat.FLOAT)
            .insert(EntityDataTypes.SEAT_CAMERA_RELAX_DISTANCE_SMOOTHING, 135, EntityDataFormat.FLOAT)
            .build();

    @SuppressWarnings("deprecation")
    public static final BedrockCodec CODEC = Bedrock_v786.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(800)
            .minecraftVersion("1.21.80")
            .updateSerializer(BiomeDefinitionListPacket.class, BiomeDefinitionListSerializer_v800.INSTANCE)
            .updateSerializer(CameraPresetsPacket.class, CameraPresetsSerializer_v800.INSTANCE)
            .updateSerializer(PlayerListPacket.class, PlayerListSerializer_v800.INSTANCE)
            .registerPacket(PlayerLocationPacket::new, PlayerLocationSerializer_v800.INSTANCE, 326, PacketRecipient.BOTH)
            .registerPacket(ClientboundControlSchemeSetPacket::new, ClientboundControlSchemeSetSerializer_v800.INSTANCE, 327, PacketRecipient.CLIENT)
            .deregisterPacket(CompressedBiomeDefinitionListPacket.class)
            .deregisterPacket(PlayerInputPacket.class)
            .deregisterPacket(RiderJumpPacket.class)
            .build();
}
