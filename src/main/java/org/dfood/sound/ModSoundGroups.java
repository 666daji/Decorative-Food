package org.dfood.sound;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class ModSoundGroups {
    public static final BlockSoundGroup MEAT = new BlockSoundGroup(
            1.0f,
            1.0f,
            SoundEvents.ENTITY_SLIME_DEATH_SMALL,
            SoundEvents.ENTITY_SLIME_JUMP_SMALL,
            SoundEvents.ENTITY_SLIME_DEATH_SMALL,
            SoundEvents.ENTITY_SLIME_DEATH_SMALL,
            SoundEvents.ENTITY_SLIME_HURT_SMALL
    );
    public static final BlockSoundGroup FISH = new BlockSoundGroup(
            1.0f,
            1.0f,
            SoundEvents.ENTITY_FISH_SWIM,
            SoundEvents.ENTITY_PUFFER_FISH_FLOP,
            SoundEvents.ENTITY_FISH_SWIM,
            SoundEvents.ENTITY_FISH_SWIM,
            SoundEvents.ENTITY_FISH_SWIM
    );
    public static final BlockSoundGroup BREAD = new BlockSoundGroup(
            1.0f,
            1.0f,
            SoundEvents.ENTITY_FOX_AMBIENT,
            SoundEvents.ENTITY_FOX_AMBIENT,
            SoundEvents.ENTITY_FOX_AMBIENT,
            SoundEvents.ENTITY_FOX_AMBIENT,
            SoundEvents.ENTITY_FOX_AMBIENT
    );
    public static final BlockSoundGroup CHORUS_FRUIT = new BlockSoundGroup(
            1.0f,
            1.0f,
            SoundEvents.ENTITY_ENDERMAN_TELEPORT,
            SoundEvents.ENTITY_ENDERMAN_HURT,
            SoundEvents.ENTITY_ENDERMAN_TELEPORT,
            SoundEvents.ENTITY_ENDERMAN_SCREAM,
            SoundEvents.ENTITY_ENDERMAN_TELEPORT
    );
    public static final BlockSoundGroup EGG = new BlockSoundGroup(
            1.0f,
            1.0f,
            SoundEvents.ENTITY_CHICKEN_EGG,
            SoundEvents.ENTITY_CHICKEN_EGG,
            SoundEvents.ENTITY_CHICKEN_EGG,
            SoundEvents.ENTITY_CHICKEN_EGG,
            SoundEvents.ENTITY_CHICKEN_EGG
    );
}
