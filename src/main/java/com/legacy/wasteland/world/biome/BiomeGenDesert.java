package com.legacy.wasteland.world.biome;

import com.legacy.wasteland.world.biome.BiomeGenWastelandBase;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class BiomeGenDesert extends BiomeGenWastelandBase {
   private NoiseGeneratorPerlin pillarNoise;
   private NoiseGeneratorPerlin pillarRoofNoise;

   public BiomeGenDesert(BiomeProperties properties) {
      super(properties);
      this.topBlock = Blocks.SAND.getDefaultState();
      this.fillerBlock = Blocks.SANDSTONE.getDefaultState();
   }

   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
      if(this.pillarNoise == null || this.pillarRoofNoise == null) {
         Random d4 = new Random(worldIn.getSeed());
         this.pillarNoise = new NoiseGeneratorPerlin(d4, 4);
         this.pillarRoofNoise = new NoiseGeneratorPerlin(d4, 1);
      }

      double var26 = 0.0D;
      int i = (x & -16) + (z & 15);
      int j = (z & -16) + (x & 15);
      double d0 = Math.min(Math.abs(noiseVal), this.pillarNoise.getValue((double)i * 0.25D, (double)j * 0.25D));
      if(d0 > 0.0D) {
         double k1 = Math.abs(this.pillarRoofNoise.getValue((double)i * 0.001953125D, (double)j * 0.001953125D));
         var26 = d0 * d0 * 2.5D;
         double i2 = Math.ceil(k1 * 50.0D) + 14.0D;
         if(var26 > i2) {
            var26 = i2;
         }

         var26 += 64.0D;
      }

      int var27 = x & 15;
      int l1 = z & 15;
      int var28 = worldIn.getSeaLevel();
      IBlockState iblockstate = this.topBlock;
      IBlockState iblockstate3 = this.fillerBlock;
      int k = (int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
      int l = -1;
      boolean flag1 = false;
      int i1 = 0;

      for(int j1 = 255; j1 >= 0; --j1) {
         if(chunkPrimerIn.getBlockState(l1, j1, var27).getMaterial() == Material.AIR && j1 < (int)var26) {
            chunkPrimerIn.setBlockState(l1, j1, var27, this.topBlock);
         }

         if(j1 <= rand.nextInt(5)) {
            chunkPrimerIn.setBlockState(l1, j1, var27, BEDROCK);
         } else if(i1 < 15) {
            IBlockState iblockstate1 = chunkPrimerIn.getBlockState(l1, j1, var27);
            if(iblockstate1.getMaterial() == Material.AIR) {
               l = -1;
            } else if(iblockstate1.getBlock() == Blocks.STONE) {
               if(l == -1) {
                  flag1 = false;
                  if(k <= 0) {
                     iblockstate = AIR;
                     iblockstate3 = this.fillerBlock;
                  } else if(j1 >= var28 - 4 && j1 <= var28 + 1) {
                     iblockstate = this.topBlock;
                     iblockstate3 = this.fillerBlock;
                  }

                  if(j1 < var28 && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
                     iblockstate = WATER;
                  }

                  l = k + Math.max(0, j1 - var28);
                  if(j1 >= var28 - 1) {
                     if(j1 > var28 + 3 + k) {
                        IBlockState iblockstate2 = this.topBlock;
                        chunkPrimerIn.setBlockState(l1, j1, var27, iblockstate2);
                     } else {
                        chunkPrimerIn.setBlockState(l1, j1, var27, this.topBlock);
                        flag1 = true;
                     }
                  } else {
                     chunkPrimerIn.setBlockState(l1, j1, var27, iblockstate3);
                  }
               } else if(l > 0) {
                  --l;
                  if(flag1) {
                     chunkPrimerIn.setBlockState(l1, j1, var27, this.topBlock);
                  }
               }

               ++i1;
            }
         }
      }
   }
}
