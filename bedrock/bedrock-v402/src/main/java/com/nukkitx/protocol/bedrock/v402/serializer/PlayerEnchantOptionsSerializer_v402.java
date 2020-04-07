package com.nukkitx.protocol.bedrock.v402.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.EnchantData;
import com.nukkitx.protocol.bedrock.data.EnchantOptionData;
import com.nukkitx.protocol.bedrock.packet.PlayerEnchantOptionsPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerEnchantOptionsSerializer_v402 implements PacketSerializer<PlayerEnchantOptionsPacket> {

    public static final PlayerEnchantOptionsSerializer_v402 INSTANCE = new PlayerEnchantOptionsSerializer_v402();

    @Override
    public void serialize(ByteBuf buffer, PlayerEnchantOptionsPacket packet) {
        BedrockUtils.writeArray(buffer, packet.getOptions(), this::serializeOption);
        buffer.writeIntLE(packet.getInt0());
    }

    @Override
    public void deserialize(ByteBuf buffer, PlayerEnchantOptionsPacket packet) {
        BedrockUtils.readArray(buffer, packet.getOptions(), this::deserializeOption);
        packet.setInt0(buffer.readIntLE());
    }

    protected void serializeOption(ByteBuf buffer, EnchantOptionData option) {
        VarInts.writeInt(buffer, option.getVarInt0());
        buffer.writeIntLE(option.getPrimarySlot());
        BedrockUtils.writeArray(buffer, option.getSlot0(), this::serializeEnchant);
        BedrockUtils.writeArray(buffer, option.getSlot1(), this::serializeEnchant);
        BedrockUtils.writeArray(buffer, option.getSlot2(), this::serializeEnchant);
        BedrockUtils.writeString(buffer, option.getDescription());
    }

    protected EnchantOptionData deserializeOption(ByteBuf buffer) {
        int varInt0 = VarInts.readInt(buffer);
        int primarySlot = buffer.readIntLE();
        List<EnchantData> enchants1 = new ArrayList<>();
        BedrockUtils.readArray(buffer, enchants1, this::deserializeEnchant);
        List<EnchantData> enchants2 = new ArrayList<>();
        BedrockUtils.readArray(buffer, enchants2, this::deserializeEnchant);
        List<EnchantData> enchants3 = new ArrayList<>();
        BedrockUtils.readArray(buffer, enchants3, this::deserializeEnchant);
        String string0 = BedrockUtils.readString(buffer);
        return new EnchantOptionData(varInt0, primarySlot, enchants1, enchants2, enchants3, string0);
    }

    protected void serializeEnchant(ByteBuf buffer, EnchantData enchant) {
        buffer.writeByte(enchant.getType());
        buffer.writeByte(enchant.getLevel());
    }

    protected EnchantData deserializeEnchant(ByteBuf buffer) {
        int type = buffer.readUnsignedByte();
        int level = buffer.readUnsignedByte();
        return new EnchantData(type, level);
    }
}
