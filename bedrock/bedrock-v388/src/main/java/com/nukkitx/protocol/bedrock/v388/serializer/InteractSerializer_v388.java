package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.InteractPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InteractSerializer_v388 implements PacketSerializer<InteractPacket> {
    public static final InteractSerializer_v388 INSTANCE = new InteractSerializer_v388();


    @Override
    public void serialize(ByteBuf buffer, InteractPacket packet) {
        buffer.writeByte(packet.getAction());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());

        if (packet.getAction() == 4/*Action.MOUSEOVER*/) {
            BedrockUtils.writeVector3f(buffer, packet.getMousePosition());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, InteractPacket packet) {
        packet.setAction(buffer.readByte());
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));

        if (packet.getAction() == 4/*Action.MOUSEOVER*/) {
            packet.setMousePosition(BedrockUtils.readVector3f(buffer));
        }
    }
}
