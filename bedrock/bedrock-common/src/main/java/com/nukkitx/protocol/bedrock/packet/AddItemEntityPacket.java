package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.Item;
import com.nukkitx.protocol.bedrock.data.MetadataDictionary;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddItemEntityPacket extends BedrockPacket {
    private final MetadataDictionary metadata = new MetadataDictionary();
    private long uniqueEntityId;
    private long runtimeEntityId;
    private Item itemInstance;
    private Vector3f position;
    private Vector3f motion;
    private boolean fromFishing;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
