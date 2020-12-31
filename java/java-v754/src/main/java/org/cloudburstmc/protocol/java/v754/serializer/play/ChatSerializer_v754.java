package org.cloudburstmc.protocol.java.v754.serializer.play;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.text.ChatPosition;
import org.cloudburstmc.protocol.java.packet.play.ChatPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatSerializer_v754 implements JavaPacketSerializer<ChatPacket> {
    public static final ChatSerializer_v754 INSTANCE = new ChatSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, ChatPacket packet) {
        helper.writeString(buffer, GsonComponentSerializer.gson().serialize(packet.getMessage()));
        buffer.writeByte(packet.getPosition().ordinal());
        helper.writeUUID(buffer, packet.getSenderUuid());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, ChatPacket packet) {
        packet.setMessage(GsonComponentSerializer.gson().deserialize(helper.readString(buffer)));
        packet.setPosition(ChatPosition.getById(buffer.readByte()));
        packet.setSenderUuid(helper.readUUID(buffer));
    }
}
