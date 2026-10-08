package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.PacketSerializeException;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.BlockEntityDataPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EncodingSettingsTest {

    @Test
    void networkNbtOverTheLimitIsRefused() {
        byte[] body = {
                0, 0, 0, // block position 0, 0, 0
                7, 0, // unnamed root TAG_Byte_Array
                (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x02 // ZigZag VarInt length 2 MiB
        };
        BedrockCodec codec = Bedrock_v2193.CODEC;
        int id = codec.getPacketDefinition(BlockEntityDataPacket.class).getId();

        PacketSerializeException e = assertThrows(PacketSerializeException.class, () ->
                codec.tryDecode(codec.createHelper(), Unpooled.wrappedBuffer(body), id, PacketRecipient.SERVER));
        Throwable cause = e;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        assertTrue(String.valueOf(cause.getMessage()).startsWith("Read size exceeded limit"), cause.toString());
    }
}
