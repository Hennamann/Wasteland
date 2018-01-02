package com.legacy.wasteland.config;

import java.io.File;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

public class WorldConfig {
   public static int wastelandTreeSpawnRate;
   public static int randomFirePerChunk;
   public static int wastelandRuinRarirty;
   public static int forestRuinRarity;
   public static int mountainRuinRarity;
   public static String surfaceBlock;
   public static String fillerBlock;
   public static boolean shouldSpawnBunker;
   public static boolean shouldSpawnDayZombies;
   public static boolean shouldSpawnCities;

   public static void init(File location) {
      File newFile = new File(location + "/wasteland_mod" + "/World_Info.cfg");

      try {
         newFile.createNewFile();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      Configuration config = new Configuration(newFile);
      config.load();
      String topic = "Worldgen Spawnrate";
      wastelandTreeSpawnRate = config.get(topic, "Dead Tree Rarity", 2).getInt(2);
      randomFirePerChunk = config.get(topic, "Wasteland fires per chunk", 1).getInt(1);
      wastelandRuinRarirty = config.get(topic, "Wasteland ruins rarity", 50).getInt(50);
      forestRuinRarity = config.get(topic, "Forest tent/treehouse/ruins rarity", 50).getInt(50);
      mountainRuinRarity = config.get(topic, "Mountain ruins rarity", 50).getInt(50);
      topic = "Worldgen Spawning";
      shouldSpawnBunker = config.get(topic, "Spawn in underground bunker", true).getBoolean(true);
      shouldSpawnCities = config.get(topic, "Enable cities", true).getBoolean(true);
      shouldSpawnDayZombies = config.get(topic, "Allow zombies to spawn in daylight", true).getBoolean(true);
      topic = "Misc";
      surfaceBlock = config.get(topic, "The top block layer of the wasteland biome", "minecraft:dirt").getString();
      fillerBlock = config.get(topic, "Top filler block layer of the wasteland biome", "minecraft:stone").getString();
      config.save();
   }

   public static Block getSurfaceBlock() {
      return Block.REGISTRY.getObject(new ResourceLocation(surfaceBlock));
   }

   public static Block getFillerBlock() {
      return Block.REGISTRY.getObject(new ResourceLocation(fillerBlock));
   }
}
