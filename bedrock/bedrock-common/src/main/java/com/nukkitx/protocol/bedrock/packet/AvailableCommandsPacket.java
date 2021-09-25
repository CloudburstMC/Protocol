package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.command.CommandData;
import com.nukkitx.protocol.bedrock.data.command.CommandEnumConstraintData;
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
public class AvailableCommandsPacket extends BedrockPacket {
    private final List<CommandData> commands = new ObjectArrayList<>();
    private final List<CommandEnumConstraintData> constraints = new ObjectArrayList<>();

    public AvailableCommandsPacket addCommand(CommandData command) {
        this.commands.add(command);
        return this;
    }

    public AvailableCommandsPacket addCommands(CommandData... commands) {
        this.commands.addAll(Arrays.asList(commands));
        return this;
    }

    public AvailableCommandsPacket addConstraint(CommandEnumConstraintData constraint) {
        this.constraints.add(constraint);
        return this;
    }

    public AvailableCommandsPacket addConstraints(CommandEnumConstraintData... constraints) {
        this.constraints.addAll(Arrays.asList(constraints));
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.AVAILABLE_COMMANDS;
    }
}
