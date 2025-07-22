package org.dfood.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.dfood.shape.FoodShapeHandle;
import org.dfood.tag.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * 所有食物方块的父类，定义了食物方块的基本行为
 */
public class foodBlock extends Block {
    public static final DirectionProperty FACING = Properties.FACING;
    public static final IntProperty NUMBER_OF_FOOD = IntProperty.of("number_of_food", 0, 12);
    private static final FoodShapeHandle foodShapeHandle = new FoodShapeHandle();

    public final int MAX_FOOD;

    public foodBlock(Settings settings, int max_food) {
        super(settings);
        MAX_FOOD = max_food;
        this.setDefaultState(this.getStateManager().getDefaultState()
                .with(FACING, net.minecraft.util.math.Direction.NORTH)
                .with(NUMBER_OF_FOOD, 0));
    }

    @Override
    public String getTranslationKey() {
        return Util.createTranslationKey("item", Registries.BLOCK.getId(this));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return foodShapeHandle.getShape(state);
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing())
                .with(NUMBER_OF_FOOD, 1);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack handStack = player.getStackInHand(hand);
        int currentCount = state.get(NUMBER_OF_FOOD);
        if (world.isClient) {
            if (handStack.getItem() == this.asItem()) {
                if (currentCount < MAX_FOOD) {
                    world.playSound(player, pos, this.soundGroup.getPlaceSound(), SoundCategory.BLOCKS,1,world.getRandom().nextFloat() * 0.1F + 0.9F);
                }
            }
            else if (currentCount > 0) {
                world.playSound(player, pos, this.soundGroup.getBreakSound(), SoundCategory.BLOCKS, 1, world.getRandom().nextFloat() * 0.1F + 0.9F);
            }
            return ActionResult.SUCCESS; // 客户端返回成功并播放声音
        }


        // 手持相同物品 - 尝试添加
        if (handStack.getItem() == this.asItem()) {
            if (currentCount < MAX_FOOD) {
                // 更新方块状态
                BlockState newState = state.with(NUMBER_OF_FOOD, currentCount + 1);
                world.playSound(player, pos, this.soundGroup.getPlaceSound(), SoundCategory.BLOCKS,1,world.getRandom().nextFloat() * 0.1F + 0.9F);
                world.setBlockState(pos, newState, Block.NOTIFY_ALL);

                // 消耗物品（非创造模式）
                if (!player.isCreative()) {
                    handStack.decrement(1);
                    player.setStackInHand(hand, handStack);
                }
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS; // 已达最大数量
        }
        // 其他物品/空手 - 尝试取出
        else if (currentCount > 0) {
            // 更新方块状态
            int newCount = currentCount - 1;
            BlockState newState = state.with(NUMBER_OF_FOOD, newCount);
            world.playSound(player, pos, this.soundGroup.getBreakSound(), SoundCategory.BLOCKS, 1, world.getRandom().nextFloat() * 0.1F + 0.9F);

            if (newCount > 0) {
                world.setBlockState(pos, newState, Block.NOTIFY_ALL);
            } else {
                world.breakBlock(pos, false); // 数量为0时销毁方块
            }

            // 给予玩家物品（非创造模式）
            if (!player.isCreative()) {
                ItemStack foodItem = new ItemStack(this.asItem(), 1);
                if (!player.giveItemStack(foodItem)) {
                    player.dropItem(foodItem, false); // 背包满时掉落
                }
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public BlockState getStateForNeighborUpdate(
            BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos
    ) {
        return !state.canPlaceAt(world, pos)
                ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos downPos = pos.down();
        return !world.getBlockState(downPos).isIn(ModTags.FOOD_PLACE);
    }

    /**
     *硬编码掉落物
     */
    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        int foodCount = state.get(NUMBER_OF_FOOD);

        // 当数量为0时，不返回任何物品
        if (foodCount <= 0) {
            return Collections.emptyList();
        }

        // 创建包含正确数量的物品堆栈
        return Collections.singletonList(new ItemStack(this.asItem(), foodCount));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(NUMBER_OF_FOOD);
    }
}