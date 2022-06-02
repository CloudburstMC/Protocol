package com.nukkitx.protocol.bedrock.v527.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.Ability;
import com.nukkitx.protocol.bedrock.data.AbilityType;
import com.nukkitx.protocol.bedrock.packet.RequestAbilityPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestAbilitySerializer_v527 implements BedrockPacketSerializer<RequestAbilityPacket> {
    public static final RequestAbilitySerializer_v527 INSTANCE = new RequestAbilitySerializer_v527();

    protected static final Ability[] ABILITIES = Ability.values();
    protected static final AbilityType[] ABILITY_TYPES = AbilityType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RequestAbilityPacket packet) {
        VarInts.writeInt(buffer, packet.getAbility().ordinal());
        buffer.writeByte(packet.getType().ordinal());
        buffer.writeBoolean(packet.isBoolValue());
        buffer.writeFloatLE(packet.getFloatValue());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RequestAbilityPacket packet) {
        packet.setAbility(ABILITIES[VarInts.readInt(buffer)]);
        packet.setType(ABILITY_TYPES[buffer.readUnsignedByte()]);
        packet.setBoolValue(buffer.readBoolean());
        packet.setFloatValue(buffer.readFloatLE());
    }
}
