package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.EntityDataDictionary;
import com.nukkitx.protocol.bedrock.data.ItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddItemEntityPacket extends BedrockPacket {
    private final EntityDataDictionary metadata = new EntityDataDictionary();
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
}
