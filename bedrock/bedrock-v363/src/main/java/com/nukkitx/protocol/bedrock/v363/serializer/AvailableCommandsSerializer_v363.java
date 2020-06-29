package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.CommandData;
import com.nukkitx.protocol.bedrock.data.CommandEnumData;
import com.nukkitx.protocol.bedrock.data.CommandParamData;
import com.nukkitx.protocol.bedrock.data.CommandParamType;
import com.nukkitx.protocol.bedrock.packet.AvailableCommandsPacket;
import com.nukkitx.protocol.bedrock.v340.serializer.AvailableCommandsSerializer_v340;
import com.nukkitx.protocol.bedrock.v361.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import com.nukkitx.protocol.util.Int2ObjectBiMap;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;

import static com.nukkitx.protocol.bedrock.data.CommandParamData.Type.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AvailableCommandsSerializer_v363 extends AvailableCommandsSerializer_v340 {
    public static final AvailableCommandsSerializer_v363 INSTANCE = new AvailableCommandsSerializer_v363();

}