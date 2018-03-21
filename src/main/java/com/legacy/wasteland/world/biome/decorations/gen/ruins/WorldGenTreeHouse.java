package com.legacy.wasteland.world.biome.decorations.gen.ruins;

import com.legacy.wasteland.config.WastelandConfig;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenTreeHouse extends WorldGenerator {
    public boolean generate(World world, Random rand, BlockPos pos) {
        if (world.getBlockState(pos.down()).getBlock() == WastelandConfig.worldgen.getSurfaceBlock()) {
            world.setBlockState(pos.add(-1, 0, -1), Blocks.PLANKS.getDefaultState());
            world.setBlockState(pos.add(-1, 1, -1), Blocks.LOG.getDefaultState());
            if (!world.getBlockState(pos.add(0, 1, -1)).getMaterial().isSolid()) {
                world.setBlockState(pos.add(0, 1, -1), Blocks.LADDER.getDefaultState());
            }

            world.setBlockState(pos.up(2), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.up(3), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.up(4), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.add(-1, 4, 0), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.add(-1, 4, -1), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.add(0, 4, -1), Blocks.LADDER.getDefaultState());
            world.setBlockState(pos.up(5), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.add(1, 5, -1), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.add(1, 5, 0), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.add(0, 5, -1), Blocks.LADDER.getDefaultState());
            world.setBlockState(pos.add(0, 5, 1), Blocks.PLANKS.getDefaultState());
            world.setBlockState(pos.add(0, 5, 2), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.add(-1, 5, -2), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.add(-1, 5, -1), Blocks.PLANKS.getDefaultState());
            world.setBlockState(pos.add(-1, 5, 0), Blocks.PLANKS.getDefaultState());
            world.setBlockState(pos.add(-1, 5, 1), Blocks.PLANKS.getDefaultState());
            world.setBlockState(pos.add(-2, 5, 0), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.up(6), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.add(2, 6, -2), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.add(-1, 6, 1), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.add(-1, 6, 1), Blocks.CHEST.getDefaultState());
            TileEntityChest chest1 = (TileEntityChest) world.getTileEntity(pos.add(-1, 6, 1));
            String[] usedLootTable = rand.nextInt(3) == 0 ? WastelandConfig.loot.ruinRareLoot : WastelandConfig.loot.ruinEasyLoot;

            for (int treasureSize = 0; treasureSize < 2 + rand.nextInt(2); ++treasureSize) {
                chest1.setInventorySlotContents(rand.nextInt(chest1.getSizeInventory()), WastelandConfig.loot.getLoot(usedLootTable)[rand.nextInt(usedLootTable.length)]);
            }

            world.setBlockState(pos.up(7), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.add(0, 6, 1), Blocks.LOG.getDefaultState());
            world.setBlockState(pos.up(8), Blocks.LEAVES.getDefaultState());
        }

        return false;
    }
}
