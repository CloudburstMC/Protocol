package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SetEntityDataPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetEntityDataSerializer_v291 implements BedrockPacketSerializer<SetEntityDataPacket> {
    public static final SetEntityDataSerializer_v291 INSTANCE = new SetEntityDataSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityDataPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeEntityData(buffer, packet.getMetadata());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityDataPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        helper.readEntityData(buffer, packet.getMetadata());
    }
}
