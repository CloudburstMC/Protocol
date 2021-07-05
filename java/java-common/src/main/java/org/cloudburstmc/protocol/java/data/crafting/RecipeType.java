package org.cloudburstmc.protocol.java.data.crafting;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import net.kyori.adventure.key.Key;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.data.inventory.ItemStack;

@EqualsAndHashCode
public abstract class RecipeType<T> {
    public static final RecipeType<ShapelessRecipe> SHAPELESS = new RecipeType<ShapelessRecipe>() {

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

    public static final RecipeType<ShapedRecipe> SHAPED = new RecipeType<ShapedRecipe>() {

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

    private static final RecipeType<Recipe> SPECIAL = new RecipeType<Recipe>() {

        @Override
        public Recipe read(JavaPacketHelper helper, ByteBuf buffer) {
            return new Recipe(this, helper.readKey(buffer));
        }

        @Override
        public void write(JavaPacketHelper helper, ByteBuf buffer, Recipe recipe) {
            helper.writeKey(buffer, recipe.getIdentifier());
        }
    };

    public static final RecipeType<Recipe> SPECIAL_ARMORDYE = new WrappedRecipeType<>(SPECIAL);

    public static final RecipeType<Recipe> SPECIAL_BOOKCLONING = new WrappedRecipeType<>(SPECIAL);

    public static final RecipeType<Recipe> SPECIAL_MAPCLONING = new WrappedRecipeType<>(SPECIAL);

    public static final RecipeType<Recipe> SPECIAL_MAPEXTENDING = new WrappedRecipeType<>(SPECIAL);

    public static final RecipeType<Recipe> SPECIAL_FIREWORK_ROCKET = new WrappedRecipeType<>(SPECIAL);

    public static final RecipeType<Recipe> SPECIAL_FIREWORK_STAR = new WrappedRecipeType<>(SPECIAL);

    public static final RecipeType<Recipe> SPECIAL_FIREWORK_STAR_FADE = new WrappedRecipeType<>(SPECIAL);

    public static final RecipeType<Recipe> SPECIAL_REPAIRITEM = new WrappedRecipeType<>(SPECIAL);

    public static final RecipeType<Recipe> SPECIAL_TIPPEDARROW = new WrappedRecipeType<>(SPECIAL);

    public static final RecipeType<Recipe> SPECIAL_BANNERDUPLICATE = new WrappedRecipeType<>(SPECIAL);

    public static final RecipeType<Recipe> SPECIAL_BANNERADDPATTERN = new WrappedRecipeType<>(SPECIAL);

    public static final RecipeType<Recipe> SPECIAL_SHIELDDECORATION = new WrappedRecipeType<>(SPECIAL);

    public static final RecipeType<Recipe> SPECIAL_SHULKERBOXCOLORING = new WrappedRecipeType<>(SPECIAL);

    public static final RecipeType<Recipe> SPECIAL_SUSPICIOUSSTEW = new WrappedRecipeType<>(SPECIAL);

    private static final RecipeType<CookingRecipe> COOKING = new RecipeType<CookingRecipe>() {
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

    public static final RecipeType<CookingRecipe> SMELTING = new WrappedRecipeType<>(COOKING);

    public static final RecipeType<CookingRecipe> BLASTING = new WrappedRecipeType<>(COOKING);

    public static final RecipeType<CookingRecipe> SMOKING = new WrappedRecipeType<>(COOKING);

    public static final RecipeType<CookingRecipe> CAMPFIRE_COOKING = new WrappedRecipeType<>(COOKING);

    public static final RecipeType<StonecuttingRecipe> STONECUTTING = new RecipeType<StonecuttingRecipe>() {

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

    public static final RecipeType<SmithingRecipe> SMITHING = new RecipeType<SmithingRecipe>() {

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

    public abstract T read(JavaPacketHelper helper, ByteBuf buffer);

    public abstract void write(JavaPacketHelper helper, ByteBuf buffer, T recipe);

    @AllArgsConstructor
    private static final class WrappedRecipeType<T> extends RecipeType<T> {
        private final RecipeType<T> type;

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
