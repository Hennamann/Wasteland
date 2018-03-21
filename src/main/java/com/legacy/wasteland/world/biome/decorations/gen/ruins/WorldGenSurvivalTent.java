package com.legacy.wasteland.world.biome.decorations.gen.ruins;

import com.legacy.wasteland.config.WastelandConfig;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenSurvivalTent extends WorldGenerator {
    public boolean generate(World world, Random random, BlockPos position) {
        position = position.down();

        int chest;
        for (chest = -3; chest < 3; ++chest) {
            world.setBlockState(position.add(-2, 0, chest), WastelandConfig.worldgen.getSurfaceBlock().getDefaultState());
        }

        for (chest = -3; chest < 0; ++chest) {
            world.setBlockState(position.add(-1, 0, chest), WastelandConfig.worldgen.getSurfaceBlock().getDefaultState());
        }

        world.setBlockState(position.west(1), Blocks.CHEST.getDefaultState());
        TileEntityChest var7 = (TileEntityChest) world.getTileEntity(position.west(1));
        String[] usedLootTable = random.nextInt(3) == 0 ? WastelandConfig.loot.ruinRareLoot : WastelandConfig.loot.ruinEasyLoot;

        int zCoord;
        for (zCoord = 0; zCoord < 2 + random.nextInt(3); ++zCoord) {
            var7.setInventorySlotContents(random.nextInt(var7.getSizeInventory()), WastelandConfig.loot.getLoot(usedLootTable)[random.nextInt(usedLootTable.length)]);
        }

        world.setBlockState(position.add(-1, 0, 1), WastelandConfig.worldgen.getSurfaceBlock().getDefaultState());
        world.setBlockState(position.add(-1, 0, 2), WastelandConfig.worldgen.getSurfaceBlock().getDefaultState());

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(0, 0, zCoord), WastelandConfig.worldgen.getSurfaceBlock().getDefaultState());
            if (zCoord == -1 || zCoord == 0 || zCoord == 1) {
                world.setBlockState(position.add(0, 0, zCoord), Blocks.PLANKS.getDefaultState());
                if (zCoord == 0) {
                    world.setBlockState(position.add(0, -1, zCoord), Blocks.TNT.getDefaultState());
                }
            }
        }

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(1, 0, zCoord), WastelandConfig.worldgen.getSurfaceBlock().getDefaultState());
        }

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(2, 0, zCoord), WastelandConfig.worldgen.getSurfaceBlock().getDefaultState());
        }

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(-2, 1, zCoord), Blocks.WOOL.getDefaultState());
        }

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(-1, 1, zCoord), Blocks.AIR.getDefaultState());
        }

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(0, 1, zCoord), Blocks.AIR.getDefaultState());
            if (zCoord == 0) {
                world.setBlockState(position.add(0, 1, zCoord), Blocks.WOODEN_PRESSURE_PLATE.getDefaultState());
            }
        }

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(1, 1, zCoord), Blocks.AIR.getDefaultState());
        }

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(2, 1, zCoord), Blocks.WOOL.getDefaultState());
        }

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(-2, 2, zCoord), Blocks.AIR.getDefaultState());
        }

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(-1, 2, zCoord), Blocks.WOOL.getDefaultState());
        }

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(0, 2, zCoord), Blocks.AIR.getDefaultState());
        }

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(1, 2, zCoord), Blocks.WOOL.getDefaultState());
        }

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(2, 2, zCoord), Blocks.AIR.getDefaultState());
        }

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(-1, 3, zCoord), Blocks.AIR.getDefaultState());
        }

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(0, 3, zCoord), Blocks.WOOL.getDefaultState());
        }

        for (zCoord = -3; zCoord < 3; ++zCoord) {
            world.setBlockState(position.add(1, 3, zCoord), Blocks.AIR.getDefaultState());
        }

        return false;
    }
}
