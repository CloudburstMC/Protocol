package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SetLastHurtByPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetLastHurtBySerializer_v291 implements BedrockPacketSerializer<SetLastHurtByPacket> {
    public static final SetLastHurtBySerializer_v291 INSTANCE = new SetLastHurtBySerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetLastHurtByPacket packet) {
        VarInts.writeInt(buffer, packet.getEntityTypeId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetLastHurtByPacket packet) {
        packet.setEntityTypeId(VarInts.readInt(buffer));
    }
}
