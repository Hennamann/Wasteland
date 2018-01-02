package com.legacy.wasteland.world.util;

import com.legacy.wasteland.config.WorldConfig;
import com.legacy.wasteland.world.WastelandWorld;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.fml.common.Loader;

public class WastelandGenLayer extends GenLayer {
   private List biomes = new ArrayList();

   public WastelandGenLayer(long worldGenSeed, GenLayer parentLayer) {
      super(worldGenSeed);
      this.parent = parentLayer;
      int i;
      if(WorldConfig.shouldSpawnCities) {
         for(i = 0; i < 1; ++i) {
            this.biomes.add(new BiomeEntry(WastelandWorld.apocalypse_city, 10));
         }
      }

      for(i = 0; i < 10; ++i) {
         this.biomes.add(new BiomeEntry(WastelandWorld.apocalypse_forest, 10));
         this.biomes.add(new BiomeEntry(WastelandWorld.apocalypse_mountains, 10));
      }

      for(i = 0; i < 40; ++i) {
         this.biomes.add(new BiomeEntry(WastelandWorld.apocalypse_desert, 10));
         this.biomes.add(new BiomeEntry(WastelandWorld.apocalypse, 10));
      }

   }

   public int[] getInts(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_) {
      int[] aint1 = IntCache.getIntCache(p_75904_3_ * p_75904_4_);

      for(int i1 = 0; i1 < p_75904_4_; ++i1) {
         for(int j1 = 0; j1 < p_75904_3_; ++j1) {
            this.initChunkSeed((long)(j1 + p_75904_1_), (long)(i1 + p_75904_2_));
            aint1[j1 + i1 * p_75904_3_] = Biome.getIdForBiome(((BiomeEntry)WeightedRandom.getRandomItem(this.biomes, (int)(this.nextLong((long)(WeightedRandom.getTotalWeight(this.biomes) / 10)) * 10L))).biome);
         }
      }

      return aint1;
   }
}
