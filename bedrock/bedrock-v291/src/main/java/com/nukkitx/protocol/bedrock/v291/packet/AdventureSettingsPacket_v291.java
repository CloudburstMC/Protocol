package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.AdventureSettingsPacket;
import io.netty.buffer.ByteBuf;

public class AdventureSettingsPacket_v291 extends AdventureSettingsPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedInt(buffer, playerFlags);
        VarInts.writeUnsignedInt(buffer, commandPermission);
        VarInts.writeUnsignedInt(buffer, worldFlags);
        VarInts.writeUnsignedInt(buffer, playerPermission);
        VarInts.writeUnsignedInt(buffer, customFlags);
        buffer.writeLongLE(uniqueEntityId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        playerFlags = VarInts.readUnsignedInt(buffer);
        commandPermission = VarInts.readUnsignedInt(buffer);
        worldFlags = VarInts.readUnsignedInt(buffer);
        playerPermission = VarInts.readUnsignedInt(buffer);
        customFlags = VarInts.readUnsignedInt(buffer);
        uniqueEntityId = buffer.readLongLE();
    }
}
