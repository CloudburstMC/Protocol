package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.AdventureSettingsPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdventureSettingsSerializer_v332 implements PacketSerializer<AdventureSettingsPacket> {
    public static final AdventureSettingsSerializer_v332 INSTANCE = new AdventureSettingsSerializer_v332();

    @Override
    public void serialize(ByteBuf buffer, AdventureSettingsPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getPlayerFlags());
        VarInts.writeUnsignedInt(buffer, packet.getCommandPermission());
        VarInts.writeUnsignedInt(buffer, packet.getWorldFlags());
        VarInts.writeUnsignedInt(buffer, packet.getPlayerPermission());
        VarInts.writeUnsignedInt(buffer, packet.getCustomFlags());
        buffer.writeLongLE(packet.getUniqueEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, AdventureSettingsPacket packet) {
        packet.setPlayerFlags(VarInts.readUnsignedInt(buffer));
        packet.setCommandPermission(VarInts.readUnsignedInt(buffer));
        packet.setWorldFlags(VarInts.readUnsignedInt(buffer));
        packet.setPlayerPermission(VarInts.readUnsignedInt(buffer));
        packet.setCustomFlags(VarInts.readUnsignedInt(buffer));
        packet.setUniqueEntityId(buffer.readLongLE());
    }
}
