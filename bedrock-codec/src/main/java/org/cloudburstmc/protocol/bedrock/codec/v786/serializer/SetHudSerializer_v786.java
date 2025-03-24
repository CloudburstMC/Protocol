package org.cloudburstmc.protocol.bedrock.codec.v786.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.HudElement;
import org.cloudburstmc.protocol.bedrock.data.HudVisibility;
import org.cloudburstmc.protocol.bedrock.packet.SetHudPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SetHudSerializer_v786 implements BedrockPacketSerializer<SetHudPacket> {
    public static final SetHudSerializer_v786 INSTANCE = new SetHudSerializer_v786();

    private static final HudElement[] VALUES = HudElement.values();
    private static final HudVisibility[] VISIBILITIES = HudVisibility.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetHudPacket packet) {
        helper.writeArray(buffer, packet.getElements(), (buf, element) -> VarInts.writeInt(buf, element.ordinal()));
        VarInts.writeInt(buffer, packet.getVisibility().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetHudPacket packet) {
        helper.readArray(buffer, packet.getElements(), buf -> VALUES[VarInts.readInt(buf)]);
        packet.setVisibility(VISIBILITIES[VarInts.readInt(buffer)]);
    }
}
