package com.henrikstabell.wasteland.world.biome.decorations.gen;

import com.henrikstabell.wasteland.config.WastelandConfig;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenRandomFire extends WorldGenerator {
    public WorldGenRandomFire() {
        super(true);
    }

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (worldIn.getBlockState(position.down()).getBlock() == WastelandConfig.worldgen.getSurfaceBlock()) {
            worldIn.setBlockState(position.down(), Blocks.NETHERRACK.getDefaultState());
            worldIn.setBlockState(position, Blocks.FIRE.getDefaultState());
            return true;
        } else {
            return false;
        }
    }
}
