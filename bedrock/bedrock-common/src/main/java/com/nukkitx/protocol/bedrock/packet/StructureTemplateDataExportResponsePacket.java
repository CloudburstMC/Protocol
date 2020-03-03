package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.nbt.tag.CompoundTag;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class StructureTemplateDataExportResponsePacket extends BedrockPacket {
    private String name;
    private boolean save; // Unsure
    private CompoundTag tag;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.STRUCTURE_TEMPLATE_DATA_EXPORT_RESPONSE;
    }
}
