package org.cloudburstmc.protocol.bedrock.netty.codec.encryption;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.CorruptedFrameException;
import org.cloudburstmc.protocol.bedrock.netty.BedrockBatchWrapper;
import org.cloudburstmc.protocol.bedrock.util.EncryptionUtils;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BedrockEncryptionDecoderTest {

    private static final SecretKey KEY = new SecretKeySpec(new byte[32], "AES");

    @Test
    public void decryptsBatchesWithValidTrailers() {
        EmbeddedChannel encoder = new EmbeddedChannel(new BedrockEncryptionEncoder(KEY, EncryptionUtils.createCipher(true, true, KEY)));
        EmbeddedChannel decoder = new EmbeddedChannel(new BedrockEncryptionDecoder(KEY, EncryptionUtils.createCipher(true, false, KEY)));
        try {
            for (String payload : new String[]{"first batch", "second batch"}) {
                decoder.writeInbound(encrypt(encoder, payload));
                BedrockBatchWrapper decrypted = decoder.readInbound();
                try {
                    assertArrayEquals(payload.getBytes(StandardCharsets.UTF_8), ByteBufUtil.getBytes(decrypted.getCompressed()));
                } finally {
                    decrypted.release();
                }
            }
        } finally {
            encoder.finishAndReleaseAll();
            decoder.finishAndReleaseAll();
        }
    }

    @Test
    public void refusesBatchWithBadTrailer() {
        EmbeddedChannel encoder = new EmbeddedChannel(new BedrockEncryptionEncoder(KEY, EncryptionUtils.createCipher(true, true, KEY)));
        EmbeddedChannel decoder = new EmbeddedChannel(new BedrockEncryptionDecoder(KEY, EncryptionUtils.createCipher(true, false, KEY)));
        try {
            BedrockBatchWrapper batch = encrypt(encoder, "batch");
            ByteBuf data = batch.getCompressed();
            int last = data.writerIndex() - 1;
            data.setByte(last, data.getByte(last) ^ 1);
            assertThrows(CorruptedFrameException.class, () -> decoder.writeInbound(batch));
            assertNull(decoder.readInbound());
        } finally {
            encoder.finishAndReleaseAll();
            decoder.finishAndReleaseAll();
        }
    }

    private static BedrockBatchWrapper encrypt(EmbeddedChannel encoder, String payload) {
        encoder.writeOutbound(BedrockBatchWrapper.newInstance(Unpooled.copiedBuffer(payload, StandardCharsets.UTF_8), null));
        return encoder.readOutbound();
    }
}
