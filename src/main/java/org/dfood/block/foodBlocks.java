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
    public static final Block COOKIE = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_YELLOW).strength(0.2F, 0.2F).nonOpaque()
            .pistonBehavior(PistonBehavior.DESTROY),
            10);
    public static final Block APPLE = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.RED).strength(0.2F, 0.2F).nonOpaque()
            .pistonBehavior(PistonBehavior.DESTROY),
            5);
    public static final Block MELON_SLICE = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.LIME).strength(0.2F, 0.2F).nonOpaque()
            .pistonBehavior(PistonBehavior.DESTROY),
            5);
    public static final Block BREAD = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_YELLOW).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.BREAD).pistonBehavior(PistonBehavior.DESTROY),
            5);
    // 蔬菜类
    public static final Block BEETROOT = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.RED).strength(0.2F, 0.2F).nonOpaque()
            .sounds(BlockSoundGroup.CANDLE).pistonBehavior(PistonBehavior.DESTROY),
            5);
    public static final Block POTATO = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.GOLD).strength(0.2F, 0.2F).nonOpaque()
            .sounds(BlockSoundGroup.CANDLE).pistonBehavior(PistonBehavior.DESTROY),
            5);
    public static final Block BAKED_POTATO = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.GOLD).strength(0.2F, 0.2F).nonOpaque()
            .sounds(BlockSoundGroup.CANDLE).pistonBehavior(PistonBehavior.DESTROY),
            5);
    public static final Block CARROT = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.YELLOW).strength(0.2F, 0.2F).nonOpaque()
            .sounds(BlockSoundGroup.CANDLE).pistonBehavior(PistonBehavior.DESTROY),
            5);
    public static final Block SWEET_BERRIES = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.RED).strength(0.2F, 0.2F).nonOpaque()
            .sounds(BlockSoundGroup.SWEET_BERRY_BUSH).pistonBehavior(PistonBehavior.DESTROY),
            5);
    public static final Block GLOW_BERRIES = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.YELLOW).strength(0.2F, 0.2F).nonOpaque()
            .sounds(BlockSoundGroup.SWEET_BERRY_BUSH).pistonBehavior(PistonBehavior.DESTROY),
            12);
    // 生熟肉类
    public static final Block CHICKEN = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.LIGHT_GRAY).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            1);
    public static final Block COOKED_CHICKEN = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_YELLOW).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            1);
    public static final Block BEEF = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.BROWN).strength(0.3F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final Block COOKED_BEEF = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_BROWN).strength(0.3F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final Block MUTTON = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.PINK).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final Block COOKED_MUTTON = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_PINK).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final Block PORKCHOP = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.PINK).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final Block COOKED_PORKCHOP = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_PINK).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final Block RABBIT = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.BROWN).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            1);
    public static final Block COOKED_RABBIT = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_BROWN).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.MEAT).pistonBehavior(PistonBehavior.DESTROY),
            1);
    // 鱼类
    public static final Block COD = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.LIGHT_BLUE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.FISH).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final Block COOKED_COD = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.FISH).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final Block SALMON = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.LIGHT_BLUE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.FISH).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final Block COOKED_SALMON = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.FISH).pistonBehavior(PistonBehavior.DESTROY),
            3);
    public static final Block PUFFERFISH = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.LIGHT_BLUE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.FISH).pistonBehavior(PistonBehavior.DESTROY),
            1);
    // 炖菜
    public static final Block RABBIT_STEW = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.BROWN).strength(0.1F, 0.1F).nonOpaque()
            .sounds(BlockSoundGroup.DECORATED_POT).pistonBehavior(PistonBehavior.DESTROY),
            1);
    public static final Block MUSHROOM_STEW = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.BROWN).strength(0.1F, 0.1F).nonOpaque()
            .sounds(BlockSoundGroup.DECORATED_POT).pistonBehavior(PistonBehavior.DESTROY),
            1);
    public static final Block BEETROOT_SOUP  = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.BROWN).strength(0.1F, 0.1F).nonOpaque()
            .sounds(BlockSoundGroup.DECORATED_POT).pistonBehavior(PistonBehavior.DESTROY),
            1);
    // 其他
    public static final Block PUMPKIN_PIE = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.TERRACOTTA_ORANGE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(BlockSoundGroup.WOOL).pistonBehavior(PistonBehavior.DESTROY),
            1);
    public static final Block CHORUS_FRUIT = new ChorusFruitBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.PURPLE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.CHORUS_FRUIT).pistonBehavior(PistonBehavior.DESTROY),
            5);
    public static final Block EGG = new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.WHITE).strength(0.2F, 0.2F).nonOpaque()
            .sounds(ModSoundGroups.EGG).pistonBehavior(PistonBehavior.DESTROY),
            5);
}
