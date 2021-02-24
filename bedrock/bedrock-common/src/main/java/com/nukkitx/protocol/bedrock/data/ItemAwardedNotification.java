package com.nukkitx.protocol.bedrock.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ItemAwardedNotification {
    public int UnsignedInt;
    public int SignedInt;
    public UUID Uuid;

    public ItemAwardedNotification(int int1, int int2, UUID Uuid1) {
        UnsignedInt = int1;
        SignedInt = int2;
        Uuid = Uuid1;

    }
}
