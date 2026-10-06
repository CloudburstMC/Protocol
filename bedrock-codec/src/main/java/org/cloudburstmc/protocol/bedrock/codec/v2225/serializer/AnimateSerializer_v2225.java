package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v898.serializer.AnimateSerializer_v898;
import org.cloudburstmc.protocol.bedrock.data.inventory.HandSlot;
import org.cloudburstmc.protocol.bedrock.packet.AnimatePacket;

public class AnimateSerializer_v2225 extends AnimateSerializer_v898 {

    public static final AnimateSerializer_v2225 INSTANCE = new AnimateSerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AnimatePacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeByte(packet.getHand().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AnimatePacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setHand(HandSlot.values()[buffer.readUnsignedByte()]);
    }
}
