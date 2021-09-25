package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class CraftingEventPacket extends BedrockPacket {
    private final List<ItemData> inputs = new ObjectArrayList<>();
    private final List<ItemData> outputs = new ObjectArrayList<>();
    private byte containerId;
    private CraftingType type;
    private UUID uuid;

    public CraftingEventPacket addInput(ItemData input) {
        this.inputs.add(input);
        return this;
    }

    public CraftingEventPacket addInput(ItemData... inputs) {
        this.inputs.addAll(Arrays.asList(inputs));
        return this;
    }

    public CraftingEventPacket addOutput(ItemData output) {
        this.outputs.add(output);
        return this;
    }

    public CraftingEventPacket addOutputs(ItemData... outputs) {
        this.outputs.addAll(Arrays.asList(outputs));
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CRAFTING_EVENT;
    }
}
