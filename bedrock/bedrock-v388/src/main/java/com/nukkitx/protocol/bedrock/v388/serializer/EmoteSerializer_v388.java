package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.EmoteFlag;
import com.nukkitx.protocol.bedrock.packet.EmotePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EmoteSerializer_v388 implements BedrockPacketSerializer<EmotePacket> {

    public static final EmoteSerializer_v388 INSTANCE = new EmoteSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EmotePacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeString(buffer, packet.getEmoteId());
        int flags = 0;
        for (EmoteFlag flag : packet.getFlags()) {
            flags |= 1 << flag.ordinal();
        }
        buffer.writeByte(flags);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EmotePacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setEmoteId(helper.readString(buffer));
        int flags = buffer.readUnsignedByte();
        if ((flags & 0b1) != 0) {
            packet.getFlags().add(EmoteFlag.SERVER_SIDE);
        }
    }
}
