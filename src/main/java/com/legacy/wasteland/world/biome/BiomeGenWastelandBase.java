package com.legacy.wasteland.world.biome;

import com.legacy.wasteland.config.WorldConfig;
import com.legacy.wasteland.entities.EntityDayZombie;
import com.legacy.wasteland.world.biome.decorations.BiomeDecoratorWasteland;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.biome.Biome.SpawnListEntry;

public class BiomeGenWastelandBase extends Biome {
   public BiomeGenWastelandBase(BiomeProperties properties) {
      super(properties);
      this.spawnableCreatureList.clear();
      this.spawnableWaterCreatureList.clear();
      this.theBiomeDecorator = new BiomeDecoratorWasteland();
      if(WorldConfig.shouldSpawnDayZombies) {
         this.spawnableCreatureList.add(new SpawnListEntry(EntityDayZombie.class, 100, 4, 4));
      }

      this.loadBiome();
   }

   public BiomeDecorator getModdedBiomeDecorator(BiomeDecorator original) {
      return new BiomeDecoratorWasteland();
   }

   public void loadBiome() {
      this.theBiomeDecorator.deadBushPerChunk = 5;
      this.theBiomeDecorator.flowersPerChunk = -999;
      this.theBiomeDecorator.generateLakes = false;
      this.theBiomeDecorator.grassPerChunk = -999;
      this.theBiomeDecorator.treesPerChunk = -999;
      this.topBlock = WorldConfig.getSurfaceBlock().getDefaultState();
      this.fillerBlock = WorldConfig.getFillerBlock().getDefaultState();
   }
}
