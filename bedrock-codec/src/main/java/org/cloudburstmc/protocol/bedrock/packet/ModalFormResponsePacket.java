package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.ModalFormCancelReason;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.Optional;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ModalFormResponsePacket implements BedrockPacket {
    private int formId;
    private String formData;
    /**
     * The reason for why the form response was cancelled.
     *
     * @since 1.19.20
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<ModalFormCancelReason> cancelReason;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MODAL_FORM_RESPONSE;
    }

    @Override
    public ModalFormResponsePacket clone() {
        try {
            return (ModalFormResponsePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

