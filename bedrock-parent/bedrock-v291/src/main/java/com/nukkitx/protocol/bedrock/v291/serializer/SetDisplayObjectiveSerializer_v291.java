package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SetDisplayObjectivePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetDisplayObjectiveSerializer_v291 implements BedrockPacketSerializer<SetDisplayObjectivePacket> {
    public static final SetDisplayObjectiveSerializer_v291 INSTANCE = new SetDisplayObjectiveSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetDisplayObjectivePacket packet) {
        helper.writeString(buffer, packet.getDisplaySlot());
        helper.writeString(buffer, packet.getObjectiveId());
        helper.writeString(buffer, packet.getDisplayName());
        helper.writeString(buffer, packet.getCriteria());
        VarInts.writeInt(buffer, packet.getSortOrder());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetDisplayObjectivePacket packet) {
        packet.setDisplaySlot(helper.readString(buffer));
        packet.setObjectiveId(helper.readString(buffer));
        packet.setDisplayName(helper.readString(buffer));
        packet.setCriteria(helper.readString(buffer));
        packet.setSortOrder(VarInts.readInt(buffer));
    }
}
