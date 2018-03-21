package com.legacy.wasteland.world;

import com.legacy.wasteland.config.WastelandConfig;
import com.legacy.wasteland.world.biome.*;
import com.legacy.wasteland.world.type.WorldTypeWasteland;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class WastelandWorld {
    public static WorldType worldtype_wasteland;
    public static Biome apocalypse;
    public static Biome apocalypse_mountains;
    public static Biome apocalypse_forest;
    public static Biome apocalypse_city;
    public static Biome apocalypse_desert;

    public static void init() {
        apocalypse = register("Wasteland", new BiomeGenApocalypse(new Biome.BiomeProperties("Wasteland").setTemperature(-0.5F).setBaseHeight(0.1F).setHeightVariation(0.05F).setWaterColor(14728553).setRainfall(0.5F).setSnowEnabled()));
        if (WastelandConfig.biomes.wastelandMountainsEnabled) {
            apocalypse_mountains = register("Wasteland Mountains", new BiomeGenMountains(new Biome.BiomeProperties("Wasteland Mountains").setTemperature(-0.5F).setBaseHeight(0.09F).setHeightVariation(0.03F).setWaterColor(10255379).setRainfall(0.5F).setSnowEnabled()));
        }
        if (WastelandConfig.biomes.wastelandForestEnabled) {
            apocalypse_forest = register("Wasteland Forest", new BiomeGenForest(new Biome.BiomeProperties("Wasteland Forest").setTemperature(-0.5F).setBaseHeight(0.1F).setHeightVariation(0.05F).setWaterColor(10793807).setRainfall(0.5F).setSnowEnabled()));
        }
        if (WastelandConfig.biomes.wastelandCityEnabled) {
            apocalypse_city = register("Wasteland City", new BiomeGenCity(new Biome.BiomeProperties("Wasteland City").setTemperature(-0.5F).setBaseHeight(0.09F).setHeightVariation(0.05F).setWaterColor(9410739).setRainfall(0.5F).setSnowEnabled()));
        }
        apocalypse_desert = register("Wasteland Desert", new BiomeGenDesert(new Biome.BiomeProperties("Wasteland Desert").setTemperature(-0.5F).setBaseHeight(0.09F).setHeightVariation(0.05F).setWaterColor(9410739).setRainfall(0.5F).setSnowEnabled()));
        worldtype_wasteland = new WorldTypeWasteland();
    }

    public static Biome register(String name, Biome biome) {
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        BiomeDictionary.addTypes(biome, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.DRY, BiomeDictionary.Type.WASTELAND);
        if (WastelandConfig.biomes.shouldWastelandBiomesSpawnInOverworld) {
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(biome, 10));
        }
        return biome;
    }
}
