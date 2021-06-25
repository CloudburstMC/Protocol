package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.entity.Equipment;
import org.cloudburstmc.protocol.java.data.entity.EquipmentSlot;
import org.cloudburstmc.protocol.java.data.inventory.ItemStack;
import org.cloudburstmc.protocol.java.packet.play.clientbound.SetEquipmentPacket;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetEquipmentSerializer_v754 implements JavaPacketSerializer<SetEquipmentPacket> {
    public static final SetEquipmentSerializer_v754 INSTANCE = new SetEquipmentSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetEquipmentPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getEntityId());
        List<Equipment> equipment = packet.getEquipment();
        for (int i = 0; i < equipment.size(); i++) {
            int rawSlot = equipment.get(i).getSlot().ordinal();
            if (i != equipment.size() - 1) {
                rawSlot = rawSlot | 128;
            }
            buffer.writeByte(rawSlot);
            helper.writeItemStack(buffer, equipment.get(i).getItemStack());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetEquipmentPacket packet) throws PacketSerializeException {
        packet.setEntityId(helper.readVarInt(buffer));
        boolean hasNextEntry = true;
        List<Equipment> list = new ArrayList<>();
        while (hasNextEntry) {
            int rawSlot = buffer.readByte();
            EquipmentSlot slot = EquipmentSlot.getById((byte) rawSlot & 127);
            ItemStack itemStack = helper.readItemStack(buffer);
            list.add(new Equipment(slot, itemStack));
            hasNextEntry = (rawSlot & 128) == 128;
        }
        packet.setEquipment(list);
    }
}
