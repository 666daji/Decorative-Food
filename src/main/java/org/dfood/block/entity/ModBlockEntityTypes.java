package org.dfood.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.dfood.ThreedFood;
import org.dfood.block.FoodBlocks;

public class ModBlockEntityTypes {
    public static final BlockEntityType<ComplexFoodBlockEntity> COMPLEX_FOOD = create("complex_food",
            BlockEntityType.Builder.create(ComplexFoodBlockEntity::new));

    public static final BlockEntityType<PotionBlockEntity> POTION_BLOCK_ENTITY = create("potion_block_entity",
            BlockEntityType.Builder.create(PotionBlockEntity::new, FoodBlocks.POTION));
    public static final BlockEntityType<SuspiciousStewBlockEntity> SUSPICIOUS_STEW_BLOCK_ENTITY = create("suspicious_stew_block_entity",
            BlockEntityType.Builder.create(SuspiciousStewBlockEntity::new, FoodBlocks.SUSPICIOUS_STEW));

    public static final BlockEntityType<EnchantedGoldenAppleBlockEntity> ENCHANTED_GOLDEN_APPLE = create("enchanted_golden_apple",
            BlockEntityType.Builder.create(EnchantedGoldenAppleBlockEntity::new, FoodBlocks.ENCHANTED_GOLDEN_APPLE));

    private static <T extends BlockEntity> BlockEntityType<T> create(String id, BlockEntityType.Builder<T> builder) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ThreedFood.MOD_ID, id), builder.build(null));
    }

    public static void register() {}
}
