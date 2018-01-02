package com.legacy.wasteland.world;

import com.legacy.wasteland.config.WorldConfig;
import com.legacy.wasteland.world.biome.*;
import com.legacy.wasteland.world.biome.properties.WastelandPropertiesBase;
import com.legacy.wasteland.world.type.WorldTypeWasteland;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class WastelandWorld {
   public static WorldType worldtype_wasteland;
   public static Biome apocalypse;
   public static Biome apocalypse_mountains;
   public static Biome apocalypse_forest;
   public static Biome apocalypse_city;
   public static Biome apocalypse_desert;
   public static Biome apocalypse_mist;

   public static void init() {
      apocalypse = register("Wasteland", new BiomeGenApocalypse(new WastelandPropertiesBase("Wasteland", 0.1F, 0.05F, 14728553)));
      apocalypse_mountains = register("Wasteland Mountains", new BiomeGenMountains(new WastelandPropertiesBase("Wasteland Mountains", 0.09F, 0.03F, 10255379)));
      apocalypse_forest = register("Wasteland Forest", new BiomeGenForest(new WastelandPropertiesBase("Wasteland Forest", 0.1F, 0.05F, 10793807)));
      apocalypse_city = register("Wasteland City", new BiomeGenCity(new WastelandPropertiesBase("Wasteland City", 0.09F, 0.05F, 9410739)));
      apocalypse_desert = register("Wasteland Desert", new BiomeGenDesert(new WastelandPropertiesBase("Wasteland Desert", 0.09F, 0.05F, 9410739)));
      if (Loader.isModLoaded("mistcore")) {
          apocalypse_mist = register("Mist Wasteland", new BiomeGenApocalypseMist(new WastelandPropertiesBase("Misty Wasteland", 0.1F, 0.05F, 14728553)));
      }
      worldtype_wasteland = new WorldTypeWasteland();
   }

   public static Biome register(String name, Biome biome) {
      biome.setRegistryName(name);
      ForgeRegistries.BIOMES.register(biome);
      BiomeDictionary.addTypes(biome, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.DRY, BiomeDictionary.Type.WASTELAND);
      BiomeManager.addBiome(BiomeManager.BiomeType.DESERT, new BiomeManager.BiomeEntry(biome, 10));
      return biome;
   }
}
