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
    protected final MetadataDictionary metadata = new MetadataDictionary();
    protected long uniqueEntityId;
    protected long runtimeEntityId;
    protected Item itemInstance;
    protected Vector3f position;
    protected Vector3f motion;
    protected boolean fromFishing;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
