package com.nukkitx.protocol.bedrock.v486.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.BossEventPacket;
import com.nukkitx.protocol.bedrock.v291.serializer.BossEventSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BossEventSerializer_v486 extends BossEventSerializer_v291 {
    public static final BossEventSerializer_v486 INSTANCE = new BossEventSerializer_v486();

    @Override
    protected void serializeAction(ByteBuf buffer, BedrockPacketHelper helper, BossEventPacket packet) {
        if (packet.getAction() == BossEventPacket.Action.QUERY) {
            VarInts.writeLong(buffer, packet.getPlayerUniqueEntityId());
        } else {
            super.serializeAction(buffer, helper, packet);
        }
    }

    @Override
    protected void deserializeAction(ByteBuf buffer, BedrockPacketHelper helper, BossEventPacket packet) {
        if (packet.getAction() == BossEventPacket.Action.QUERY) {
            packet.setPlayerUniqueEntityId(VarInts.readLong(buffer));
        } else {
            super.deserializeAction(buffer, helper, packet);
        }
    }
}
