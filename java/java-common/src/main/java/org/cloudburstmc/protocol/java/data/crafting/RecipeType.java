package org.cloudburstmc.protocol.java.data.crafting;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.data.inventory.ItemStack;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public abstract class RecipeType<T> {
    public static final RecipeType<ShapelessRecipe> SHAPELESS = new RecipeType<ShapelessRecipe>(Key.key("crafting_shapeless")) {

        @Override
        public ShapelessRecipe read(JavaPacketHelper helper, ByteBuf buffer) {
            Key identifier = helper.readKey(buffer);
            String group = helper.readString(buffer);
            RecipeIngredient[] ingredients = helper.readArray(buffer, new RecipeIngredient[0], helper::readRecipeIngredient);
            ItemStack result = helper.readItemStack(buffer);
            return new ShapelessRecipe(this, identifier, group, result, ingredients);
        }

        @Override
        public void write(JavaPacketHelper helper, ByteBuf buffer, ShapelessRecipe recipe) {
            helper.writeKey(buffer, recipe.getIdentifier());
            helper.writeString(buffer, recipe.getGroup());
            helper.writeArray(buffer, recipe.getIngredients(), helper::writeRecipeIngredient);
            helper.writeItemStack(buffer, recipe.getResult());
        }
    };

    public static final RecipeType<ShapedRecipe> SHAPED = new RecipeType<ShapedRecipe>(Key.key("crafting_shaped")) {

        @Override
        public ShapedRecipe read(JavaPacketHelper helper, ByteBuf buffer) {
            Key identifier = helper.readKey(buffer);
            int width = helper.readVarInt(buffer);
            int height = helper.readVarInt(buffer);
            String group = helper.readString(buffer);
            RecipeIngredient[] ingredients = helper.readArray(buffer, new RecipeIngredient[0], helper::readRecipeIngredient);
            ItemStack result = helper.readItemStack(buffer);

            return new ShapedRecipe(this, identifier, group, width, height, ingredients, result);
        }

        @Override
        public void write(JavaPacketHelper helper, ByteBuf buffer, ShapedRecipe recipe) {
            helper.writeKey(buffer, recipe.getIdentifier());
            helper.writeVarInt(buffer, recipe.getWidth());
            helper.writeVarInt(buffer, recipe.getHeight());
            helper.writeString(buffer, recipe.getGroup());
            helper.writeArray(buffer, recipe.getIngredients(), helper::writeRecipeIngredient);
            helper.writeItemStack(buffer, recipe.getResult());
        }
    };

    private static final RecipeType<SpecialRecipe> SPECIAL = new RecipeType<SpecialRecipe>(Key.key("crafting_special_armordye")) {

        @Override
        public SpecialRecipe read(JavaPacketHelper helper, ByteBuf buffer) {
            return new SpecialRecipe(this, helper.readKey(buffer));
        }

        @Override
        public void write(JavaPacketHelper helper, ByteBuf buffer, SpecialRecipe recipe) {
            helper.writeKey(buffer, recipe.getIdentifier());
        }
    };

    public static final RecipeType<SpecialRecipe> SPECIAL_ARMORDYE = new WrappedRecipeType<>(Key.key("crafting_special_armordye"), SPECIAL);

    public static final RecipeType<SpecialRecipe> SPECIAL_BOOKCLONING = new WrappedRecipeType<>(Key.key("crafting_special_bookcloning"), SPECIAL);

    public static final RecipeType<SpecialRecipe> SPECIAL_MAPCLONING = new WrappedRecipeType<>(Key.key("crafting_special_mapcloning"), SPECIAL);

    public static final RecipeType<SpecialRecipe> SPECIAL_MAPEXTENDING = new WrappedRecipeType<>(Key.key("crafting_special_mapextending"), SPECIAL);

    public static final RecipeType<SpecialRecipe> SPECIAL_FIREWORK_ROCKET = new WrappedRecipeType<>(Key.key("crafting_special_firework_rocket"), SPECIAL);

    public static final RecipeType<SpecialRecipe> SPECIAL_FIREWORK_STAR = new WrappedRecipeType<>(Key.key("crafting_special_firework_star"), SPECIAL);

    public static final RecipeType<SpecialRecipe> SPECIAL_FIREWORK_STAR_FADE = new WrappedRecipeType<>(Key.key("crafting_special_firework_star_fade"), SPECIAL);

    public static final RecipeType<SpecialRecipe> SPECIAL_REPAIRITEM = new WrappedRecipeType<>(Key.key("crafting_special_repairitem"), SPECIAL);

    public static final RecipeType<SpecialRecipe> SPECIAL_TIPPEDARROW = new WrappedRecipeType<>(Key.key("crafting_special_tippedarrow"), SPECIAL);

    public static final RecipeType<SpecialRecipe> SPECIAL_BANNERDUPLICATE = new WrappedRecipeType<>(Key.key("crafting_special_bannerduplicate"), SPECIAL);

    public static final RecipeType<SpecialRecipe> SPECIAL_BANNERADDPATTERN = new WrappedRecipeType<>(Key.key("crafting_special_banneraddpattern"), SPECIAL);

    public static final RecipeType<SpecialRecipe> SPECIAL_SHIELDDECORATION = new WrappedRecipeType<>(Key.key("crafting_special_shielddecoration"), SPECIAL);

    public static final RecipeType<SpecialRecipe> SPECIAL_SHULKERBOXCOLORING = new WrappedRecipeType<>(Key.key("crafting_special_shulkerboxcoloring"), SPECIAL);

    public static final RecipeType<SpecialRecipe> SPECIAL_SUSPICIOUSSTEW = new WrappedRecipeType<>(Key.key("crafting_special_suspiciousstew"), SPECIAL);

    private static final RecipeType<CookingRecipe> COOKING = new RecipeType<CookingRecipe>(Key.key("cooking")) {
        @Override
        public CookingRecipe read(JavaPacketHelper helper, ByteBuf buffer) {
            Key identifier = helper.readKey(buffer);
            String group = helper.readString(buffer);
            RecipeIngredient ingredient = helper.readRecipeIngredient(buffer);
            ItemStack result = helper.readItemStack(buffer);
            float experience = buffer.readFloat();
            int cookingTime = helper.readVarInt(buffer);

            return new CookingRecipe(this, identifier, group, ingredient, result, experience, cookingTime);
        }

        @Override
        public void write(JavaPacketHelper helper, ByteBuf buffer, CookingRecipe recipe) {
            helper.writeKey(buffer, recipe.getIdentifier());
            helper.writeString(buffer, recipe.getGroup());
            helper.writeRecipeIngredient(buffer, recipe.getIngredient());
            helper.writeItemStack(buffer, recipe.getResult());
            buffer.writeFloat(recipe.getExperience());
            helper.writeVarInt(buffer, recipe.getCookingTime());
        }
    };

    public static final RecipeType<CookingRecipe> SMELTING = new WrappedRecipeType<>(Key.key("smelting"), COOKING);

    public static final RecipeType<CookingRecipe> BLASTING = new WrappedRecipeType<>(Key.key("blasting"), COOKING);

    public static final RecipeType<CookingRecipe> SMOKING = new WrappedRecipeType<>(Key.key("smoking"), COOKING);

    public static final RecipeType<CookingRecipe> CAMPFIRE_COOKING = new WrappedRecipeType<>(Key.key("campfire_cooking"), COOKING);

    public static final RecipeType<StonecuttingRecipe> STONECUTTING = new RecipeType<StonecuttingRecipe>(Key.key("stonecutting")) {

        @Override
        public StonecuttingRecipe read(JavaPacketHelper helper, ByteBuf buffer) {
            Key identifier = helper.readKey(buffer);
            String group = helper.readString(buffer);
            RecipeIngredient ingredient = helper.readRecipeIngredient(buffer);
            ItemStack result = helper.readItemStack(buffer);

            return new StonecuttingRecipe(this, identifier, group, ingredient, result);
        }

        @Override
        public void write(JavaPacketHelper helper, ByteBuf buffer, StonecuttingRecipe recipe) {
            helper.writeKey(buffer, recipe.getIdentifier());
            helper.writeString(buffer, recipe.getGroup());
            helper.writeRecipeIngredient(buffer, recipe.getIngredient());
            helper.writeItemStack(buffer, recipe.getResult());
        }
    };

    public static final RecipeType<SmithingRecipe> SMITHING = new RecipeType<SmithingRecipe>(Key.key("smithing")) {

        @Override
        public SmithingRecipe read(JavaPacketHelper helper, ByteBuf buffer) {
            Key identifier = helper.readKey(buffer);
            RecipeIngredient base = helper.readRecipeIngredient(buffer);
            RecipeIngredient addition = helper.readRecipeIngredient(buffer);
            ItemStack result = helper.readItemStack(buffer);

            return new SmithingRecipe(this, identifier, base, addition, result);
        }

        @Override
        public void write(JavaPacketHelper helper, ByteBuf buffer, SmithingRecipe recipe) {
            helper.writeKey(buffer, recipe.getIdentifier());
            helper.writeRecipeIngredient(buffer, recipe.getBase());
            helper.writeRecipeIngredient(buffer, recipe.getAddition());
            helper.writeItemStack(buffer, recipe.getResult());
        }
    };

    private final Key identifier;

    public abstract T read(JavaPacketHelper helper, ByteBuf buffer);

    public abstract void write(JavaPacketHelper helper, ByteBuf buffer, T recipe);

    private static final class WrappedRecipeType<T> extends RecipeType<T> {
        private final RecipeType<T> type;

        public WrappedRecipeType(Key identifier, RecipeType<T> type) {
            super(identifier);

            this.type = type;
        }

        @Override
        public T read(JavaPacketHelper helper, ByteBuf buffer) {
            return this.type.read(helper, buffer);
        }

        @Override
        public void write(JavaPacketHelper helper, ByteBuf buffer, T recipe) {
            this.type.write(helper, buffer, recipe);
        }
    }
}
