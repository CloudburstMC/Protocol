package org.cloudburstmc.protocol.bedrock.codec.v649.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.HudElement;
import org.cloudburstmc.protocol.bedrock.data.HudVisibility;
import org.cloudburstmc.protocol.bedrock.packet.SetHudPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SetHudSerializer_v649 implements BedrockPacketSerializer<SetHudPacket> {
    public static final SetHudSerializer_v649 INSTANCE = new SetHudSerializer_v649();

    private static final HudElement[] VALUES = HudElement.values();
    private static final HudVisibility[] VISIBILITIES = HudVisibility.values();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetHudPacket packet) {
        helper.writeArray(buffer, packet.getElements(), (buf, element) -> VarInts.writeUnsignedInt(buf, element.ordinal()));
        buffer.writeByte(packet.getVisibility().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetHudPacket packet) {
        helper.readArray(buffer, packet.getElements(), buf -> VALUES[VarInts.readUnsignedInt(buf)]);
        packet.setVisibility(VISIBILITIES[buffer.readUnsignedByte()]);
    }
}
