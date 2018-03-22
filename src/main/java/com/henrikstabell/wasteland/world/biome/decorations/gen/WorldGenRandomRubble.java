package com.henrikstabell.wasteland.world.biome.decorations.gen;

import com.henrikstabell.wasteland.config.WastelandConfig;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenRandomRubble extends WorldGenerator {

    public WorldGenRandomRubble() {
        super(true);
    }

    public boolean generate(World world, Random random, BlockPos pos) {
        if (world.getBlockState(pos.down()).getBlock() == WastelandConfig.worldgen.getSurfaceBlock() && !world.getBlockState(pos.up()).getMaterial().isSolid() && !world.getBlockState(pos).getMaterial().isSolid() && !world.getBlockState(pos.south()).getMaterial().isSolid() && !world.getBlockState(pos.east()).getMaterial().isSolid() && !world.getBlockState(pos.west()).getMaterial().isSolid() && !world.getBlockState(pos.north()).getMaterial().isSolid() && world.isAirBlock(pos) && world.isAirBlock(pos.up())) {
            for (int j1 = 0; j1 < 75; ++j1) {
                BlockPos randomPos = pos.add(random.nextInt(8), random.nextInt(4), random.nextInt(8));
                Material material6 = world.getBlockState(randomPos.down()).getMaterial();
                if (world.isAirBlock(randomPos) && material6.isSolid()) {
                    Block block = Blocks.COBBLESTONE;
                    int chance = random.nextInt(31);
                    if (chance < 10) {
                        block = Blocks.COBBLESTONE;
                    } else if (chance >= 10 && chance < 20) {
                        block = Blocks.MOSSY_COBBLESTONE;
                    } else if (chance >= 20 && chance < 30) {
                        block = Blocks.PLANKS;
                    } else {
                        block = Blocks.BRICK_BLOCK;
                    }

                    world.setBlockState(randomPos, block.getDefaultState());
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
