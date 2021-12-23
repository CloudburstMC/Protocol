package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ModalFormRequestPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModalFormRequestSerializer_v291 implements BedrockPacketSerializer<ModalFormRequestPacket> {
    public static final ModalFormRequestSerializer_v291 INSTANCE = new ModalFormRequestSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ModalFormRequestPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getFormId());
        helper.writeString(buffer, packet.getFormData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ModalFormRequestPacket packet) {
        packet.setFormId(VarInts.readUnsignedInt(buffer));
        packet.setFormData(helper.readString(buffer));
    }
}
