package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.InteractPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InteractSerializer_v388 implements BedrockPacketSerializer<InteractPacket> {
    public static final InteractSerializer_v388 INSTANCE = new InteractSerializer_v388();

    private static final InteractPacket.Action[] ACTIONS = InteractPacket.Action.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InteractPacket packet) {
        buffer.writeByte(packet.getAction().ordinal());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());

        if (packet.getAction() == InteractPacket.Action.MOUSEOVER || packet.getAction() == InteractPacket.Action.LEAVE_VEHICLE) {
            helper.writeVector3f(buffer, packet.getMousePosition());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InteractPacket packet) {
        packet.setAction(ACTIONS[buffer.readUnsignedByte()]);
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));

        if (packet.getAction() == InteractPacket.Action.MOUSEOVER || packet.getAction() == InteractPacket.Action.LEAVE_VEHICLE) {
            packet.setMousePosition(helper.readVector3f(buffer));
        }
    }
}
