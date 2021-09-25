package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class PurchaseReceiptPacket extends BedrockPacket {
    private final List<String> receipts = new ObjectArrayList<>();

    public PurchaseReceiptPacket addReceipt(String receipt) {
        this.receipts.add(receipt);
        return this;
    }

    public PurchaseReceiptPacket addReceipts(String... receipts) {
        this.receipts.addAll(Arrays.asList(receipts));
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PURCHASE_RECEIPT;
    }
}
