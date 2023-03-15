package v575;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.Ability;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.inventory.descriptor.*;
import com.nukkitx.protocol.bedrock.v567.BedrockPacketHelper_v567patch;
import io.netty.buffer.ByteBuf;
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


    @Override
    protected ItemDescriptorWithCount readIngredient(ByteBuf buffer, BedrockPacketHelper helper) {
        ItemDescriptorType type = TYPES[buffer.readUnsignedByte()];

        ItemDescriptor descriptor;
        switch (type) {
            case DEFAULT:
                int itemId = buffer.readShortLE();
                int auxValue = itemId != 0 ? buffer.readShortLE() : 0;
                descriptor = new DefaultDescriptor(itemId, auxValue);
                break;
            case MOLANG:
                descriptor = new MolangDescriptor(helper.readString(buffer), buffer.readUnsignedByte());
                break;
            case ITEM_TAG:
                descriptor = new ItemTagDescriptor(helper.readString(buffer));
                break;
            case DEFERRED:
                descriptor = new DeferredDescriptor(helper.readString(buffer), buffer.readUnsignedShortLE());
                break;
            case COMPLEX_ALIAS:
                descriptor = new ComplexAliasDescriptor(this.readString(buffer));
                break;
            default:
                descriptor = InvalidDescriptor.INSTANCE;
                break;
        }

        return new ItemDescriptorWithCount(descriptor, VarInts.readInt(buffer));
    }

    @Override
    protected void writeIngredient(ByteBuf buffer, ItemDescriptorWithCount ingredient) {
        ItemDescriptorType type = ingredient.getDescriptor().getType();
        buffer.writeByte(type.ordinal());

        switch (type) {
            case DEFAULT:
                DefaultDescriptor defaultDescriptor = (DefaultDescriptor) ingredient.getDescriptor();
                buffer.writeShortLE(defaultDescriptor.getItemId());
                if (defaultDescriptor.getItemId() != 0) {
                    buffer.writeShortLE(defaultDescriptor.getAuxValue());
                }
                break;
            case MOLANG:
                MolangDescriptor molangDescriptor = (MolangDescriptor) ingredient.getDescriptor();
                this.writeString(buffer, molangDescriptor.getTagExpression());
                buffer.writeByte(molangDescriptor.getMolangVersion());
                break;
            case ITEM_TAG:
                ItemTagDescriptor tagDescriptor = (ItemTagDescriptor) ingredient.getDescriptor();
                this.writeString(buffer, tagDescriptor.getItemTag());
                break;
            case DEFERRED:
                DeferredDescriptor deferredDescriptor = (DeferredDescriptor) ingredient.getDescriptor();
                this.writeString(buffer, deferredDescriptor.getFullName());
                buffer.writeShortLE(deferredDescriptor.getAuxValue());
                break;
            case COMPLEX_ALIAS:
                this.writeString(buffer, ((ComplexAliasDescriptor) ingredient.getDescriptor()).getName());
        }
        VarInts.writeInt(buffer, ingredient.getCount());
    }
}
