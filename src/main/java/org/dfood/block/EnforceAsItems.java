package org.dfood.block;

import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class EnforceAsItems {
    public static final FoodBlock.EnforceAsItem POTATO = createAsItem("potato");
    public static final FoodBlock.EnforceAsItem CARROT = createAsItem("carrot");
    public static final FoodBlock.EnforceAsItem SWEET_BERRIES = createAsItem("sweet_berries");
    public static final FoodBlock.EnforceAsItem GLOW_BERRIES = createAsItem("glow_berries");

    private static FoodBlock.EnforceAsItem createAsItem(String item){
        return () -> Registries.ITEM.get(new Identifier(item));
    }
}
