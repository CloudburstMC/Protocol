package v575;

import com.nukkitx.protocol.bedrock.data.Ability;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.v567.BedrockPacketHelper_v567patch;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v575 extends BedrockPacketHelper_v567patch {

    public static final BedrockPacketHelper_v575 INSTANCE = new BedrockPacketHelper_v575();

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.soundEvents.remove(458); // Remove old undefined value
        this.soundEvents.put(458, SoundEvent.INSERT);
        this.soundEvents.put(459, SoundEvent.PICKUP);
        this.soundEvents.put(460, SoundEvent.INSERT_ENCHANTED);
        this.soundEvents.put(461, SoundEvent.PICKUP_ENCHANTED);
        this.soundEvents.put(462, SoundEvent.UNDEFINED);
    }

    @Override
    protected void registerAbilities() {
        super.registerAbilities();
        this.playerAbilities.put(18, Ability.PRIVILEGED_BUILDER);
    }

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();
        this.addEntityFlag(110, EntityFlag.SCENTING);
        this.addEntityFlag(111, EntityFlag.RISING);
        this.addEntityFlag(112, EntityFlag.FEELING_HAPPY);
        this.addEntityFlag(113, EntityFlag.SEARCHING);
    }
}
