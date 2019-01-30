package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetDisplayObjectivePacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetDisplayObjectiveSerializer_v332 implements PacketSerializer<SetDisplayObjectivePacket> {
    public static final SetDisplayObjectiveSerializer_v332 INSTANCE = new SetDisplayObjectiveSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, SetDisplayObjectivePacket packet) {
        BedrockUtils.writeString(buffer, packet.getDisplaySlot());
        BedrockUtils.writeString(buffer, packet.getObjectiveId());
        BedrockUtils.writeString(buffer, packet.getDisplayName());
        BedrockUtils.writeString(buffer, packet.getCriteria());
        VarInts.writeInt(buffer, packet.getSortOrder());
    }

    @Override
    public void deserialize(ByteBuf buffer, SetDisplayObjectivePacket packet) {
        packet.setDisplaySlot(BedrockUtils.readString(buffer));
        packet.setObjectiveId(BedrockUtils.readString(buffer));
        packet.setDisplayName(BedrockUtils.readString(buffer));
        packet.setCriteria(BedrockUtils.readString(buffer));
        packet.setSortOrder(VarInts.readInt(buffer));
    }
}
