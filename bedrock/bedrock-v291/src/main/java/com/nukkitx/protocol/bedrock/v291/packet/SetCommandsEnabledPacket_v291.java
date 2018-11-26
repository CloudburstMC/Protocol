package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.SetCommandsEnabledPacket;
import io.netty.buffer.ByteBuf;

public class SetCommandsEnabledPacket_v291 extends SetCommandsEnabledPacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeBoolean(commandsEnabled);
    }

    @Override
    public void decode(ByteBuf buffer) {
        commandsEnabled = buffer.readBoolean();
    }
}
