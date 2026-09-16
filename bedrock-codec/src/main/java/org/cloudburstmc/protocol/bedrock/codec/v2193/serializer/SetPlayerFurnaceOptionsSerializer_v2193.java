package org.cloudburstmc.protocol.bedrock.codec.v2193.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.FurnaceOptions;
import org.cloudburstmc.protocol.bedrock.packet.SetPlayerFurnaceOptionsPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SetPlayerFurnaceOptionsSerializer_v2193 implements BedrockPacketSerializer<SetPlayerFurnaceOptionsPacket> {

    public static final SetPlayerFurnaceOptionsSerializer_v2193 INSTANCE = new SetPlayerFurnaceOptionsSerializer_v2193();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetPlayerFurnaceOptionsPacket packet) {
        buffer.writeByte(packet.getType().ordinal());
        writeFurnaceOptions(buffer, packet.getOptions());
    }

    private void writeFurnaceOptions(ByteBuf buffer, FurnaceOptions options) {
        VarInts.writeInt(buffer, options.getLeftTabIndex().ordinal());
        buffer.writeBoolean(options.isFiltering());
        VarInts.writeInt(buffer, options.getLayout().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetPlayerFurnaceOptionsPacket packet) {
        packet.setType(SetPlayerFurnaceOptionsPacket.FurnaceType.values()[buffer.readUnsignedByte()]);
        packet.setOptions(readFurnaceOptions(buffer));
    }

    private FurnaceOptions readFurnaceOptions(ByteBuf buffer) {
        return new FurnaceOptions(
                FurnaceOptions.FurnaceLeftTabIndex.values()[VarInts.readInt(buffer)],
                buffer.readBoolean(),
                FurnaceOptions.FurnaceLayout.values()[VarInts.readInt(buffer)]
        );
    }
}
