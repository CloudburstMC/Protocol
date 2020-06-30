package com.nukkitx.protocol.bedrock.v407;

import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.entity.EntityLinkData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemInstance;
import com.nukkitx.protocol.bedrock.v390.BedrockPacketHelper_v390;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.data.command.CommandParamType.*;
import static com.nukkitx.protocol.bedrock.data.command.CommandParamType.TARGET;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v407 extends BedrockPacketHelper_v390 {
    public static final BedrockPacketHelper_v407 INSTANCE = new BedrockPacketHelper_v407();

    @Override
    protected void registerEntityData() {
        super.registerEntityData();

        this.addEntityData(113, EntityData.LOW_TIER_CURED_TRADE_DISCOUNT);
        this.addEntityData(114, EntityData.HIGH_TIER_CURED_TRADE_DISCOUNT);
        this.addEntityData(115, EntityData.NEARBY_CURED_TRADE_DISCOUNT);
        this.addEntityData(116, EntityData.NEARBY_CURED_DISCOUNT_TIME_STAMP);
        this.addEntityData(117, EntityData.HITBOX);
        this.addEntityData(118, EntityData.IS_BUOYANT);
        this.addEntityData(119, EntityData.BUOYANCY_DATA);
    }

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();

        this.addEntityFlag(86, EntityFlag.IS_AVOIDING_BLOCK);
        this.addEntityFlag(87, EntityFlag.FACING_TARGET_TO_RANGE_ATTACK);
        this.addEntityFlag(88, EntityFlag.HIDDEN_WHEN_INVISIBLE);
        this.addEntityFlag(89, EntityFlag.IS_IN_UI);
        this.addEntityFlag(90, EntityFlag.STALKING);
        this.addEntityFlag(91, EntityFlag.EMOTING);
        this.addEntityFlag(92, EntityFlag.CELEBRATING);
        this.addEntityFlag(93, EntityFlag.ADMIRING);
        this.addEntityFlag(94, EntityFlag.CELEBRATING_SPECIAL);
    }

    @Override
    protected void registerCommandParams() {
        this.addCommandParam(1, INT);
        this.addCommandParam(2, FLOAT);
        this.addCommandParam(3, VALUE);
        this.addCommandParam(4, WILDCARD_INT);
        this.addCommandParam(5, OPERATOR);
        this.addCommandParam(6, TARGET);
        this.addCommandParam(7, WILDCARD_TARGET);
        this.addCommandParam(14, FILE_PATH);
        this.addCommandParam(29, STRING);
        this.addCommandParam(37, BLOCK_POSITION);
        this.addCommandParam(38, POSITION);
        this.addCommandParam(41, MESSAGE);
        this.addCommandParam(43, TEXT);
        this.addCommandParam(47, JSON);
        this.addCommandParam(54, COMMAND);
    }

    @Override
    public EntityLinkData readEntityLink(ByteBuf buffer) {
        Preconditions.checkNotNull(buffer, "buffer");

        return new EntityLinkData(
                VarInts.readLong(buffer),
                VarInts.readLong(buffer),
                EntityLinkData.Type.byId(buffer.readUnsignedByte()),
                buffer.readBoolean(),
                buffer.readBoolean()
        );
    }

    @Override
    public void writeEntityLink(ByteBuf buffer, EntityLinkData entityLink) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(entityLink, "entityLink");

        VarInts.writeLong(buffer, entityLink.getFrom());
        VarInts.writeLong(buffer, entityLink.getTo());
        buffer.writeByte(entityLink.getType().ordinal());
        buffer.writeBoolean(entityLink.isImmediate());
        buffer.writeBoolean(entityLink.isRiderInitiated());
    }

    @Override
    public ItemInstance readItemInstance(ByteBuf buffer) {
        return ItemInstance.of(VarInts.readInt(buffer), readItem(buffer));
    }

    @Override
    public void writeItemInstance(ByteBuf buffer, ItemInstance itemInstance) {
        VarInts.writeInt(buffer, itemInstance.getNetworkId());
        writeItem(buffer, itemInstance.getItem());
    }

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        this.addLevelEvent(1050, LevelEventType.SOUND_CAMERA);

        this.addLevelEvent(3600, LevelEventType.BLOCK_START_BREAK);
        this.addLevelEvent(3601, LevelEventType.BLOCK_STOP_BREAK);
        this.addLevelEvent(3602, LevelEventType.BLOCK_UPDATE_BREAK);

        this.addLevelEvent(4000, LevelEventType.SET_DATA);

        this.addLevelEvent(9800, LevelEventType.ALL_PLAYERS_SLEEPING);
    }
}
