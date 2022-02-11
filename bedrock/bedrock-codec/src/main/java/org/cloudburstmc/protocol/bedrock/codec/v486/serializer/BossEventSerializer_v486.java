package org.cloudburstmc.protocol.bedrock.codec.v486.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.BossEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.BossEventPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BossEventSerializer_v486 extends BossEventSerializer_v291 {
    public static final BossEventSerializer_v486 INSTANCE = new BossEventSerializer_v486();

    @Override
    protected void serializeAction(ByteBuf buffer, BedrockCodecHelper helper, BossEventPacket packet) {
        if (packet.getAction() == BossEventPacket.Action.QUERY) {
            VarInts.writeLong(buffer, packet.getPlayerUniqueEntityId());
        } else {
            super.serializeAction(buffer, helper, packet);
        }
    }

    @Override
    protected void deserializeAction(ByteBuf buffer, BedrockCodecHelper helper, BossEventPacket packet) {
        if (packet.getAction() == BossEventPacket.Action.QUERY) {
            packet.setPlayerUniqueEntityId(VarInts.readLong(buffer));
        } else {
            super.deserializeAction(buffer, helper, packet);
        }
    }
}
