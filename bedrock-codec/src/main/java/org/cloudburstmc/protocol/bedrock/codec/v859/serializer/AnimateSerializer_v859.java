package org.cloudburstmc.protocol.bedrock.codec.v859.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.AnimateSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.AnimatePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import static org.cloudburstmc.protocol.bedrock.packet.AnimatePacket.Action;

public class AnimateSerializer_v859 extends AnimateSerializer_v291 {

    public static final AnimateSerializer_v859 INSTANCE = new AnimateSerializer_v859();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AnimatePacket packet) {
        Action action = packet.getAction();
        VarInts.writeInt(buffer, types.get(action));
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        buffer.writeFloatLE(packet.getData()); // new
        if (action == Action.ROW_LEFT || action == Action.ROW_RIGHT) {
            buffer.writeFloatLE(packet.getRowingTime());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AnimatePacket packet) {
        Action action = types.get(VarInts.readInt(buffer));
        packet.setAction(action);
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setData(buffer.readFloatLE()); // new
        if (action == Action.ROW_LEFT || action == Action.ROW_RIGHT) {
            packet.setRowingTime(buffer.readFloatLE());
        }
    }
}
