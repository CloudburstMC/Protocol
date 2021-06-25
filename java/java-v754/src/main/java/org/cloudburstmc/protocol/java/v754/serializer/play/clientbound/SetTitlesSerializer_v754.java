package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.SetTitlesPacket;

import static org.cloudburstmc.protocol.java.packet.play.clientbound.SetTitlesPacket.Type;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetTitlesSerializer_v754 implements JavaPacketSerializer<SetTitlesPacket> {
    public static final SetTitlesSerializer_v754 INSTANCE = new SetTitlesSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetTitlesPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getType().ordinal());
        switch (packet.getType()) {
            case TITLE:
            case SUBTITLE:
            case ACTIONBAR:
                helper.writeComponent(buffer, packet.getText());
                break;
            case TIMES:
                buffer.writeInt(packet.getFadeInTime());
                buffer.writeInt(packet.getStayTime());
                buffer.writeInt(packet.getFadeOutTime());
                break;
            default:
                break;
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetTitlesPacket packet) throws PacketSerializeException {
        packet.setType(Type.getById(helper.readVarInt(buffer)));
        switch (packet.getType()) {
            case TITLE:
            case SUBTITLE:
            case ACTIONBAR:
                packet.setText(helper.readComponent(buffer));
                break;
            case TIMES:
                packet.setFadeInTime(buffer.readInt());
                packet.setStayTime(buffer.readInt());
                packet.setFadeOutTime(buffer.readInt());
                break;
            default:
                break;
        }
    }
}
