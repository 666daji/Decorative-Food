package org.dfood.render;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.dfood.block.FoodBlock;
import org.dfood.block.FoodBlocks;
import org.dfood.block.entity.EnchantedGoldenAppleBlockEntity;
import org.dfood.util.DFoodUtils;

public class EnchantedGoldenAppleBlockEntityRenderer implements BlockEntityRenderer<EnchantedGoldenAppleBlockEntity> {
    protected final BlockRenderManager blockRender;

    public EnchantedGoldenAppleBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        blockRender = ctx.getRenderManager();
    }

    @Override
    public void render(EnchantedGoldenAppleBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.getCachedState().getBlock() instanceof FoodBlock foodBlock) {
            Direction facing = entity.getCachedState().get(FoodBlock.FACING);
            int count = DFoodUtils.getFoodBlockCount(entity.getCachedState());
            BlockState state = FoodBlocks.GOLDEN_APPLE.getDefaultState()
                    .with(FoodBlock.FACING, facing)
                    .with(foodBlock.NUMBER_OF_FOOD, count);

            blockRender.renderBlock(state, entity.getPos(), entity.getWorld(), matrices,
                    vertexConsumers.getBuffer(RenderLayer.getCutout()), true, Random.create());

            blockRender.renderBlock(state, entity.getPos(), entity.getWorld(), matrices,
                    vertexConsumers.getBuffer(RenderLayer.getGlint()), true, Random.create());
        }
    }
}
