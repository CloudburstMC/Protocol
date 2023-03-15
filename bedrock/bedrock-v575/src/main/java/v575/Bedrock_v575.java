package v575;


import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.packet.CameraInstructionPacket;
import com.nukkitx.protocol.bedrock.packet.CameraPresetsPacket;
import com.nukkitx.protocol.bedrock.packet.UnlockedRecipesPacket;
import com.nukkitx.protocol.bedrock.v567.Bedrock_v567patch;
import lombok.experimental.UtilityClass;
import v575.serializer.CameraInstructionSerializer_v575;
import v575.serializer.CameraPresetsSerializer_v575;
import v575.serializer.UnlockedRecipesSerializer_v575;

@UtilityClass
public class Bedrock_v575 {

    public static final BedrockPacketCodec V575_CODEC = Bedrock_v567patch.BEDROCK_V567PATCH.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(575)
            .minecraftVersion("1.19.70")
            .helper(BedrockPacketHelper_v575.INSTANCE)
            .registerPacket(CameraPresetsPacket.class, new CameraPresetsSerializer_v575(), 198)
            .registerPacket(UnlockedRecipesPacket.class, new UnlockedRecipesSerializer_v575(), 199)
            .registerPacket(CameraInstructionPacket.class, new CameraInstructionSerializer_v575(), 300)
            .build();
}
