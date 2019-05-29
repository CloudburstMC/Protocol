package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.Attribute;
import com.nukkitx.protocol.bedrock.data.EntityDataDictionary;
import com.nukkitx.protocol.bedrock.data.EntityLink;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddEntityPacket extends BedrockPacket {
    private final List<Attribute> attributes = new ArrayList<>();
    private final EntityDataDictionary metadata = new EntityDataDictionary();
    private final List<EntityLink> entityLinks = new ArrayList<>();
    private long uniqueEntityId;
    private long runtimeEntityId;
    private String identifier;
    private int entityType;
    private Vector3f position;
    private Vector3f motion;
    private Vector3f rotation;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
