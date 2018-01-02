package com.legacy.wasteland.client.renders;

import com.legacy.wasteland.client.renders.entities.factory.DayZombieFactory;
import com.legacy.wasteland.entities.EntityDayZombie;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntityRenderRegistry {
   public static void initialize() {
      registerEntityFactory(EntityDayZombie.class, new DayZombieFactory());
   }

   public static void registerEntityFactory(Class classes, IRenderFactory factory) {
      RenderingRegistry.registerEntityRenderingHandler(classes, factory);
   }
}
