package com.legacy.wasteland.client.renders.entities;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

public class DayZombieRenderer extends RenderZombie {
   private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation("wastelands", "textures/entity/zombie/zombie.png");

   public DayZombieRenderer(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   protected ResourceLocation getEntityTexture(EntityZombie entity) {
      return ZOMBIE_TEXTURES;
   }
}
