package org.dfood.jade;

import org.dfood.block.ComplexFoodBlock;
import org.dfood.block.FoodBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class FoodBlockPlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(new FoodBlockComponentProvider(), FoodBlock.class);
        registration.registerBlockComponent(new ComplexFoodBlockComponentProvider(), ComplexFoodBlock.class);
    }
}
