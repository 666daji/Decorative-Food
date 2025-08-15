package org.dfood.block.entity;

import com.mojang.datafixers.types.Type;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.dfood.ThreedFood;
import org.dfood.block.foodBlocks;

public class ModBlockEntityTypes {
    public static final BlockEntityType<PotionBlockEntity> POTION_BLOCK_ENTITY = create("potion_block_entity", FabricBlockEntityTypeBuilder.create(PotionBlockEntity::new, foodBlocks.POTION.getBlock()));

    private static <T extends BlockEntity> BlockEntityType<T> create(String id, FabricBlockEntityTypeBuilder<T> builder, Block... blocks) {
        Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(ThreedFood.MOD_ID, id), builder.build());
    }

    public static void register() {

    }
}
