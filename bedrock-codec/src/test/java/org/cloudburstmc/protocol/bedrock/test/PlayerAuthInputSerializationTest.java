package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.PacketSerializeException;
import org.cloudburstmc.protocol.bedrock.codec.v2168.Bedrock_v2168;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.data.ClientPlayMode;
import org.cloudburstmc.protocol.bedrock.data.InputInteractionModel;
import org.cloudburstmc.protocol.bedrock.data.InputMode;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.PlayerAuthInputData;
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerAuthInputSerializationTest {
    private static final BedrockCodec[] CODECS = {Bedrock_v2168.CODEC, Bedrock_v2193.CODEC};

    @Test
    void inputFlagsRoundTrip() {
        for (BedrockCodec codec : CODECS) {
            String name = "v" + codec.getProtocolVersion();
            PlayerAuthInputPacket packet = packet();
            packet.getInputData().addAll(EnumSet.allOf(PlayerAuthInputData.class));
            packet.getInputData().removeAll(EnumSet.of(PlayerAuthInputData.PERFORM_ITEM_INTERACTION,
                    PlayerAuthInputData.PERFORM_ITEM_STACK_REQUEST, PlayerAuthInputData.PERFORM_BLOCK_ACTIONS,
                    PlayerAuthInputData.IN_CLIENT_PREDICTED_IN_VEHICLE));

            ByteBuf buffer = Unpooled.buffer();
            try {
                codec.tryEncode(codec.createHelper(), buffer, packet);
                PlayerAuthInputPacket decoded = (PlayerAuthInputPacket) codec.tryDecode(codec.createHelper(), buffer,
                        id(codec), PacketRecipient.SERVER);
                assertEquals(packet.getInputData(), decoded.getInputData(), name);
                assertFalse(buffer.isReadable(), name + " left bytes unread");
            } finally {
                buffer.release();
            }
        }
    }

    @Test
    void tooManyInputFlagsAreRefused() {
        for (BedrockCodec codec : CODECS) {
            ByteBuf buffer = inputData(codec, PlayerAuthInputData.values().length + 1);
            assertRefused(codec, buffer, "input flags but maximum is");
        }
    }

    @Test
    void unknownFlagsAreRefused() {
        for (BedrockCodec codec : CODECS) {
            ByteBuf buffer = inputData(codec, 1);
            VarInts.writeInt(buffer, PlayerAuthInputData.values().length);
            assertRefused(codec, buffer, "Unknown input flag");

            buffer = inputData(codec, 1);
            VarInts.writeInt(buffer, -1);
            assertRefused(codec, buffer, "Unknown input flag");
        }
    }

    private static void assertRefused(BedrockCodec codec, ByteBuf buffer, String message) {
        String name = "v" + codec.getProtocolVersion();
        try {
            PacketSerializeException e = assertThrows(PacketSerializeException.class,
                    () -> codec.tryDecode(codec.createHelper(), buffer, id(codec), PacketRecipient.SERVER), name);
            assertInstanceOf(IllegalArgumentException.class, e.getCause(), name);
            assertTrue(e.getCause().getMessage().contains(message), name + ": " + e.getCause().getMessage());
        } finally {
            buffer.release();
        }
    }

    /**
     * The start of a packet, up to and including the input flag count.
     */
    private static ByteBuf inputData(BedrockCodec codec, int count) {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeZero(8 * Float.BYTES); // rotation, position and motion
        if (codec.getProtocolVersion() < Bedrock_v2193.CODEC.getProtocolVersion()) {
            buffer.writeBoolean(true);
        }
        VarInts.writeUnsignedInt(buffer, count);
        return buffer;
    }

    private static int id(BedrockCodec codec) {
        return codec.getPacketDefinition(PlayerAuthInputPacket.class).getId();
    }

    private static PlayerAuthInputPacket packet() {
        PlayerAuthInputPacket packet = new PlayerAuthInputPacket();
        packet.setRotation(Vector3f.ZERO);
        packet.setPosition(Vector3f.ZERO);
        packet.setMotion(Vector2f.ZERO);
        packet.setInputMode(InputMode.MOUSE);
        packet.setPlayMode(ClientPlayMode.NORMAL);
        packet.setInputInteractionModel(InputInteractionModel.CROSSHAIR);
        packet.setInteractRotation(Vector2f.ZERO);
        packet.setDelta(Vector3f.ZERO);
        packet.setAnalogMoveVector(Vector2f.ZERO);
        packet.setCameraOrientation(Vector3f.ZERO);
        packet.setRawMoveVector(Vector2f.ZERO);
        return packet;
    }
}
