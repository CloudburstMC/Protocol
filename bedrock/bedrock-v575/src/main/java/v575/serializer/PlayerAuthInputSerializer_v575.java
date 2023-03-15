package v575.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket;
import com.nukkitx.protocol.bedrock.v527.serializer.PlayerAuthInputSerializer_v527;
import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PlayerAuthInputSerializer_v575 extends PlayerAuthInputSerializer_v527 {

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerAuthInputPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeVector2f(buffer, packet.getAnalogMoveVector());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerAuthInputPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setAnalogMoveVector(helper.readVector2f(buffer));
    }
}