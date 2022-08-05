package com.nukkitx.protocol.bedrock.v544.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.ModalFormCancelReason;
import com.nukkitx.protocol.bedrock.packet.ModalFormResponsePacket;
import com.nukkitx.protocol.bedrock.v291.serializer.ModalFormResponseSerializer_v291;
import io.netty.buffer.ByteBuf;

import java.util.Objects;
import java.util.Optional;

public class ModalFormResponseSerializer_v544 extends ModalFormResponseSerializer_v291 {

    protected static final ModalFormCancelReason[] VALUES = ModalFormCancelReason.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ModalFormResponsePacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getFormId());
        helper.writeOptional(buffer, Objects::nonNull, packet.getFormData(), helper::writeString);
        helper.writeOptional(buffer, Optional::isPresent, packet.getCancelReason(), (buf, reason) ->
                buf.writeByte(reason.get().ordinal()));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ModalFormResponsePacket packet) {
        packet.setFormId(VarInts.readUnsignedInt(buffer));
        packet.setFormData(helper.readOptional(buffer, null, helper::readString));
        packet.setCancelReason(helper.readOptional(buffer, Optional.empty(), byteBuf -> Optional.of(VALUES[byteBuf.readByte()])));
    }
}
