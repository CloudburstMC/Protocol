package org.cloudburstmc.protocol.java.v754;

import lombok.experimental.UtilityClass;
import org.cloudburstmc.protocol.java.JavaPacketCodec;
import org.cloudburstmc.protocol.java.packet.State;
import org.cloudburstmc.protocol.java.packet.handshake.*;
import org.cloudburstmc.protocol.java.packet.login.*;
import org.cloudburstmc.protocol.java.packet.status.*;
import org.cloudburstmc.protocol.java.v754.serializer.handshake.*;
import org.cloudburstmc.protocol.java.v754.serializer.login.*;
import org.cloudburstmc.protocol.java.v754.serializer.status.*;

@UtilityClass
public class Java_v754 {
    public static final JavaPacketCodec V754_CODEC = JavaPacketCodec.builder()
            .protocolVersion(754)
            .minecraftVersion("1.16.4")
            .helper(JavaPacketHelper_v754.INSTANCE)
            .codec(State.HANDSHAKING, JavaPacketCodec.JavaStateCodec.builder()
                    .registerServerbound(HandshakingPacket.class, HandshakingSerializer_v754.INSTANCE, 0)
                    // .registerClientbound(LegacyServerListPingPacket.class, LegacyServerListPingSerializer_v754.INSTANCE, 254) // TODO
                    .build()
            ).codec(State.STATUS, JavaPacketCodec.JavaStateCodec.builder()
                    .registerClientbound(StatusResponsePacket.class, StatusResponseSerializer_v754.INSTANCE, 0)
                    .registerClientbound(PongPacket.class, PongSerializer_v754.INSTANCE, 1)
                    .registerServerbound(StatusRequestPacket.class, StatusRequestSerializer_v754.INSTANCE, 0)
                    .registerServerbound(PingPacket.class, PingSerializer_v754.INSTANCE, 1)
                    .build()
            ).codec(State.LOGIN, JavaPacketCodec.JavaStateCodec.builder()
                    .registerClientbound(DisconnectPacket.class, DisconnectPacketSerializer_v754.INSTANCE, 0)
                    .registerClientbound(EncryptionRequestPacket.class, EncryptionRequestSerializer_v754.INSTANCE, 1)
                    .registerClientbound(LoginSuccessPacket.class, LoginSuccessSerializer_v754.INSTANCE, 2)
                    .registerClientbound(SetCompressionPacket.class, SetCompressionSerializer_v754.INSTANCE, 3)
                    .registerClientbound(CustomQueryRequestPacket.class, CustomQueryRequestSerializer_v754.INSTANCE, 4)
                    .registerServerbound(LoginStartPacket.class, LoginStartSerializer_v754.INSTANCE, 0)
                    .registerServerbound(EncryptionResponsePacket.class, EncryptionResponseSerializer_v754.INSTANCE, 1)
                    .registerServerbound(CustomQueryResponsePacket.class, CustomQueryResponseSerializer_v754.INSTANCE, 2)
                    .build()
            ).build();
}
