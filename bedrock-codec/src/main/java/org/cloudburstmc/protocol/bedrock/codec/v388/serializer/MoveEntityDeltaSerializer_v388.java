package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.MoveEntityDeltaSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.MoveEntityDeltaPacket;
import org.cloudburstmc.protocol.bedrock.packet.MoveEntityDeltaPacket.Flag;
import org.cloudburstmc.protocol.common.util.TriConsumer;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MoveEntityDeltaSerializer_v388 extends MoveEntityDeltaSerializer_v291 {

    public static final MoveEntityDeltaSerializer_v388 INSTANCE = new MoveEntityDeltaSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MoveEntityDeltaPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());

        int flagsIndex = buffer.writerIndex();
        buffer.writeShortLE(0); // flags

        // For some reason, the bitflags are preset to 0xFFFF and removed if not used.
        int flags = 0xFFFF;
        for (Flag flag : FLAGS) {
            if (!packet.getFlags().contains(flag)) {
                flags &= ~(1 << flag.ordinal());
                continue;
            }

            TriConsumer<ByteBuf, BedrockCodecHelper, MoveEntityDeltaPacket> writer = this.writers.get(flag);
            if (writer != null) {
                writer.accept(buffer, helper, packet);
            }
        }

        // Go back to flags and set them
        buffer.setShortLE(flagsIndex, flags);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MoveEntityDeltaPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        int flags = buffer.readUnsignedShortLE();
        Set<Flag> flagSet = packet.getFlags();

        for (Flag flag : FLAGS) {
            if ((flags & (1 << flag.ordinal())) != 0) {
                flagSet.add(flag);
                TriConsumer<ByteBuf, BedrockCodecHelper, MoveEntityDeltaPacket> reader = this.readers.get(flag);
                if (reader != null) {
                    reader.accept(buffer, helper, packet);
                }
            }
        }
    }
}
