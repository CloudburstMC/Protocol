package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.ArrayList;
import java.util.List;

/**
 * Registers signed audio content for the sending player.
 * @since v2225
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerboundRegisterAudioContentPacket implements BedrockPacket {

    private final List<AudioContentRegistrationEntry> registrations = new ArrayList<>();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVERBOUND_REGISTER_AUDIO_CONTENT;
    }

    @Override
    public ServerboundRegisterAudioContentPacket clone() {
        try {
            return (ServerboundRegisterAudioContentPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Value
    public static class AudioContentRegistrationEntry {
        String audioContentID;
        String sharedMetadata;
        String serverContent;
        String playbackContent;
    }
}
