package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.ContainerId;
import com.nukkitx.protocol.bedrock.packet.PlayerHotbarPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerHotbarSerializer_v313 implements PacketSerializer<PlayerHotbarPacket> {
    public static final PlayerHotbarSerializer_v313 INSTANCE = new PlayerHotbarSerializer_v313();

    @Override
    public void serialize(ByteBuf buffer, PlayerHotbarPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getSelectedHotbarSlot());
        buffer.writeByte(packet.getContainerId().id());
        buffer.writeBoolean(packet.isSelectHotbarSlot());
    }

    @Override
    public void deserialize(ByteBuf buffer, PlayerHotbarPacket packet) {
        packet.setSelectedHotbarSlot(VarInts.readUnsignedInt(buffer));
        packet.setContainerId(ContainerId.byId(buffer.readUnsignedByte()));
        packet.setSelectHotbarSlot(buffer.readBoolean());
    }
}
