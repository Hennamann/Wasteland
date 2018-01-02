package com.legacy.wasteland.config;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class LootConfig {
   public static String[] ruinEasyLoot;
   public static String[] ruinRareLoot;
   public static String[] seedLoot;
   public static String[] startLoot;

   public static void init(File location) {
      File newFile = new File(location + "/wasteland_mod" + "/Loot_Info.cfg");

      try {
         newFile.createNewFile();
      } catch (IOException var3) {
         var3.printStackTrace();
      }

      Configuration config = new Configuration(newFile);
      config.load();
      config.setCategoryComment("Loot List", "Add item names chest loot. Do NOT skip or add blank lines. Format for items:\nmod_name:item_name,max,min\nCheck mod language registry for item names. max = maximum stack size, min = minimum stack size. If max/min stack size > game stack limit, game will chose the stack limit.");
      ruinEasyLoot = config.get("Loot List", "Common ruins chest loot items", new String[]{"minecraft:mushroom_stew,1,1", "minecraft:bread,4,1", "minecraft:wheat,2,1", "minecraft:apple,3,1", "minecraft:string,6,2", "minecraft:rotten_flesh,8,2"}).getStringList();
      ruinRareLoot = config.get("Loot List", "Rare ruins chest loot items", new String[]{"minecraft:bucket,1,1", "minecraft:cooked_porkchop,3,2", "minecraft:cooked_beef,4,1", "minecraft:feather,8,2", "minecraft:iron_ingot,3,1"}).getStringList();
      seedLoot = config.get("Loot List", "Ruins seed chest loot items", new String[]{"minecraft:wheat_seeds,8,2"}).getStringList();
      startLoot = config.get("Loot List", "Start bunker chest loot items", new String[]{"minecraft:stone_sword,1,1", "minecraft:bread,2,2", "minecraft:leather_helmet,1,1", "minecraft:leather_boots,1,1", "minecraft:cooked_chicken,3,2", "minecraft:glass_bottle,2,1"}).getStringList();
      config.save();
   }

   public static ItemStack[] getLoot(String[] rawStringArray) {
      ItemStack[] items = new ItemStack[rawStringArray.length];

      for(int i = 0; i < rawStringArray.length; ++i) {
         if(rawStringArray[i].length() > 0) {
            String[] split = rawStringArray[i].split(",");
            int max;
            int min;
            if(split.length == 3) {
               max = Integer.parseInt(split[1]);
               min = Integer.parseInt(split[2]);
            } else {
               max = 1;
               min = 1;
            }

            int range = max - min <= 0?1:max - min;
            items[i] = GameRegistry.makeItemStack(split[0], 0, (new Random()).nextInt(range) + 1, null);
         }
      }

      return items;
   }
}
