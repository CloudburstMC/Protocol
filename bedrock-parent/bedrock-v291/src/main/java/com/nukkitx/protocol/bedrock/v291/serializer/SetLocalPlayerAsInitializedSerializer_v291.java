package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SetLocalPlayerAsInitializedPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetLocalPlayerAsInitializedSerializer_v291 implements BedrockPacketSerializer<SetLocalPlayerAsInitializedPacket> {
    public static final SetLocalPlayerAsInitializedSerializer_v291 INSTANCE = new SetLocalPlayerAsInitializedSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetLocalPlayerAsInitializedPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetLocalPlayerAsInitializedPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
    }
}
