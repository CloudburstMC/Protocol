package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public abstract class StackRequestActionData {
    /**
     * Used for the server to determine which strings should be filtered. Used in anvils to verify the string.
     * @since v422
     */
    @Getter @Setter
    @NonNull String[] filterStrings = new String[0];

    public abstract StackRequestActionType getType();
}
