package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.Attribute;
import com.nukkitx.protocol.bedrock.data.EntityLink;
import com.nukkitx.protocol.bedrock.data.MetadataDictionary;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddEntityPacket extends BedrockPacket {
    protected final List<Attribute> attributes = new ArrayList<>();
    protected final MetadataDictionary metadata = new MetadataDictionary();
    protected final List<EntityLink> entityLinks = new ArrayList<>();
    protected long uniqueEntityId;
    protected long runtimeEntityId;
    protected int entityType;
    protected Vector3f position;
    protected Vector3f motion;
    protected Vector3f rotation;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
