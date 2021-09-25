package com.nukkitx.protocol.bedrock.data.inventory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeIngredient {
    public static final RecipeIngredient EMPTY = new RecipeIngredient(0, 0, 0);

    int id;
    int auxValue;
    int stackSize;

    public static RecipeIngredient of(int id, int auxValue, int stackSize) {
        if (id == 0) {
            return EMPTY;
        }
        return new RecipeIngredient(id, auxValue, stackSize);
    }
}
