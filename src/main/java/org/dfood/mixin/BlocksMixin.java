package org.dfood.mixin;

import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.dfood.block.foodBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Blocks.class)
public class BlocksMixin {

    @Inject(method = "<clinit>", at = @At("HEAD"), remap = false)
    private static void registerFoodBlocks(CallbackInfo ci) {
        // 零食类
        register("cookie", foodBlocks.COOKIE);
        register("apple", foodBlocks.APPLE);
        register("melon_slice", foodBlocks.MELON_SLICE);
        register("bread", foodBlocks.BREAD);

        // 蔬菜类
        register("beetroot", foodBlocks.BEETROOT);
        register("potato", foodBlocks.POTATO);
        register("baked_potato", foodBlocks.BAKED_POTATO);
        register("carrot", foodBlocks.CARROT);
        register("sweet_berries", foodBlocks.SWEET_BERRIES);
        register("glow_berries", foodBlocks.GLOW_BERRIES);

        // 生熟肉类
        register("chicken", foodBlocks.CHICKEN);
        register("cooked_chicken", foodBlocks.COOKED_CHICKEN);
        register("beef", foodBlocks.BEEF);
        register("cooked_beef", foodBlocks.COOKED_BEEF);
        register("mutton", foodBlocks.MUTTON);
        register("cooked_mutton", foodBlocks.COOKED_MUTTON);
        register("porkchop", foodBlocks.PORKCHOP);
        register("cooked_porkchop", foodBlocks.COOKED_PORKCHOP);
        register("rabbit", foodBlocks.RABBIT);
        register("cooked_rabbit", foodBlocks.COOKED_RABBIT);

        // 鱼类
        register("cod", foodBlocks.COD);
        register("cooked_cod", foodBlocks.COOKED_COD);
        register("salmon", foodBlocks.SALMON);
        register("cooked_salmon", foodBlocks.COOKED_SALMON);
        register("pufferfish", foodBlocks.PUFFERFISH);

        // 炖菜类
        register("rabbit_stew", foodBlocks.RABBIT_STEW);
        register("mushroom_stew", foodBlocks.MUSHROOM_STEW);
        register("beetroot_soup", foodBlocks.BEETROOT_SOUP);

        // 其他
        register("pumpkin_pie", foodBlocks.PUMPKIN_PIE);
        register("chorus_fruit", foodBlocks.CHORUS_FRUIT);
        register("egg", foodBlocks.EGG);
    }

    @Unique
    private static Block register(String id, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(id), block);
    }
}
