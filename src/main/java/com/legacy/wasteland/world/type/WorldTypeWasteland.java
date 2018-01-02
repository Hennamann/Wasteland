package com.legacy.wasteland.world.type;

import com.legacy.wasteland.world.ChunkGeneratorWasteland;
import com.legacy.wasteland.world.util.WastelandGenLayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class WorldTypeWasteland extends WorldType {
   public WorldTypeWasteland() {
      super("wasteland");
   }

   public BiomeProvider getBiomeProvider(World world) {
      return new BiomeProvider(world.getWorldInfo());
   }

   public boolean isCustomizable() {
      return false;
   }

   public net.minecraft.world.chunk.IChunkGenerator getChunkGenerator(World world, String generatorOptions)
   {
      return new ChunkGeneratorWasteland(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), generatorOptions);
   }

   public net.minecraft.world.gen.layer.GenLayer getBiomeLayer(long worldSeed, net.minecraft.world.gen.layer.GenLayer parentLayer, net.minecraft.world.gen.ChunkProviderSettings chunkSettings) {
      WastelandGenLayer ret = new WastelandGenLayer(200L, parentLayer);
      GenLayer ret1 = GenLayerZoom.magnify(1000L, ret, 2);
      GenLayerBiomeEdge ret2 = new GenLayerBiomeEdge(1000L, ret1);
      return ret2;
   }
}
