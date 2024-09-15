package org.cloudburstmc.protocol.bedrock.codec.v729.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v589.serializer.EmoteSerializer_v589;
import org.cloudburstmc.protocol.bedrock.packet.EmotePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EmoteSerializer_v729 extends EmoteSerializer_v589 {

    public static final EmoteSerializer_v729 INSTANCE = new EmoteSerializer_v729();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EmotePacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeString(buffer, packet.getEmoteId());
        VarInts.writeUnsignedInt(buffer, packet.getEmoteDuration());
        helper.writeString(buffer, packet.getXuid());
        helper.writeString(buffer, packet.getPlatformId());
        this.writeFlags(buffer, helper, packet.getFlags());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EmotePacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setEmoteId(helper.readString(buffer));
        packet.setEmoteDuration(VarInts.readUnsignedInt(buffer));
        packet.setXuid(helper.readString(buffer));
        packet.setPlatformId(helper.readString(buffer));
        this.readFlags(buffer, helper, packet.getFlags());
    }

}
