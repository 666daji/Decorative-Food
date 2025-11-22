package org.dfood.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import org.dfood.util.mixin.dFoodSignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

@Mixin(SignBlockEntity.class)
public abstract class SignBlockEntityMixin extends BlockEntity implements dFoodSignBlockEntity {
    /** 正面物品和背面物品 */
    @Unique
    private ItemStack dFood$frontItem = ItemStack.EMPTY;
    @Unique
    private ItemStack dFood$backItem = ItemStack.EMPTY;

    /** 定义允许放入的物品 */
    @Unique
    private static final Set<Item> ALLOWED_ITEMS = new HashSet<>();

    static {
        ALLOWED_ITEMS.add(Items.COD);
        ALLOWED_ITEMS.add(Items.COOKED_COD);
        ALLOWED_ITEMS.add(Items.SALMON);
        ALLOWED_ITEMS.add(Items.COOKED_SALMON);
    }

    public SignBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    private void dFood$readNbt(NbtCompound nbt, CallbackInfo ci) {
        // 读取正面物品
        if (nbt.contains("dfood_front_item", 10)) {
            this.dFood$frontItem = ItemStack.fromNbt(nbt.getCompound("dfood_front_item"));
        } else {
            this.dFood$frontItem = ItemStack.EMPTY;
        }

        // 读取背面物品
        if (nbt.contains("dfood_back_item", 10)) {
            this.dFood$backItem = ItemStack.fromNbt(nbt.getCompound("dfood_back_item"));
        } else {
            this.dFood$backItem = ItemStack.EMPTY;
        }
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    private void dFood$writeNbt(NbtCompound nbt, CallbackInfo ci) {
        // 保存正面物品
        if (!this.dFood$frontItem.isEmpty()) {
            NbtCompound frontNbt = new NbtCompound();
            this.dFood$frontItem.writeNbt(frontNbt);
            nbt.put("dfood_front_item", frontNbt);
        }

        // 保存背面物品
        if (!this.dFood$backItem.isEmpty()) {
            NbtCompound backNbt = new NbtCompound();
            this.dFood$backItem.writeNbt(backNbt);
            nbt.put("dfood_back_item", backNbt);
        }
    }

    @Override
    public ItemStack dFood$getItem(boolean front) {
        return front ? dFood$frontItem : dFood$backItem;
    }

    @Override
    public void dFood$setItem(boolean front, ItemStack stack) {
        if (front) {
            this.dFood$frontItem = stack;
        } else {
            this.dFood$backItem = stack;
        }
        this.markDirty();

        // 同步数据到客户端
        if (this.world != null && !this.world.isClient) {
            this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
        }
    }

    @Override
    public boolean dFood$tryAddItem(boolean front, ItemStack stack) {

        if (stack.isEmpty() || !ALLOWED_ITEMS.contains(stack.getItem())) {
            return false;
        }

        // 检查对应面是否已有物品
        if (dFood$hasItem(front)) {
            return false;
        }

        // 设置物品（最大堆叠数为1）
        ItemStack singleStack = new ItemStack(stack.getItem(), 1);
        dFood$setItem(front, singleStack);
        stack.decrement(1);

        return true;
    }

    @Override
    public boolean dFood$hasItem(boolean front) {
        ItemStack item = front ? dFood$frontItem : dFood$backItem;
        return !item.isEmpty();
    }

    @Override
    public int dFood$getIronNuggetsToReturn() {
        int count = 0;
        if (!dFood$frontItem.isEmpty()) count++;
        if (!dFood$backItem.isEmpty()) count++;
        return count;
    }
}