package org.dfood.block;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import org.dfood.sound.ModSoundGroups;

/**
 * 关于原版的食物方块定义
 */
public class foodBlocks {
    // 零食
    public static final foodBlockManage COOKIE = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_YELLOW).strength(0.2F, 0.2F).nonOpaque()
            .pistonBehavior(PistonBehavior.DESTROY),
            10);
    public static final foodBlockManage APPLE = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.RED).strength(0.2F, 0.2F).nonOpaque()
            .pistonBehavior(PistonBehavior.DESTROY),
            5);
    public static final foodBlockManage MELON_SLICE = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.LIME).strength(0.2F, 0.2F).nonOpaque()
            .pistonBehavior(PistonBehavior.DESTROY),
            5);
    public static final foodBlockManage BREAD = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_YELLOW).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.BREAD).pistonBehavior(PistonBehavior.DESTROY),
            5);
    // 蔬菜类
    public static final foodBlockManage BEETROOT = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.RED).strength(0.2F, 0.2F).nonOpaque()
            .sounds(BlockSoundGroup.CANDLE).pistonBehavior(PistonBehavior.DESTROY),
            5);
    public static final foodBlockManage POTATO = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.GOLD).strength(0.2F, 0.2F).nonOpaque()
            .sounds(BlockSoundGroup.CANDLE).pistonBehavior(PistonBehavior.DESTROY),
            5, foodBlock.CROPS.POTATO);
    public static final foodBlockManage BAKED_POTATO = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.GOLD).strength(0.2F, 0.2F).nonOpaque()
            .sounds(BlockSoundGroup.CANDLE).pistonBehavior(PistonBehavior.DESTROY),
            5);
    public static final foodBlockManage CARROT = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.YELLOW).strength(0.2F, 0.2F).nonOpaque()
            .sounds(BlockSoundGroup.CANDLE).pistonBehavior(PistonBehavior.DESTROY),
            5, foodBlock.CROPS.CARROT);
    public static final foodBlockManage SWEET_BERRIES = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.RED).strength(0.2F, 0.2F).nonOpaque()
            .sounds(BlockSoundGroup.SWEET_BERRY_BUSH).pistonBehavior(PistonBehavior.DESTROY),
            5);
    public static final foodBlockManage GLOW_BERRIES = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.YELLOW).strength(0.2F, 0.2F).nonOpaque()
            .sounds(BlockSoundGroup.SWEET_BERRY_BUSH).pistonBehavior(PistonBehavior.DESTROY)
            .luminance(state -> state.get(foodBlock.NUMBER_OF_FOOD) + 3),
            12);
    // 生熟肉类
    public static final foodBlockManage CHICKEN = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.LIGHT_GRAY).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            1);
    public static final foodBlockManage COOKED_CHICKEN = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_YELLOW).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            1);
    public static final foodBlockManage BEEF = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.BROWN).strength(0.3F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final foodBlockManage COOKED_BEEF = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_BROWN).strength(0.3F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final foodBlockManage MUTTON = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.PINK).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final foodBlockManage COOKED_MUTTON = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_PINK).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final foodBlockManage PORKCHOP = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.PINK).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final foodBlockManage COOKED_PORKCHOP = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_PINK).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final foodBlockManage RABBIT = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.BROWN).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            1);
    public static final foodBlockManage COOKED_RABBIT = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_BROWN).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            1);
    // 鱼类
    public static final foodBlockManage COD = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.LIGHT_BLUE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.FISH).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final foodBlockManage COOKED_COD = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.FISH).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final foodBlockManage SALMON = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.LIGHT_BLUE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.FISH).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final foodBlockManage COOKED_SALMON = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.FISH).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final foodBlockManage PUFFERFISH = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.LIGHT_BLUE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.FISH).pistonBehavior(PistonBehavior.DESTROY),
            1);
    // 炖菜
    public static final foodBlockManage RABBIT_STEW = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.BROWN).strength(0.1F, 0.1F).nonOpaque()
            .sounds(BlockSoundGroup.DECORATED_POT).pistonBehavior(PistonBehavior.DESTROY),
            1);
    public static final foodBlockManage MUSHROOM_STEW = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.BROWN).strength(0.1F, 0.1F).nonOpaque()
            .sounds(BlockSoundGroup.DECORATED_POT).pistonBehavior(PistonBehavior.DESTROY),
            1);
    public static final foodBlockManage BEETROOT_SOUP  = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.BROWN).strength(0.1F, 0.1F).nonOpaque()
            .sounds(BlockSoundGroup.DECORATED_POT).pistonBehavior(PistonBehavior.DESTROY),
            1);
    // 其他
    public static final foodBlockManage PUMPKIN_PIE = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_ORANGE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(BlockSoundGroup.WOOL).pistonBehavior(PistonBehavior.DESTROY),
            1);
    public static final foodBlockManage CHORUS_FRUIT = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.PURPLE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.CHORUS_FRUIT).pistonBehavior(PistonBehavior.DESTROY),
            5);
    public static final foodBlockManage EGG = new foodBlockManage(AbstractBlock.Settings.create()
            .mapColor(MapColor.WHITE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.EGG).pistonBehavior(PistonBehavior.DESTROY),
            5);
}
