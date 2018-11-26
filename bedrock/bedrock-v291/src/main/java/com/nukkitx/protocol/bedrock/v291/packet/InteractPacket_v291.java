package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.InteractPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class InteractPacket_v291 extends InteractPacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(action);
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);

        if (action == 4/*Action.MOUSEOVER*/) {
            BedrockUtils.writeVector3f(buffer, mousePosition);
        }
    }

    @Override
    public void decode(ByteBuf buffer) {
        action = buffer.readByte();
        runtimeEntityId = VarInts.readUnsignedLong(buffer);

        if (action == 4/*Action.MOUSEOVER*/) {
            mousePosition = BedrockUtils.readVector3f(buffer);
        }
    }
}
