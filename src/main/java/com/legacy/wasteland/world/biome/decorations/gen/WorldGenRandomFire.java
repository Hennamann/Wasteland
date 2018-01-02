package com.legacy.wasteland.world.biome.decorations.gen;

import com.legacy.wasteland.config.WorldConfig;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenRandomFire extends WorldGenerator {
   public WorldGenRandomFire() {
      super(true);
   }

   public boolean generate(World worldIn, Random rand, BlockPos position) {
      if(worldIn.getBlockState(position.down()).getBlock() == WorldConfig.getSurfaceBlock()) {
         worldIn.setBlockState(position.down(), Blocks.NETHERRACK.getDefaultState());
         worldIn.setBlockState(position, Blocks.FIRE.getDefaultState());
         return true;
      } else {
         return false;
      }
   }
}
