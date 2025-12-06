package org.dfood.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

/**
 * 表示该模组独有的方块物品，虽然这样做会有很大一部分重复代码，但是可以很好地与其他模组兼容。
 * <p>示例:{@linkplain ModPotionItem}</p>
 */
public interface HaveBlock {
    Block getBlock();

    /**
     * 将该方块物品添加到指定的映射中。
     * @param map 目标映射
     * @param item 该方块物品对应的物品实例
     * @apiNote 该方法用于代替BlockItem.appendBlocks，以确保方块物品能够正确注册到模组的方块物品映射中。
     */
    default void appendBlocks(Map<Block, Item> map, Item item) {
        map.put(this.getBlock(), item);
    }

    default ActionResult place(ItemPlacementContext context) {
        if (!this.getBlock().isEnabled(context.getWorld().getEnabledFeatures())) {
            return ActionResult.FAIL;
        } else if (!context.canPlace()) {
            return ActionResult.FAIL;
        } else {
            ItemPlacementContext itemPlacementContext = this.getPlacementContext(context);
            if (itemPlacementContext == null) {
                return ActionResult.FAIL;
            } else {
                BlockState blockState = this.getPlacementState(itemPlacementContext);
                if (blockState == null) {
                    return ActionResult.FAIL;
                } else if (!this.place(itemPlacementContext, blockState)) {
                    return ActionResult.FAIL;
                } else {
                    BlockPos blockPos = itemPlacementContext.getBlockPos();
                    World world = itemPlacementContext.getWorld();
                    PlayerEntity playerEntity = itemPlacementContext.getPlayer();
                    ItemStack itemStack = itemPlacementContext.getStack();
                    BlockState blockState2 = world.getBlockState(blockPos);
                    if (blockState2.isOf(blockState.getBlock())) {
                        blockState2 = this.placeFromNbt(blockPos, world, itemStack, blockState2);
                        this.postPlacement(blockPos, world, playerEntity, itemStack, blockState2);
                        blockState2.getBlock().onPlaced(world, blockPos, blockState2, playerEntity, itemStack);
                        if (playerEntity instanceof ServerPlayerEntity) {
                            Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
                        }
                    }

                    BlockSoundGroup blockSoundGroup = blockState2.getSoundGroup();
                    world.playSound(
                            playerEntity,
                            blockPos,
                            this.getPlaceSound(blockState2),
                            SoundCategory.BLOCKS,
                            (blockSoundGroup.getVolume() + 1.0F) / 2.0F,
                            blockSoundGroup.getPitch() * 0.8F
                    );
                    world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Emitter.of(playerEntity, blockState2));
                    if (playerEntity == null || !playerEntity.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                    }

                    return ActionResult.success(world.isClient);
                }
            }
        }
    }

    @Nullable
    default BlockState getPlacementState(ItemPlacementContext context) {
        BlockState blockState = this.getBlock().getPlacementState(context);
        return blockState != null && this.canPlace(context, blockState) ? blockState : null;
    }

    default SoundEvent getPlaceSound(BlockState state) {
        return state.getSoundGroup().getPlaceSound();
    }

    @Nullable
    default ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        return context;
    }

    default boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        return writeNbtToBlockEntity(world, player, pos, stack);
    }

    private BlockState placeFromNbt(BlockPos pos, World world, ItemStack stack, BlockState state) {
        BlockState blockState = state;
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound != null) {
            NbtCompound nbtCompound2 = nbtCompound.getCompound("BlockStateTag");
            StateManager<Block, BlockState> stateManager = state.getBlock().getStateManager();

            for (String string : nbtCompound2.getKeys()) {
                Property<?> property = stateManager.getProperty(string);
                if (property != null) {
                    String string2 = Objects.requireNonNull(nbtCompound2.get(string)).asString();
                    blockState = with(blockState, property, string2);
                }
            }
        }

        if (blockState != state) {
            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
        }

        return blockState;
    }

    private static <T extends Comparable<T>> BlockState with(BlockState state, Property<T> property, String name) {
        return property.parse(name).map(value -> state.with(property, value)).orElse(state);
    }

    default boolean canPlace(ItemPlacementContext context, BlockState state) {
        PlayerEntity playerEntity = context.getPlayer();
        ShapeContext shapeContext = playerEntity == null ? ShapeContext.absent() : ShapeContext.of(playerEntity);
        return (!this.checkStatePlacement() || state.canPlaceAt(context.getWorld(), context.getBlockPos()))
                && context.getWorld().canPlace(state, context.getBlockPos(), shapeContext);
    }

    default boolean checkStatePlacement() {
        return true;
    }

    static boolean writeNbtToBlockEntity(World world, @Nullable PlayerEntity player, BlockPos pos, ItemStack stack) {
        MinecraftServer minecraftServer = world.getServer();
        if (minecraftServer != null) {
            NbtCompound nbtCompound = getBlockEntityNbt(stack);
            if (nbtCompound != null) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity != null) {
                    if (!world.isClient && blockEntity.copyItemDataRequiresOperator() && (player == null || !player.isCreativeLevelTwoOp())) {
                        return false;
                    }

                    NbtCompound nbtCompound2 = blockEntity.createNbt();
                    NbtCompound nbtCompound3 = nbtCompound2.copy();
                    nbtCompound2.copyFrom(nbtCompound);
                    if (!nbtCompound2.equals(nbtCompound3)) {
                        blockEntity.readNbt(nbtCompound2);
                        blockEntity.markDirty();
                        return true;
                    }
                }
            }

        }
        return false;
    }

    @Nullable
    static NbtCompound getBlockEntityNbt(ItemStack stack) {
        return stack.getSubNbt("BlockEntityTag");
    }

    default boolean place(ItemPlacementContext context, BlockState state) {
        return context.getWorld().setBlockState(context.getBlockPos(), state, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
    }
}
