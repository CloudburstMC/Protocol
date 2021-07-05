package org.cloudburstmc.protocol.java.packet.play.clientbound;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.java.JavaPacket;
import org.cloudburstmc.protocol.java.data.MerchantOffer;
import org.cloudburstmc.protocol.java.handler.JavaPlayPacketHandler;
import org.cloudburstmc.protocol.java.packet.type.JavaPacketType;
import org.cloudburstmc.protocol.java.packet.type.JavaPlayPacketType;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class MerchantOffersPacket extends JavaPacket<JavaPlayPacketHandler> {
    private int containerId;
    private final List<MerchantOffer> offers = new ObjectArrayList<>();
    private int villagerLevel;
    private int villagerXp;
    private boolean showProgress;
    private boolean canRestock;

    @Override
    public boolean handle(JavaPlayPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public JavaPacketType getPacketType() {
        return JavaPlayPacketType.MERCHANT_OFFERS;
    }
}
