package com.legacy.wasteland.world.biome;

import com.legacy.wasteland.Wasteland;
import com.legacy.wasteland.config.WastelandConfig;
import com.legacy.wasteland.world.biome.decorations.BiomeDecoratorWasteland;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.fml.common.Loader;

public class BiomeGenWastelandBase extends Biome {
   public BiomeGenWastelandBase(BiomeProperties properties) {
      super(properties);
      this.spawnableCreatureList.clear();
      this.spawnableWaterCreatureList.clear();
      this.theBiomeDecorator = new BiomeDecoratorWasteland();
      if(WastelandConfig.worldgen.shouldSpawnDayZombies) {
         this.spawnableCreatureList.add(new SpawnListEntry(EntityZombie.class, 100, 4, 5));
      }
      try {
         if (Loader.isModLoaded("cyberware") && WastelandConfig.worldgen.shouldSpawnCyberZombies && WastelandConfig.worldgen.shouldSpawnDayZombies) {
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
      this.theBiomeDecorator.deadBushPerChunk = 5;
      this.theBiomeDecorator.flowersPerChunk = -999;
      this.theBiomeDecorator.generateLakes = false;
      this.theBiomeDecorator.grassPerChunk = -999;
      this.theBiomeDecorator.treesPerChunk = -999;
      this.topBlock = WastelandConfig.worldgen.getSurfaceBlock().getDefaultState();
      this.fillerBlock = WastelandConfig.worldgen.getFillerBlock().getDefaultState();
   }
}
