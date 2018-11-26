package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.CameraPacket;
import io.netty.buffer.ByteBuf;

public class CameraPacket_v291 extends CameraPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeLong(buffer, cameraUniqueEntityId);
        VarInts.writeLong(buffer, playerUniqueEntityId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        cameraUniqueEntityId = VarInts.readLong(buffer);
        playerUniqueEntityId = VarInts.readLong(buffer);
    }
}
