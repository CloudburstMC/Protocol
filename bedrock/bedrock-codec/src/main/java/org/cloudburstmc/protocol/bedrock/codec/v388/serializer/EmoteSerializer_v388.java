package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.EmoteFlag;
import org.cloudburstmc.protocol.bedrock.packet.EmotePacket;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EmoteSerializer_v388 implements BedrockPacketSerializer<EmotePacket> {

    public static final EmoteSerializer_v388 INSTANCE = new EmoteSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EmotePacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeString(buffer, packet.getEmoteId());
        int flags = 0;
        for (EmoteFlag flag : packet.getFlags()) {
            flags |= 1 << flag.ordinal();
        }
        buffer.writeByte(flags);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EmotePacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setEmoteId(helper.readString(buffer));
        int flags = buffer.readUnsignedByte();
        if ((flags & 0b1) != 0) {
            packet.getFlags().add(EmoteFlag.SERVER_SIDE);
        }
    }
}
