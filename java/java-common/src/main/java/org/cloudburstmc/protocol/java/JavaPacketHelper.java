package org.cloudburstmc.protocol.java;

import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.util.Int2ObjectBiMap;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.cloudburstmc.protocol.java.data.entity.EntityType;
import org.cloudburstmc.protocol.java.data.profile.GameProfile;
import org.cloudburstmc.protocol.java.data.profile.property.Property;
import org.cloudburstmc.protocol.java.data.profile.property.PropertyMap;
import org.cloudburstmc.protocol.java.data.world.BlockEntityAction;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public abstract class JavaPacketHelper {
    protected static final InternalLogger log = InternalLoggerFactory.getInstance(JavaPacketHelper.class);

    protected final Int2ObjectBiMap<EntityType> entityTypes = new Int2ObjectBiMap<>();
    protected final Int2ObjectBiMap<BlockEntityAction> blockEntityActions = new Int2ObjectBiMap<>();

    protected JavaPacketHelper() {
        this.registerEntityTypes();
        this.registerBlockEntityActions();
    }

    public byte[] readByteArray(ByteBuf buffer) {
        Preconditions.checkNotNull(buffer, "buffer");
        int length = VarInts.readUnsignedInt(buffer);
        Preconditions.checkArgument(buffer.isReadable(length),
                 "Tried to read %s bytes but only has %s readable", length, buffer.readableBytes());
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        return bytes;
    }

    public void writeByteArray(ByteBuf buffer, byte[] bytes) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(bytes, "bytes");
        VarInts.writeUnsignedInt(buffer, bytes.length);
        buffer.writeBytes(bytes);
    }

    public String readString(ByteBuf buffer) {
        Preconditions.checkNotNull(buffer, "buffer");
        return new String(readByteArray(buffer), StandardCharsets.UTF_8);
    }

    public void writeString(ByteBuf buffer, String string) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(string, "string");
        writeByteArray(buffer, string.getBytes(StandardCharsets.UTF_8));
    }

    public GameProfile readGameProfile(ByteBuf buffer) {
        UUID id = readUUID(buffer);
        String name = null;
        if (buffer.readableBytes() > 0) {
            name = readString(buffer);
        }
        GameProfile profile = new GameProfile(id, name);
        if (buffer.readableBytes() <= 0) {
            return profile;
        }
        // Read properties
        int propertiesAmt = VarInts.readUnsignedInt(buffer);
        PropertyMap properties = new PropertyMap();
        for (int i = 0; i < propertiesAmt; i++) {
            Property property = new Property(readString(buffer), readString(buffer), buffer.readBoolean() ? readString(buffer) : null);
            properties.put(property.getName(), property);
        }
        profile.setProperties(properties);
        return profile;
    }

    public void writeGameProfile(ByteBuf buffer, GameProfile profile) {
        writeGameProfile(buffer, profile, false);
    }

    public void writeGameProfile(ByteBuf buffer, GameProfile profile, boolean writeProperties) {
        writeUUID(buffer, profile.getId());
        writeString(buffer, profile.getName() == null ? "" : profile.getName());
        if (writeProperties && profile.getProperties() != null && !profile.getProperties().isEmpty()) {
            VarInts.writeUnsignedInt(buffer, profile.getProperties().size());
            for (Property property : profile.getProperties().values()) {
                writeString(buffer, property.getName());
                writeString(buffer, property.getValue());
                buffer.writeBoolean(property.getSignature() != null);
                if (property.getSignature() != null) {
                    writeString(buffer, property.getSignature());
                }
            }
        }
    }

    public UUID readUUID(ByteBuf buffer) {
        return new UUID(buffer.readLong(), buffer.readLong());
    }

    public void writeUUID(ByteBuf buffer, UUID uuid) {
        buffer.writeLong(uuid.getMostSignificantBits());
        buffer.writeLong(uuid.getLeastSignificantBits());
    }

    public final int getEntityId(EntityType entityType) {
        return this.entityTypes.get(entityType);
    }

    public final EntityType getEntityType(int entityId) {
        return this.entityTypes.get(entityId);
    }

    public final int getBlockEntityActionId(BlockEntityAction action) {
        return this.blockEntityActions.get(action);
    }

    public final BlockEntityAction getBlockEntityAction(int actionId) {
        return this.blockEntityActions.get(actionId);
    }

    protected abstract void registerEntityTypes();

    protected abstract void registerBlockEntityActions();
}
