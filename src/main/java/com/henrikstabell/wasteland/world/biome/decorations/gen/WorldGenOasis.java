package com.henrikstabell.wasteland.world.biome.decorations.gen;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenOasis extends WorldGenerator {
    public WorldGenOasis() {
        super(false);
    }

    public boolean generate(World world, Random random, BlockPos position) {
        int x;
        int z;
        for (x = -2; x < 3; ++x) {
            for (z = -2; z < 3; ++z) {
                if (!world.getBlockState(position.add(x, -1, z)).getMaterial().isSolid()) {
                    return false;
                }
            }
        }

        for (x = -3; x < 4; ++x) {
            for (z = -3; z < 4; ++z) {
                world.setBlockState(position.add(x, 0, z), Blocks.WATER.getDefaultState());
                if (x == -3 || x == 3 || z == -3 || z == 3) {
                    world.setBlockState(position.add(x, 0, z), random.nextInt(2) == 0 ? Blocks.LOG.getDefaultState() : Blocks.LEAVES.getDefaultState());
                }

                if (x == 0 && z == 0) {
                    world.setBlockState(position, Blocks.GRASS.getDefaultState());
                }
            }
        }

        (new WorldGenReed()).generate(world, random, position);
        return true;
    }
}
