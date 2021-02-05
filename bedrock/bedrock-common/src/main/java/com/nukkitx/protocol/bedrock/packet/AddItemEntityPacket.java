package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.entity.EntityDataMap;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.math.vector.Vector3f;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class AddItemEntityPacket extends BedrockPacket {
    private final EntityDataMap metadata = new EntityDataMap();
    private long uniqueEntityId;
    private long runtimeEntityId;
    private ItemData itemInHand;
    private Vector3f position;
    private Vector3f motion;
    private boolean fromFishing;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADD_ITEM_ENTITY;
    }
}
