package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ModalFormResponsePacket;
import com.nukkitx.protocol.bedrock.v340.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModalFormResponseSerializer_v340 implements PacketSerializer<ModalFormResponsePacket> {
    public static final ModalFormResponseSerializer_v340 INSTANCE = new ModalFormResponseSerializer_v340();


    @Override
    public void serialize(ByteBuf buffer, ModalFormResponsePacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getFormId());
        BedrockUtils.writeString(buffer, packet.getFormData());
    }

    @Override
    public void deserialize(ByteBuf buffer, ModalFormResponsePacket packet) {
        packet.setFormId(VarInts.readUnsignedInt(buffer));
        packet.setFormData(BedrockUtils.readString(buffer));
    }
}
