package com.legacy.wasteland.entities;

import com.legacy.wasteland.Wasteland;
import com.legacy.wasteland.entities.EntityDayZombie;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class WastelandEntityRegistry {
   public static void init() {
      register(new ResourceLocation(Wasteland.MOD_ID + ":" + "textures/entity/zombie/zombie.png"), EntityDayZombie.class, "day_zombie", 0);
   }

   public static void register(ResourceLocation resourceLocation, Class entityClass, String entityName, int entityID) {
      EntityRegistry.registerModEntity(resourceLocation, entityClass, entityName, entityID, "wastelands", 80, 3, true);
   }
}
