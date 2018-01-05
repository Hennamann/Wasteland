package com.legacy.wasteland.world.biome;

import com.legacy.wasteland.Wasteland;
import com.legacy.wasteland.config.WorldConfig;
import com.legacy.wasteland.world.biome.decorations.BiomeDecoratorWasteland;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;

public class BiomeGenWastelandBase extends Biome {
   public BiomeGenWastelandBase(BiomeProperties properties) {
      super(properties);
      this.spawnableCreatureList.clear();
      this.spawnableWaterCreatureList.clear();
      this.decorator = new BiomeDecoratorWasteland();
      if(WorldConfig.shouldSpawnDayZombies) {
         this.spawnableCreatureList.add(new SpawnListEntry(EntityZombie.class, 100, 4, 5));
      }
      try {
         if (Loader.isModLoaded("cyberware") && WorldConfig.shouldSpawnCyberZombies && WorldConfig.shouldSpawnDayZombies) {
            Class cyberZombie = Class.forName("flaxbeard.cyberware.common.entity.EntityCyberZombie");
            Wasteland.wastelandLogger.info("Found flaxbeard.cyberware.common.entity.EntityCyberZombie adding CyberWare support");
            this.spawnableCreatureList.add(new SpawnListEntry(cyberZombie, 100, 4, 4));
         }
      } catch(Exception ex) {
         Wasteland.wastelandLogger.warn("Could not find: flaxbeard.cyberware.common.entity.EntityCyberZombie skipping CyberWare support");
      }
      this.loadBiome();
   }

   public BiomeDecorator getModdedBiomeDecorator(BiomeDecorator original) {
      return new BiomeDecoratorWasteland();
   }

   public void loadBiome() {
      this.decorator.deadBushPerChunk = 5;
      this.decorator.flowersPerChunk = -999;
      this.decorator.generateFalls = false;
      this.decorator.grassPerChunk = -999;
      this.decorator.treesPerChunk = -999;
      this.topBlock = WorldConfig.getSurfaceBlock().getDefaultState();
      this.fillerBlock = WorldConfig.getFillerBlock().getDefaultState();
   }
}
