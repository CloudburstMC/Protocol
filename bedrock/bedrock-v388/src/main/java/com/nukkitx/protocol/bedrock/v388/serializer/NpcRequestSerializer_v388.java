package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.NpcRequestPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.NpcRequestPacket.Type;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NpcRequestSerializer_v388 implements PacketSerializer<NpcRequestPacket> {
    public static final NpcRequestSerializer_v388 INSTANCE = new NpcRequestSerializer_v388();


    @Override
    public void serialize(ByteBuf buffer, NpcRequestPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        buffer.writeByte(packet.getRequestType().ordinal());
        BedrockUtils.writeString(buffer, packet.getCommand());
        buffer.writeByte(packet.getActionType());
    }

    @Override
    public void deserialize(ByteBuf buffer, NpcRequestPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setRequestType(Type.values()[buffer.readUnsignedByte()]);
        packet.setCommand(BedrockUtils.readString(buffer));
        packet.setActionType(buffer.readUnsignedByte());
    }
}
