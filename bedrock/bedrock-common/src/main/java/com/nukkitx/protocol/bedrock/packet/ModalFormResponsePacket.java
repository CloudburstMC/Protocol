package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.ModalFormCancelReason;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Optional;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ModalFormResponsePacket extends BedrockPacket {
    private int formId;
    private String formData;
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<ModalFormCancelReason> cancelReason = Optional.empty();

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MODAL_FORM_RESPONSE;
    }
}
