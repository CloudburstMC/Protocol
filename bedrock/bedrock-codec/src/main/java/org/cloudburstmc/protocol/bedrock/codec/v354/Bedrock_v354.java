package org.cloudburstmc.protocol.bedrock.codec.v354;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v340.Bedrock_v340;
import org.cloudburstmc.protocol.bedrock.codec.v354.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityData;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v354 extends Bedrock_v340 {

    protected static final TypeMap<EntityData> ENTITY_DATA = Bedrock_v340.ENTITY_DATA.toBuilder()
            .insert(102, EntityData.TRADE_XP)
            .build();


    public static final BedrockCodec CODEC = Bedrock_v340.CODEC.toBuilder()
            .protocolVersion(354)
            .minecraftVersion("1.11.0")
            .helper(BedrockCodecHelper_v354.INSTANCE)
            .updateSerializer(CraftingDataPacket.class, CraftingDataSerializer_v354.INSTANCE)
            .updateSerializer(EventPacket.class, EventSerializer_v354.INSTANCE)
            .updateSerializer(ClientboundMapItemDataPacket.class, ClientboundMapItemDataSerializer_v354.INSTANCE)
            .updateSerializer(UpdateTradePacket.class, UpdateTradeSerializer_v354.INSTANCE)
            .updateSerializer(LecternUpdatePacket.class, LecternUpdateSerializer_v354.INSTANCE)
            .registerPacket(MapCreateLockedCopyPacket.class, MapCreateLockedCopySerializer_v354.INSTANCE, 126)
            .registerPacket(OnScreenTextureAnimationPacket.class, OnScreenTextureAnimationSerializer_v354.INSTANCE, 127)
            .build();
}
