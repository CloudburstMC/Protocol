package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ModalFormRequestPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModalFormRequestSerializer_v354 implements PacketSerializer<ModalFormRequestPacket> {
    public static final ModalFormRequestSerializer_v354 INSTANCE = new ModalFormRequestSerializer_v354();


    @Override
    public void serialize(ByteBuf buffer, ModalFormRequestPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getFormId());
        BedrockUtils.writeString(buffer, packet.getFormData());
    }

    @Override
    public void deserialize(ByteBuf buffer, ModalFormRequestPacket packet) {
        packet.setFormId(VarInts.readUnsignedInt(buffer));
        packet.setFormData(BedrockUtils.readString(buffer));
    }
}
