package org.cloudburstmc.protocol.java.v754.serializer.login;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.login.EncryptionRequestPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EncryptionRequestSerializer_v754 implements JavaPacketSerializer<EncryptionRequestPacket> {
    public static final EncryptionRequestSerializer_v754 INSTANCE = new EncryptionRequestSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, EncryptionRequestPacket packet) {
        helper.writeByteArray(buffer, packet.getPublicKey());
        helper.writeByteArray(buffer, packet.getPrivateKey());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, EncryptionRequestPacket packet) {
        packet.setPublicKey(helper.readByteArray(buffer));
        packet.setPrivateKey(helper.readByteArray(buffer));
    }
}
