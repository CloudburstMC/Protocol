package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.ClientboundUpdateSoundDataSerializer_v2168;
import org.cloudburstmc.protocol.bedrock.data.sound.*;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundUpdateSoundDataPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class ClientboundUpdateSoundDataSerializer_v2225 extends ClientboundUpdateSoundDataSerializer_v2168 {

    public static final ClientboundUpdateSoundDataSerializer_v2225 INSTANCE = new ClientboundUpdateSoundDataSerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundUpdateSoundDataPacket packet) {
        buffer.writeLongLE(packet.getServerSoundHandle());

        VarInts.writeUnsignedInt(buffer, packet.getEvent().getType().ordinal());
        buffer.writeByte(packet.getEvent().getType().ordinal());

        switch (packet.getEvent().getType()) {
            case STOP:
            case PAUSE:
            case RESUME:
                break;
            case SET_VOLUME:
                buffer.writeFloatLE(((SetVolumeSoundData) packet.getEvent()).getVolume());
                break;
            case SET_PITCH:
                buffer.writeFloatLE(((SetPitchSoundData) packet.getEvent()).getPitch());
                break;
            case FADE:
                buffer.writeFloatLE(((FadeSoundData) packet.getEvent()).getDuration());
                buffer.writeFloatLE(((FadeSoundData) packet.getEvent()).getTargetVolume());
                break;
            case SEEK_TO:
                buffer.writeFloatLE(((SeekToSoundData) packet.getEvent()).getSeconds());
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundUpdateSoundDataPacket packet) {
        packet.setServerSoundHandle(buffer.readLongLE());

        int type = VarInts.readUnsignedInt(buffer);
        if (type != buffer.readUnsignedByte()) {
            throw new IllegalArgumentException();
        }

        switch (SoundDataType.values()[type]) {
            case STOP:
                packet.setEvent(new StopSoundData());
                break;
            case PAUSE:
                packet.setEvent(new PauseSoundData());
                break;
            case RESUME:
                packet.setEvent(new ResumeSoundData());
                break;
            case SET_VOLUME:
                packet.setEvent(new SetVolumeSoundData(buffer.readFloatLE()));
                break;
            case SET_PITCH:
                packet.setEvent(new SetPitchSoundData(buffer.readFloatLE()));
                break;
            case FADE:
                packet.setEvent(new FadeSoundData(buffer.readFloatLE(), buffer.readFloatLE()));
                break;
            case SEEK_TO:
                packet.setEvent(new SeekToSoundData(buffer.readFloatLE()));
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
