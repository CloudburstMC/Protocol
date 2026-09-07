package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v1001.serializer.ClientboundUpdateSoundDataSerializer_v1001;
import org.cloudburstmc.protocol.bedrock.data.sound.*;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundUpdateSoundDataPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class ClientboundUpdateSoundDataSerializer_v2168 extends ClientboundUpdateSoundDataSerializer_v1001 {

    public static final ClientboundUpdateSoundDataSerializer_v2168 INSTANCE = new ClientboundUpdateSoundDataSerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundUpdateSoundDataPacket packet) {
        buffer.writeLongLE(packet.getServerSoundHandle());

        this.writeType(buffer, SoundDataType.STOP);

        SetVolumeSoundData volume = packet.getVolume();
        if (volume == null) {
            this.writeType(buffer, SoundDataType.STOP);
        } else {
            this.writeType(buffer, SoundDataType.SET_VOLUME);
            buffer.writeFloatLE(volume.getVolume());
        }

        SetPitchSoundData pitch = packet.getPitch();
        if (pitch == null) {
            this.writeType(buffer, SoundDataType.STOP);
        } else {
            this.writeType(buffer, SoundDataType.SET_PITCH);
            buffer.writeFloatLE(pitch.getPitch());
        }

        FadeSoundData fade = packet.getFade();
        if (fade == null) {
            this.writeType(buffer, SoundDataType.STOP);
        } else {
            this.writeType(buffer, SoundDataType.FADE);
            buffer.writeFloatLE(fade.getDuration());
            buffer.writeFloatLE(fade.getTargetVolume());
        }

        SeekToSoundData seekTo = packet.getSeekTo();
        if (seekTo == null) {
            this.writeType(buffer, SoundDataType.STOP);
        } else {
            this.writeType(buffer, SoundDataType.SEEK_TO);
            buffer.writeFloatLE(seekTo.getSeconds());
        }

        this.writeType(buffer, packet.getPause() == null ? SoundDataType.STOP : SoundDataType.PAUSE);
        this.writeType(buffer, packet.getResume() == null ? SoundDataType.STOP : SoundDataType.RESUME);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundUpdateSoundDataPacket packet) {
        packet.setServerSoundHandle(buffer.readLongLE());

        Object stop = this.readSoundData(buffer);
        packet.setStop(stop instanceof StopSoundData ? (StopSoundData) stop : null);

        Object volume = this.readSoundData(buffer);
        packet.setVolume(volume instanceof SetVolumeSoundData ? (SetVolumeSoundData) volume : null);

        Object pitch = this.readSoundData(buffer);
        packet.setPitch(pitch instanceof SetPitchSoundData ? (SetPitchSoundData) pitch : null);

        Object fade = this.readSoundData(buffer);
        packet.setFade(fade instanceof FadeSoundData ? (FadeSoundData) fade : null);

        Object seekTo = this.readSoundData(buffer);
        packet.setSeekTo(seekTo instanceof SeekToSoundData ? (SeekToSoundData) seekTo : null);

        Object pause = this.readSoundData(buffer);
        packet.setPause(pause instanceof PauseSoundData ? (PauseSoundData) pause : null);

        Object resume = this.readSoundData(buffer);
        packet.setResume(resume instanceof ResumeSoundData ? (ResumeSoundData) resume : null);
    }

    protected void writeType(ByteBuf buffer, SoundDataType type) {
        VarInts.writeUnsignedInt(buffer, type.ordinal());
    }

    protected Object readSoundData(ByteBuf buffer) {
        switch (SoundDataType.from(VarInts.readUnsignedInt(buffer))) {
            case SET_VOLUME:
                return new SetVolumeSoundData(buffer.readFloatLE());
            case SET_PITCH:
                return new SetPitchSoundData(buffer.readFloatLE());
            case FADE: {
                float duration = buffer.readFloatLE();
                return new FadeSoundData(buffer.readFloatLE(), duration);
            }
            case SEEK_TO:
                return new SeekToSoundData(buffer.readFloatLE());
            case PAUSE:
                return new PauseSoundData();
            case RESUME:
                return new ResumeSoundData();
            default:
                return new StopSoundData();
        }
    }
}
