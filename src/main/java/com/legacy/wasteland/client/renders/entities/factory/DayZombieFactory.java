package com.legacy.wasteland.client.renders.entities.factory;

import com.legacy.wasteland.client.renders.entities.DayZombieRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class DayZombieFactory implements IRenderFactory {
   public DayZombieRenderer createRenderFor(RenderManager manager) {
      return new DayZombieRenderer(manager);
   }
}
