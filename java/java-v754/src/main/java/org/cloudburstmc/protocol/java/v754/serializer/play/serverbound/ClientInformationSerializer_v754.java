package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.Hand;
import org.cloudburstmc.protocol.java.data.text.ChatVisibility;
import org.cloudburstmc.protocol.java.packet.play.serverbound.ClientInformationPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientInformationSerializer_v754 implements JavaPacketSerializer<ClientInformationPacket> {
    public static final ClientInformationSerializer_v754 INSTANCE = new ClientInformationSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, ClientInformationPacket packet)
            throws PacketSerializeException {
        helper.writeString(buffer, packet.getLanguage());
        buffer.writeByte(packet.getViewDistance());
        VarInts.writeInt(buffer, packet.getVisibility().ordinal());
        buffer.writeBoolean(packet.isChatColors());
        buffer.writeByte(packet.getModelCustomisation());
        VarInts.writeInt(buffer, packet.getMainHand().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, ClientInformationPacket packet)
            throws PacketSerializeException {
        packet.setLanguage(helper.readString(buffer));
        packet.setViewDistance(buffer.readByte());
        packet.setVisibility(ChatVisibility.getById(VarInts.readInt(buffer)));
        packet.setChatColors(buffer.readBoolean());
        packet.setModelCustomisation(buffer.readUnsignedByte());
        packet.setMainHand(Hand.getById(buffer.readByte()));
    }
}
