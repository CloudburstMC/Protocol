package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.NpcRequestType;
import com.nukkitx.protocol.bedrock.packet.NpcRequestPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NpcRequestSerializer_v291 implements BedrockPacketSerializer<NpcRequestPacket> {
    public static final NpcRequestSerializer_v291 INSTANCE = new NpcRequestSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NpcRequestPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        buffer.writeByte(packet.getRequestType().ordinal());
        helper.writeString(buffer, packet.getCommand());
        buffer.writeByte(packet.getActionType());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NpcRequestPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setRequestType(NpcRequestType.values()[buffer.readUnsignedByte()]);
        packet.setCommand(helper.readString(buffer));
        packet.setActionType(buffer.readUnsignedByte());
    }
}
