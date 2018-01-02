package com.legacy.wasteland.world.biome.properties;

import net.minecraft.world.biome.Biome.BiomeProperties;

public class WastelandPropertiesBase extends BiomeProperties {
   public WastelandPropertiesBase(String nameIn, float baseHeight, float variationHeight, int waterColor) {
      super(nameIn);
      this.setBaseHeight(baseHeight);
      this.setHeightVariation(variationHeight);
      this.setWaterColor(waterColor);
   }
}
