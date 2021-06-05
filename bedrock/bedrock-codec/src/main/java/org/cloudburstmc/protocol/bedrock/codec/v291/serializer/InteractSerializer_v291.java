package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.InteractPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InteractSerializer_v291 implements BedrockPacketSerializer<InteractPacket> {
    public static final InteractSerializer_v291 INSTANCE = new InteractSerializer_v291();

    private static final InteractPacket.Action[] ACTIONS = InteractPacket.Action.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, InteractPacket packet) {
        buffer.writeByte(packet.getAction().ordinal());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());

        if (packet.getAction() == InteractPacket.Action.MOUSEOVER) {
            helper.writeVector3f(buffer, packet.getMousePosition());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, InteractPacket packet) {
        packet.setAction(ACTIONS[buffer.readUnsignedByte()]);
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));

        if (packet.getAction() == InteractPacket.Action.MOUSEOVER) {
            packet.setMousePosition(helper.readVector3f(buffer));
        }
    }
}
