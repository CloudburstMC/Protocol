package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.bedrock.data.sound.*;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent to update sound data.
 *
 * @since v1001
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundUpdateSoundDataPacket implements BedrockPacket {

    private long serverSoundHandle;
    private SoundData event;
    /**
     * @since v2168
     */
    @Nullable
    private FadeSoundData fade;
    /**
     * @since v2168
     */
    @Nullable
    private PauseSoundData pause;
    /**
     * @since v2168
     */
    @Nullable
    private ResumeSoundData resume;
    /**
     * @since v2168
     */
    @Nullable
    private SeekToSoundData seekTo;
    /**
     * @since v2168
     */
    @Nullable
    private SetPitchSoundData pitch;
    /**
     * @since v2168
     */
    @Nullable
    private SetVolumeSoundData volume;
    /**
     * @since v2168
     */
    @Nullable
    private StopSoundData stop;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_UPDATE_SOUND_DATA;
    }

    @Override
    public BedrockPacket clone() {
        try {
            return (ClientboundUpdateSoundDataPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
