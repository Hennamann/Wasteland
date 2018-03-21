package com.legacy.wasteland.world.biome.decorations.gen.ruins;

import com.legacy.wasteland.config.WastelandConfig;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenCivilizationRuins extends WorldGenerator {
    public boolean generate(World world, Random random, BlockPos pos) {
        BlockPos upPos = pos.up();
        int j1 = 0;
        int k1 = 0;
        boolean solidBottom = world.getBlockState(pos).getMaterial().isSolid() || world.getBlockState(pos.down()).getMaterial().isSolid();
        boolean solidSides = world.getBlockState(pos.east(6)).getMaterial().isSolid() && world.getBlockState(pos.south(6)).getMaterial().isSolid() && world.getBlockState(pos.add(6, 0, 6)).getMaterial().isSolid();
        boolean hasRoom = world.getBlockState(upPos).getMaterial().isSolid() && world.getBlockState(upPos.east(6)).getMaterial().isSolid() && world.getBlockState(upPos.south(6)).getMaterial().isSolid() && world.getBlockState(upPos.add(6, 0, 6)).getMaterial().isSolid();
        if (solidBottom && solidSides && !hasRoom) {
            int randomWallChance = random.nextInt(3);
            int randomFloorChance = random.nextInt(2);
            IBlockState wallState;
            if (randomWallChance == 0) {
                wallState = Blocks.COBBLESTONE.getDefaultState();
            } else if (randomWallChance == 1) {
                wallState = Blocks.MOSSY_COBBLESTONE.getDefaultState();
            } else if (randomWallChance == 2) {
                wallState = Blocks.PLANKS.getDefaultState();
            } else {
                wallState = Blocks.MOSSY_COBBLESTONE.getDefaultState();
            }

            IBlockState floorState;
            if (randomFloorChance == 0) {
                floorState = Blocks.COBBLESTONE.getDefaultState();
            } else if (randomFloorChance == 1) {
                floorState = Blocks.MOSSY_COBBLESTONE.getDefaultState();
            } else {
                floorState = Blocks.PLANKS.getDefaultState();
            }

            int i3;
            int k3;
            for (i3 = 1; i3 <= 3; ++i3) {
                for (k3 = 0; k3 < 49; ++k3) {
                    world.setBlockState(pos.add(k1, i3, j1), Blocks.AIR.getDefaultState());
                    ++k1;
                    if (k1 == 7) {
                        ++j1;
                        k1 = 0;
                    }
                }
            }

            i3 = 0;
            k3 = 0;

            int i4;
            for (i4 = 0; i4 < 49; ++i4) {
                world.setBlockState(pos.add(k3, 0, i3), floorState);
                ++k3;
                if (k3 == 7) {
                    ++i3;
                    k3 = 0;
                }
            }

            i4 = 0;
            int j4 = 0;
            int k4 = random.nextInt(2);
            int k5;
            int k6;
            int j8;
            int i9;
            int l9;
            if (k4 == 0) {
                for (k5 = 1; k5 < 4; ++k5) {
                    for (k6 = 0; k6 < 25; ++k6) {
                        world.setBlockState(pos.add(i4 + 1, k5, j4 + 1), Blocks.AIR.getDefaultState());
                        ++i4;
                        if (i4 == 5) {
                            ++j4;
                            i4 = 0;
                        }
                    }

                    i4 = 0;
                    j4 = 0;
                }

                world.setBlockState(pos.add(3, -3, 3), Blocks.TORCH.getDefaultState());
                world.setBlockState(pos.add(2, -3, 3), Blocks.STONE_PRESSURE_PLATE.getDefaultState());
                world.setBlockState(pos.add(2, -4, 3), Blocks.STONE.getDefaultState());
                world.setBlockState(pos.add(2, -5, 3), Blocks.TNT.getDefaultState());
                k5 = random.nextInt(2);
                if (k5 == 0) {
                    for (k6 = 0; k6 < 25; ++k6) {
                        world.setBlockState(pos.add(i4 + 1, -3, j4 + 1), Blocks.WATER.getDefaultState());
                        ++i4;
                        if (i4 == 5) {
                            ++j4;
                            i4 = 0;
                        }
                    }
                }

                k6 = random.nextInt(26);
                j8 = k6 / 5;
                i9 = k6 % 5;
                world.setBlockState(pos.add(j8, -3, i9), Blocks.CHEST.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
                TileEntityChest k9 = (TileEntityChest) world.getTileEntity(pos.add(j8, -3, i9));

                for (l9 = 0; l9 < 2 + random.nextInt(2); ++l9) {
                    k9.setInventorySlotContents(random.nextInt(k9.getSizeInventory()), WastelandConfig.loot.getLoot(WastelandConfig.loot.seedLoot)[random.nextInt(WastelandConfig.loot.seedLoot.length)]);
                }
            }

            boolean var33 = false;
            boolean var34 = false;

            int var35;
            for (k5 = 0; k5 < 3; ++k5) {
                k6 = 0;
                j8 = 0;

                for (i9 = 0; i9 < 28; ++i9) {
                    var35 = random.nextInt(k5 + 1);
                    Material var36;
                    if (k6 == 0) {
                        var36 = world.getBlockState(pos.add(0, k5, j8)).getMaterial();
                        if (var36.isSolid() && var35 == 0) {
                            if (k5 == 1) {
                                if (random.nextInt(2) == 0) {
                                    world.setBlockState(pos.add(0, 1 + k5, j8), Blocks.GLASS.getDefaultState());
                                } else {
                                    world.setBlockState(pos.add(0, 1 + k5, j8), wallState);
                                }
                            } else {
                                world.setBlockState(pos.add(0, 1 + k5, j8), wallState);
                            }
                        }

                        ++j8;
                    }

                    if (k6 == 1) {
                        var36 = world.getBlockState(pos.add(6, k5, j8)).getMaterial();
                        if (var36.isSolid() && var35 == 0) {
                            if (k5 == 1) {
                                if (random.nextInt(2) == 0) {
                                    world.setBlockState(pos.add(6, 1 + k5, j8), Blocks.GLASS.getDefaultState());
                                } else {
                                    world.setBlockState(pos.add(6, 1 + k5, j8), wallState);
                                }
                            } else {
                                world.setBlockState(pos.add(6, 1 + k5, j8), wallState);
                            }
                        }

                        ++j8;
                    }

                    if (k6 == 2) {
                        var36 = world.getBlockState(pos.add(j8, k5, 0)).getMaterial();
                        if (var36.isSolid() && var35 == 0) {
                            if (k5 == 1) {
                                if (random.nextInt(2) == 0) {
                                    world.setBlockState(pos.add(j8, 1 + k5, 0), Blocks.GLASS.getDefaultState());
                                } else {
                                    world.setBlockState(pos.add(j8, 1 + k5, 0), wallState);
                                }
                            } else {
                                world.setBlockState(pos.add(7, 1 + k5, 0), wallState);
                            }
                        }

                        ++j8;
                    }

                    if (k6 == 3) {
                        var36 = world.getBlockState(pos.add(j8, k5, 6)).getMaterial();
                        if (var36.isSolid() && var35 == 0) {
                            if (k5 == 1) {
                                if (random.nextInt(2) == 0) {
                                    world.setBlockState(pos.add(j8, 1 + k5, 6), Blocks.GLASS.getDefaultState());
                                } else {
                                    world.setBlockState(pos.add(j8, 1 + k5, 6), wallState);
                                }
                            } else {
                                world.setBlockState(pos.add(j8, 1 + k5, 6), wallState);
                            }
                        }

                        ++j8;
                    }

                    if (j8 == 7) {
                        ++k6;
                        j8 = 0;
                    }
                }
            }

            k5 = random.nextInt(3);
            k6 = random.nextInt(3);
            if (k5 == 0) {
                world.setBlockState(pos.add(0, 1, 2 + k6), Blocks.AIR.getDefaultState());
                world.setBlockState(pos.add(0, 2, 2 + k6), Blocks.AIR.getDefaultState());
            }

            if (k5 == 1) {
                world.setBlockState(pos.add(6, 1, 2 + k6), Blocks.AIR.getDefaultState());
                world.setBlockState(pos.add(6, 2, 2 + k6), Blocks.AIR.getDefaultState());
            }

            if (k5 == 2) {
                world.setBlockState(pos.add(2 + k6, 1, 0), Blocks.AIR.getDefaultState());
                world.setBlockState(pos.add(2 + k6, 2, 0), Blocks.AIR.getDefaultState());
            }

            if (k5 == 3) {
                world.setBlockState(pos.add(2 + k6, 1, 6), Blocks.AIR.getDefaultState());
                world.setBlockState(pos.add(2 + k6, 2, 6), Blocks.AIR.getDefaultState());
            }

            j8 = random.nextInt(2);
            i9 = random.nextInt(5) + 1;
            var35 = 0;
            l9 = 0;
            if (j8 == 0) {
                var35 = i9;
                l9 = 1;
            }

            if (j8 == 1) {
                var35 = 1;
                l9 = i9;
            }

            if (j8 == 2) {
                var35 = 1;
                l9 = i9;
            }

            world.setBlockState(pos.add(var35, 1, l9), Blocks.CHEST.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
            TileEntityChest chest1 = (TileEntityChest) world.getTileEntity(pos.add(var35, 1, l9));
            String[] usedLootTable = random.nextInt(3) == 0 ? WastelandConfig.loot.ruinRareLoot : WastelandConfig.loot.ruinEasyLoot;

            int i10;
            for (i10 = 0; i10 < 2 + random.nextInt(3); ++i10) {
                chest1.setInventorySlotContents(random.nextInt(chest1.getSizeInventory()), WastelandConfig.loot.getLoot(usedLootTable)[random.nextInt(usedLootTable.length)]);
            }

            i10 = random.nextInt(2);
            if (i10 == 0) {
                int j10 = 0;
                int k10 = 0;

                int k11;
                for (k11 = 0; k11 < 12; ++k11) {
                    if (k10 == 0) {
                        world.setBlockState(pos.add(2, 1, -1 - j10), Blocks.OAK_FENCE.getDefaultState());
                    }

                    if (k10 == 1) {
                        world.setBlockState(pos.add(3 + j10, 1, -4), Blocks.OAK_FENCE.getDefaultState());
                    }

                    if (k10 == 2) {
                        world.setBlockState(pos.add(6, 1, -1 - j10), Blocks.OAK_FENCE.getDefaultState());
                    }

                    ++j10;
                    if (j10 == 4) {
                        ++k10;
                        j10 = 0;
                    }
                }

                int chest2;
                int treasureSize;
                for (k11 = 0; k11 < 20; ++k11) {
                    chest2 = k11 / 5;
                    treasureSize = k11 % 5;
                    world.setBlockState(pos.add(treasureSize + 2, 0, -chest2 - 1), Blocks.DIRT.getDefaultState());
                }

                for (k11 = 0; k11 < 9; ++k11) {
                    chest2 = k11 / 3;
                    treasureSize = k11 % 3;
                    world.setBlockState(pos.add(treasureSize + 3, 1, -chest2 - 1), Blocks.AIR.getDefaultState());
                }

                k11 = random.nextInt(2);
                if (k11 == 0) {
                    world.setBlockState(pos.add(3, 1, -3), Blocks.CHEST.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
                    TileEntityChest var37 = (TileEntityChest) world.getTileEntity(pos.add(3, 1, -3));

                    for (treasureSize = 0; treasureSize < 2 + random.nextInt(2); ++treasureSize) {
                        var37.setInventorySlotContents(random.nextInt(var37.getSizeInventory()), WastelandConfig.loot.getLoot(WastelandConfig.loot.seedLoot)[random.nextInt(WastelandConfig.loot.seedLoot.length)]);
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
