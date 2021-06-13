package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.bytes.Byte2ByteFunction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.entity.Equipment;
import org.cloudburstmc.protocol.java.data.entity.EquipmentSlot;
import org.cloudburstmc.protocol.java.data.inventory.ItemStack;
import org.cloudburstmc.protocol.java.packet.play.clientbound.SetEquipmentPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetEquipmentSerializer_v754 implements JavaPacketSerializer<SetEquipmentPacket> {
    public static final SetEquipmentSerializer_v754 INSTANCE = new SetEquipmentSerializer_v754();

    // I just copied this from the mc source code, as I wasn't sure that I would be able to do it right myself
    // so feel free to change it, especially the variable names

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetEquipmentPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getEntityId());
        int i = packet.getEquipment().size();

        for (int j = 0; j < i; ++j) {
            Equipment equipment = packet.getEquipment().get(j);
            EquipmentSlot slot = equipment.getSlot();
            boolean bl = j != i - 1;
            int k = slot.ordinal();
            buffer.writeByte(bl ? k | -128 : k);
            helper.writeItemStack(buffer, equipment.getItemStack());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetEquipmentPacket packet) throws PacketSerializeException {
        packet.setEntityId(helper.readVarInt(buffer));
        EquipmentSlot[] slots = EquipmentSlot.values();

        byte i;
        do {
            i = buffer.readByte();
            EquipmentSlot slot = slots[i & 127];
            ItemStack itemStack = helper.readItemStack(buffer);
            packet.getEquipment().add(new Equipment(slot, itemStack));
        } while((i & -128) != 0);

    }
}
