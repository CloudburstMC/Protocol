package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.EmoteFlag;
import org.cloudburstmc.protocol.bedrock.packet.EmotePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EmoteSerializer_v388 implements BedrockPacketSerializer<EmotePacket> {

    public static final EmoteSerializer_v388 INSTANCE = new EmoteSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EmotePacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeString(buffer, packet.getEmoteId());
        this.writeFlags(buffer, helper, packet.getFlags());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EmotePacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setEmoteId(helper.readString(buffer));
        this.readFlags(buffer, helper, packet.getFlags());
    }

    protected void writeFlags(ByteBuf buffer, BedrockCodecHelper helper, Set<EmoteFlag> flags) {
        int flagsData = 0;
        for (EmoteFlag flag : flags) {
            flagsData |= 1 << flag.ordinal();
        }
        buffer.writeByte(flagsData);
    }

    protected void readFlags(ByteBuf buffer, BedrockCodecHelper helper, Set<EmoteFlag> flags) {
        int flagsData = buffer.readUnsignedByte();
        for (EmoteFlag flag : EmoteFlag.values()) {
            if ((flagsData & (1L << flag.ordinal())) != 0) {
                flags.add(flag);
            }
        }
    }
}
