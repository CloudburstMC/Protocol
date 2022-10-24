package org.cloudburstmc.protocol.bedrock.netty.codec.packet;

import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper;

@ChannelHandler.Sharable
public class BedrockPacketCodec_v3 extends BedrockPacketCodec {

    @Override
    public void encodeHeader(ByteBuf buf, BedrockPacketWrapper msg) {
        int header = 0;
        header |= (msg.getPacketId() & 0x3ff);
        header |= (msg.getSenderId() & 3) << 10;
        header |= (msg.getClientId() & 3) << 12;
        VarInts.writeUnsignedInt(buf, header);
    }

    @Override
    public void decodeHeader(ByteBuf buf, BedrockPacketWrapper msg) {
        int header = VarInts.readUnsignedInt(buf);
        msg.setPacketId(header & 0x3ff);
        msg.setSenderId((header >> 10) & 3);
        msg.setClientId((header >> 12) & 3);
    }
}
