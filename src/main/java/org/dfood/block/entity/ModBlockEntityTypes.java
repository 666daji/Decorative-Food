package org.dfood.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.dfood.ThreedFood;
import org.dfood.block.FoodBlocks;

public class ModBlockEntityTypes {
    public static final BlockEntityType<PotionBlockEntity> POTION_BLOCK_ENTITY = create("potion_block_entity",
            FabricBlockEntityTypeBuilder.create(PotionBlockEntity::new, FoodBlocks.POTION));
    public static final BlockEntityType<SuspiciousStewBlockEntity> SUSPICIOUS_STEW_BLOCK_ENTITY = create("suspicious_stew_block_entity",
            FabricBlockEntityTypeBuilder.create(SuspiciousStewBlockEntity::new, FoodBlocks.SUSPICIOUS_STEW));

    private static <T extends BlockEntity> BlockEntityType<T> create(String id, FabricBlockEntityTypeBuilder<T> builder, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(ThreedFood.MOD_ID, id), builder.build());
    }

    public static void register() {

    }
}
