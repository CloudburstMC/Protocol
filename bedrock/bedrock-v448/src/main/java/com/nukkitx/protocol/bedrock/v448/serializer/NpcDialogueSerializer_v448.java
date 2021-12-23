package com.nukkitx.protocol.bedrock.v448.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.NpcDialoguePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class NpcDialogueSerializer_v448 implements BedrockPacketSerializer<NpcDialoguePacket> {
    public static final NpcDialogueSerializer_v448 INSTANCE = new NpcDialogueSerializer_v448();

    private static final NpcDialoguePacket.Action[] VALUES = NpcDialoguePacket.Action.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NpcDialoguePacket packet) {
        buffer.writeLongLE(packet.getUniqueEntityId());
        VarInts.writeInt(buffer, packet.getAction().ordinal());
        helper.writeString(buffer, packet.getDialogue());
        helper.writeString(buffer, packet.getSceneName());
        helper.writeString(buffer, packet.getNpcName());
        helper.writeString(buffer, packet.getActionJson());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NpcDialoguePacket packet) {
        packet.setUniqueEntityId(buffer.readLongLE());
        packet.setAction(VALUES[VarInts.readInt(buffer)]);
        packet.setDialogue(helper.readString(buffer));
        packet.setSceneName(helper.readString(buffer));
        packet.setNpcName(helper.readString(buffer));
        packet.setActionJson(helper.readString(buffer));
    }
}
