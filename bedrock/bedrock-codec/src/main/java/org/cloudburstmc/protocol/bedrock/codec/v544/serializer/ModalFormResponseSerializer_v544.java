package org.cloudburstmc.protocol.bedrock.codec.v544.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.ModalFormResponseSerializer_v291;
import org.cloudburstmc.protocol.bedrock.data.ModalFormCancelReason;
import org.cloudburstmc.protocol.bedrock.packet.ModalFormResponsePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.Objects;
import java.util.Optional;

public class ModalFormResponseSerializer_v544 extends ModalFormResponseSerializer_v291 {

    protected static final ModalFormCancelReason[] VALUES = ModalFormCancelReason.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ModalFormResponsePacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getFormId());
        helper.writeOptional(buffer, Objects::nonNull, packet.getFormData(), helper::writeString);
        helper.writeOptional(buffer, Optional::isPresent, packet.getCancelReason(), (buf, reason) ->
                buf.writeByte(reason.get().ordinal()));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ModalFormResponsePacket packet) {
        packet.setFormId(VarInts.readUnsignedInt(buffer));
        packet.setFormData(helper.readOptional(buffer, null, helper::readString));
        packet.setCancelReason(helper.readOptional(buffer, Optional.empty(), byteBuf -> Optional.of(VALUES[byteBuf.readByte()])));
    }
}
