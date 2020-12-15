package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SetHealthPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetHealthSerializer_v291 implements BedrockPacketSerializer<SetHealthPacket> {
    public static final SetHealthSerializer_v291 INSTANCE = new SetHealthSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetHealthPacket packet) {
        VarInts.writeInt(buffer, packet.getHealth());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetHealthPacket packet) {
        packet.setHealth(VarInts.readInt(buffer));
    }
}
