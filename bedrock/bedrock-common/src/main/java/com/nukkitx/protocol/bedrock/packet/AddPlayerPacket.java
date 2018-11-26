package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.EntityLink;
import com.nukkitx.protocol.bedrock.data.Item;
import com.nukkitx.protocol.bedrock.data.MetadataDictionary;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AddPlayerPacket extends BedrockPacket {
    protected final MetadataDictionary metadata = new MetadataDictionary();
    protected final List<EntityLink> entityLinks = new ArrayList<>();
    protected UUID uuid;
    protected String username;
    protected long uniqueEntityId;
    protected long runtimeEntityId;
    protected String platformChatId;
    protected Vector3f position;
    protected Vector3f motion;
    protected Vector3f rotation;
    protected Item hand;
    protected int playerFlags;
    protected int commandPermission;
    protected int worldFlags;
    protected int playerPermission;
    protected int customFlags;
    protected String deviceId;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
