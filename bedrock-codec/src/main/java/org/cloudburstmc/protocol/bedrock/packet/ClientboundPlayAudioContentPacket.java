package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Provides signed audio content and requests playback.
 * @since v2225
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundPlayAudioContentPacket implements BedrockPacket {

    private String sharedMetadata;
    private String playbackContent;
    private AudioContentPlaybackType playbackType;
    private PlaySoundPacket playSound;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_PLAY_AUDIO_CONTENT;
    }

    @Override
    public ClientboundPlayAudioContentPacket clone() {
        try {
            return (ClientboundPlayAudioContentPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public enum AudioContentPlaybackType {
        MUSIC("Music"),
        SOUND("Sound");

        @Getter
        private final String name;

        AudioContentPlaybackType(String name) {
            this.name = name;
        }

        public static AudioContentPlaybackType fromName(String name) {
            return name.equals(MUSIC.name) ? MUSIC : SOUND;
        }
    }
}
