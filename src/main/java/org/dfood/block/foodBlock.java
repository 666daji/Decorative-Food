package org.dfood.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
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
    @Nullable private CROPS CROP;
    private onUseHook onUseHook = (state, world, pos, player, hand, hit) -> ActionResult.PASS;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty NUMBER_OF_FOOD = IntProperty.of("number_of_food", 1, 12);
    private static final FoodShapeHandle foodShapeHandle = FoodShapeHandle.getInstance();

    public final int MAX_FOOD;

    public onUseHook getOnUseHook() {
        return onUseHook;
    }

    public void setOnUseHook(onUseHook onUseHook) {
        this.onUseHook = onUseHook;
    }

    public enum CROPS{
        POTATO,
        CARROT;
    }

    public foodBlock(Settings settings, int max_food) {
        super(settings);
        MAX_FOOD = max_food;
        this.setDefaultState(this.getStateManager().getDefaultState()
                .with(FACING, net.minecraft.util.math.Direction.NORTH)
                .with(NUMBER_OF_FOOD, 1));
    }

    public foodBlock(Settings settings, int max_food, @Nullable CROPS crop) {
        this(settings, max_food);
        this.CROP = crop;
    }

    @Override
    public String getTranslationKey() {
        return Util.createTranslationKey("item", Registries.BLOCK.getId(this));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return foodShapeHandle.getShape(state, NUMBER_OF_FOOD);
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
        // 调用自定义钩子
        ActionResult HookResult = this.onUseHook.interact(state, world, pos, player, hand, hit);
        if (HookResult != ActionResult.PASS) {
            return HookResult;
        }
        ItemStack handStack = player.getStackInHand(hand);
        int currentCount = state.get(NUMBER_OF_FOOD);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        boolean isSameItem = isSame(handStack, blockEntity);

        // 客户端只负责播放声音和返回结果
        if (world.isClient) {
            if (isSameItem && currentCount < MAX_FOOD) {
                playSound(this.soundGroup.getPlaceSound(), world, pos, player);
            } else if (!isSameItem && currentCount > 0) {
                playSound(this.soundGroup.getBreakSound(), world, pos, player);
            }
            return ActionResult.SUCCESS; // 客户端返回成功并播放声音
        }

        // 手持相同物品 - 尝试添加
        if (isSameItem) {
            if (currentCount < MAX_FOOD) {
                // 更新方块状态
                BlockState newState = state.with(NUMBER_OF_FOOD, currentCount + 1);
                world.setBlockState(pos, newState, Block.NOTIFY_ALL);
                playSound(this.soundGroup.getPlaceSound(), world, pos, player);

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
        if (currentCount > 0) {
            int newCount = currentCount - 1;
            BlockState newState = state.with(NUMBER_OF_FOOD, newCount);

            if (newCount > 0) {
                world.setBlockState(pos, newState, Block.NOTIFY_ALL);
            } else {
                world.breakBlock(pos, false); // 数量为0时销毁方块
            }
            playSound(this.soundGroup.getBreakSound(), world, pos, player);

            // 给予玩家物品（非创造模式）
            if (!player.isCreative()) {
                ItemStack foodItem = createStack(1, blockEntity);
                if (!player.giveItemStack(foodItem)) {
                    player.dropItem(foodItem, false); // 背包满时掉落
                }
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    /**
     * 检查手持物品是否与方块匹配
     * @param stack 手持物品堆栈
     * @param blockEntity 对应的方块实体
     * @return 匹配放回true,否则返回false
     */
    public boolean isSame(ItemStack stack, BlockEntity blockEntity) {
        return stack.getItem() == this.asItem();
    }

    /**
     * 根据数量创建对应的物品堆栈
     * @param count 数量
     * @param blockEntity 对应的方块实体
     * @return 创建成功的物品堆栈
     */
    public ItemStack createStack(int count, @Nullable BlockEntity blockEntity) {
        if (count <= 0 || count > MAX_FOOD) {
            throw new IllegalArgumentException("Count must be between 1 and " + MAX_FOOD);
        }
        return new ItemStack(this.asItem(), count);
    }

    public void playSound(SoundEvent event, World world, BlockPos pos, PlayerEntity player) {
        world.playSound(player, pos, this.soundGroup.getPlaceSound(), SoundCategory.BLOCKS, 1, world.getRandom().nextFloat() * 0.1F + 0.9F);
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
    public Item asItem() {
        if (this.CROP == CROPS.POTATO) {
            return Items.POTATO;
        } else if (this.CROP == CROPS.CARROT) {
            return Items.CARROT;
        }
        return super.asItem();
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(NUMBER_OF_FOOD);
    }

    @FunctionalInterface
    public interface onUseHook{
        ActionResult interact(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit);
    }
}