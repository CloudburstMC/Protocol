package com.nukkitx.protocol.bedrock.data.command;

import lombok.Value;

/**
 * CommandEnumConstraintData is sent in the AvailableCommandsPacket to limit what values of an enum may be used
 * taking in account things such as whether cheats are enabled.
 */
@Value
public class CommandEnumConstraintData {
    // The option in an enum that the constraints should be applied to.
    String option;

    // The name of the enum of which the option above should be constrained.
    String name;

    // List of constraints that should be applied to the enum option.
    CommandEnumConstraintType[] constraints;
}
