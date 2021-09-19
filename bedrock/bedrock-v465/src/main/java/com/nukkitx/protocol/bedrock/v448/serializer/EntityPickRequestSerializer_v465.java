package com.nukkitx.protocol.bedrock.v448.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.EntityPickRequestPacket;
import com.nukkitx.protocol.bedrock.v291.serializer.EntityPickRequestSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntityPickRequestSerializer_v465 extends EntityPickRequestSerializer_v291 {

    public static final EntityPickRequestSerializer_v465 INSTANCE = new EntityPickRequestSerializer_v465();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EntityPickRequestPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeBoolean(packet.isWithData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EntityPickRequestPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setWithData(buffer.readBoolean());
    }
}
