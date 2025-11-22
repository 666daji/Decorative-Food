package org.dfood.mixin.client;

import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Direction;
import org.dfood.render.HangingItemTransform;
import org.dfood.util.DFoodUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignBlockEntityRenderer.class)
public class SignBlockEntityRendererMixin {
    @Unique
    private static final float STANDING_Z_OFFSET = 0.01f;
    @Unique
    private static final float WALL_Z_OFFSET = 0.015f;

    @Inject(method = "render(Lnet/minecraft/block/entity/SignBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
            at = @At("TAIL"))
    private void dFood$renderHangingItems(SignBlockEntity signBlockEntity, float f, MatrixStack matrixStack,
                                          VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        // 渲染正面物品
        ItemStack frontItem = signBlockEntity.dFood$getItem(true);
        if (!frontItem.isEmpty()) {
            renderSideItem(signBlockEntity, frontItem, true, matrixStack, vertexConsumerProvider, i, j);
        }

        // 渲染背面物品
        ItemStack backItem = signBlockEntity.dFood$getItem(false);
        if (!backItem.isEmpty()) {
            renderSideItem(signBlockEntity, backItem, false, matrixStack, vertexConsumerProvider, i, j);
        }
    }

    /**
     * 渲染一侧的物品
     * @param signBlockEntity 告示牌方块实体
     * @param stack 需要渲染的物品
     * @param front 是否为正面
     */
    @Unique
    private void renderSideItem(SignBlockEntity signBlockEntity, ItemStack stack,
                                boolean front, MatrixStack matrixStack,
                                VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
        // 获取物品对应的方块状态
        BlockState blockState = DFoodUtils.getBlockStateFromItem(stack.getItem());
        if (blockState == null) {
            return;
        }

        // 获取告示牌的方块状态
        BlockState signState = signBlockEntity.getCachedState();
        boolean isWallSign = signState.getBlock() instanceof WallSignBlock;

        // 计算物品朝向
        Direction itemFacing = calculateItemFacing(signState, front);

        matrixStack.push();

        // 根据告示牌类型设置位置
        if (isWallSign) {
            setupWallItemPosition(matrixStack, signState, front, itemFacing);
        } else {
            setupStandingItemPosition(matrixStack, signState, front, itemFacing);
        }

        // 不同的物品应用不同的变换
        HangingItemTransform.applyTransformation(stack, matrixStack, signState, itemFacing);

        // 渲染悬挂物品
        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(
                blockState, matrixStack, vertexConsumerProvider, light, overlay
        );

        matrixStack.pop();
    }

    /**
     * 计算悬挂物品的朝向
     * @param signState 悬挂的告示牌的方块状态
     * @param front 是否为正面
     * @return 物品朝向
     */
    @Unique
    private Direction calculateItemFacing(BlockState signState, boolean front) {
        boolean isWallSign = signState.getBlock() instanceof WallSignBlock;

        if (isWallSign) {
            // 墙上告示牌：物品朝向与告示牌朝向相同
            return signState.get(HorizontalFacingBlock.FACING);
        } else {
            // 地上告示牌：根据旋转计算朝向
            if (signState.contains(Properties.ROTATION)) {
                int rotation = signState.get(Properties.ROTATION);
                float rotationDegrees = -rotation * 22.5f; // 转换为度数（逆时针）

                // 正面物品朝向与告示牌相同，背面物品朝向与告示牌相反
                float itemRotation = front ? rotationDegrees : rotationDegrees + 180f;

                // 标准化角度到0度至360度范围
                itemRotation = (itemRotation % 360 + 360) % 360;

                // 将角度转换为方向
                if (itemRotation >= 45f && itemRotation < 135f) {
                    return Direction.WEST;
                } else if (itemRotation >= 135f && itemRotation < 225f) {
                    return Direction.NORTH;
                } else if (itemRotation >= 225f && itemRotation < 315f) {
                    return Direction.EAST;
                } else {
                    return Direction.SOUTH;
                }
            }
        }

        return Direction.NORTH;
    }

    @Unique
    private void setupStandingItemPosition(MatrixStack matrixStack, BlockState signState, boolean front, Direction itemFacing) {
        // 地上告示牌的位置调整
        matrixStack.translate(0.5, 0.5, 0.5);

        // 应用告示牌的旋转
        if (signState.contains(Properties.ROTATION)) {
            int rotation = signState.get(Properties.ROTATION);
            float rotationDegrees = -rotation * 22.5f;
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotationDegrees));
        }

        // 根据正面/背面设置Z偏移
        float zOffset = front ? -STANDING_Z_OFFSET : STANDING_Z_OFFSET;

        // 物品悬挂在告示牌中心位置
        matrixStack.translate(0, 0.4, zOffset);

        if (!front) {
            // 背面物品需要旋转180度来面向外面
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180f));
        }
    }

    @Unique
    private void setupWallItemPosition(MatrixStack matrixStack, BlockState signState, boolean front, Direction itemFacing) {
        Direction signFacing = signState.get(HorizontalFacingBlock.FACING);

        // 中心位置调整
        float wallOffset = 0.44f;
        switch (signFacing) {
            case NORTH -> matrixStack.translate(0.5, 0.5, 0.5 + wallOffset);
            case SOUTH -> matrixStack.translate(0.5, 0.5, 0.5 - wallOffset);
            case WEST -> matrixStack.translate(0.5 + wallOffset, 0.5, 0.5);
            case EAST -> matrixStack.translate(0.5 - wallOffset, 0.5, 0.5);
        }

        // 应用告示牌的旋转
        float rotationDegrees = switch (signFacing){
            case NORTH -> 180f;
            case EAST -> 90f;
            case SOUTH -> 0f;
            case WEST -> 270f;
            default -> 360f;
        };
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotationDegrees));

        // 根据正面/背面设置Z偏移
        float zOffset = front ? -WALL_Z_OFFSET : WALL_Z_OFFSET;

        // 物品悬挂在告示牌中心位置
        matrixStack.translate(0, 0.085, zOffset);

        if (!front) {
            // 背面物品需要旋转180度来面向外面
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180f));
        }
    }
}