package com.nukkitx.protocol.bedrock.beta.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.AbilityType;
import com.nukkitx.protocol.bedrock.packet.RequestAbilityPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestAbilitySerializerBeta implements BedrockPacketSerializer<RequestAbilityPacket> {
    public static final RequestAbilitySerializerBeta INSTANCE = new RequestAbilitySerializerBeta();

    private static final AbilityType[] ABILITIES = AbilityType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RequestAbilityPacket packet) {
        VarInts.writeInt(buffer, packet.getAbility());
        buffer.writeByte(packet.getType().ordinal());
        buffer.writeBoolean(packet.isBoolValue());
        buffer.writeFloatLE(packet.getFloatValue());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RequestAbilityPacket packet) {
        packet.setAbility(VarInts.readInt(buffer));
        packet.setType(ABILITIES[buffer.readUnsignedByte()]);
        packet.setBoolValue(buffer.readBoolean());
        packet.setFloatValue(buffer.readFloatLE());
    }
}
