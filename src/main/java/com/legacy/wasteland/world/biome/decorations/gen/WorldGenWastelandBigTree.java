package com.legacy.wasteland.world.biome.decorations.gen;

import com.legacy.wasteland.config.WastelandConfig;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;

import java.util.Random;

public class WorldGenWastelandBigTree extends WorldGenBigTree {
    static final byte[] otherCoordPairs = new byte[]{(byte) 2, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 1};
    Random rand = new Random();
    private World worldObj;
    private BlockPos basePos;
    private int heightLimit;
    private int height;
    private double heightAttenuation;
    private double branchSlope;
    private double scaleWidth;
    private double leafDensity;
    private int leafDistanceLimit;
    private int[][] leafNodes;

    public WorldGenWastelandBigTree(boolean par1) {
        super(par1);
        this.basePos = BlockPos.ORIGIN;
        this.heightLimit = 0;
        this.heightAttenuation = 0.45D;
        this.branchSlope = 0.2D;
        this.scaleWidth = 1.0D;
        this.leafDensity = 1.0D;
        this.leafDistanceLimit = 4;
    }

    void generateLeafNodeList() {
        this.height = (int) ((double) this.heightLimit * this.heightAttenuation);
        if (this.height >= this.heightLimit) {
            this.height = this.heightLimit - 1;
        }

        int var1 = (int) (1.382D + Math.pow(this.leafDensity * (double) this.heightLimit / 13.0D, 2.0D));
        if (var1 < 1) {
            var1 = 1;
        }

        int[][] var2 = new int[var1 * this.heightLimit][4];
        int var3 = this.basePos.getY() + this.heightLimit - this.leafDistanceLimit;
        int var4 = 1;
        int var5 = this.basePos.getY() + this.height;
        int var6 = var3 - this.basePos.getY();
        var2[0][0] = this.basePos.getX();
        var2[0][1] = var3;
        var2[0][2] = this.basePos.getZ();
        var2[0][3] = var5;
        --var3;

        while (true) {
            while (var6 >= 0) {
                int var7 = 0;
                float var8 = this.layerSize(var6);
                if (var8 < 0.0F) {
                    --var3;
                    --var6;
                } else {
                    for (double var9 = 0.5D; var7 < var1; ++var7) {
                        double var11 = this.scaleWidth * (double) var8 * ((double) this.rand.nextFloat() + 0.328D);
                        double var13 = (double) this.rand.nextFloat() * 2.0D * 3.141592653589793D;
                        int var15 = MathHelper.floor(var11 * Math.sin(var13) + (double) this.basePos.getX() + var9);
                        int var16 = MathHelper.floor(var11 * Math.cos(var13) + (double) this.basePos.getZ() + var9);
                        int[] var17 = new int[]{var15, var3, var16};
                        int[] var18 = new int[]{var15, var3 + this.leafDistanceLimit, var16};
                        if (this.checkBlockLine(var17, var18) == -1) {
                            int[] PositionData = new int[]{this.basePos.getX(), this.basePos.getY(), this.basePos.getZ()};
                            double var20 = Math.sqrt(Math.pow((double) Math.abs(this.basePos.getX() - var17[0]), 2.0D) + Math.pow((double) Math.abs(this.basePos.getZ() - var17[2]), 2.0D));
                            double var22 = var20 * this.branchSlope;
                            if ((double) var17[1] - var22 > (double) var5) {
                                PositionData[1] = var5;
                            } else {
                                PositionData[1] = (int) ((double) var17[1] - var22);
                            }

                            if (this.checkBlockLine(PositionData, var17) == -1) {
                                var2[var4][0] = var15;
                                var2[var4][1] = var3;
                                var2[var4][2] = var16;
                                var2[var4][3] = PositionData[1];
                                ++var4;
                            }
                        }
                    }

                    --var3;
                    --var6;
                }
            }

            this.leafNodes = new int[var4][4];
            System.arraycopy(var2, 0, this.leafNodes, 0, var4);
            return;
        }
    }

    void genTreeLayer(int par1, int par2, int par3, float par4, byte par5, Block log) {
        int var7 = (int) ((double) par4 + 0.618D);
        byte var8 = otherCoordPairs[par5];
        byte var9 = otherCoordPairs[par5 + 3];
        int[] var10 = new int[]{par1, par2, par3};
        int[] positionData = new int[]{0, 0, 0};
        int var12 = -var7;
        int var13 = -var7;

        label35:
        for (positionData[par5] = var10[par5]; var12 <= var7; ++var12) {
            positionData[var8] = var10[var8] + var12;
            var13 = -var7;

            while (true) {
                while (true) {
                    if (var13 > var7) {
                        continue label35;
                    }

                    double var15 = Math.pow((double) Math.abs(var12) + 0.5D, 2.0D) + Math.pow((double) Math.abs(var13) + 0.5D, 2.0D);
                    if (var15 > (double) (par4 * par4)) {
                        ++var13;
                    } else {
                        positionData[var9] = var10[var9] + var13;
                        BlockPos pos = new BlockPos(positionData[0], positionData[1], positionData[2]);
                        Block block = this.worldObj.getBlockState(pos).getBlock();
                        if (!this.worldObj.isAirBlock(pos) && block != Blocks.LEAVES && block != Blocks.LEAVES2) {
                            ++var13;
                        } else {
                            this.setBlockAndNotifyAdequately(this.worldObj, pos, log.getDefaultState());
                            ++var13;
                        }
                    }
                }
            }
        }

    }

    float layerSize(int par1) {
        if ((double) par1 < (double) this.heightLimit * 0.3D) {
            return -1.618F;
        } else {
            float var2 = (float) this.heightLimit / 2.0F;
            float var3 = (float) this.heightLimit / 2.0F - (float) par1;
            float var4;
            if (var3 == 0.0F) {
                var4 = var2;
            } else if (Math.abs(var3) >= var2) {
                var4 = 0.0F;
            } else {
                var4 = (float) Math.sqrt(Math.pow((double) Math.abs(var2), 2.0D) - Math.pow((double) Math.abs(var3), 2.0D));
            }

            var4 *= 0.5F;
            return var4;
        }
    }

    void placeBlockLine(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger, Block block) {
        int[] var4 = new int[]{0, 0, 0};
        byte var5 = 0;

        byte var6;
        for (var6 = 0; var5 < 3; ++var5) {
            var4[var5] = par2ArrayOfInteger[var5] - par1ArrayOfInteger[var5];
            if (Math.abs(var4[var5]) > Math.abs(var4[var6])) {
                var6 = var5;
            }
        }

        if (var4[var6] != 0) {
            byte var7 = otherCoordPairs[var6];
            byte var8 = otherCoordPairs[var6 + 3];
            byte var9;
            if (var4[var6] > 0) {
                var9 = 1;
            } else {
                var9 = -1;
            }

            double var10 = (double) var4[var7] / (double) var4[var6];
            double var12 = (double) var4[var8] / (double) var4[var6];
            int[] positionData = new int[]{0, 0, 0};
            int positionModifer = 0;

            for (int baseModifier = var4[var6] + var9; positionModifer != baseModifier; positionModifer += var9) {
                positionData[var6] = MathHelper.floor((double) (par1ArrayOfInteger[var6] + positionModifer) + 0.5D);
                positionData[var7] = MathHelper.floor((double) par1ArrayOfInteger[var7] + (double) positionModifer * var10 + 0.5D);
                positionData[var8] = MathHelper.floor((double) par1ArrayOfInteger[var8] + (double) positionModifer * var12 + 0.5D);
                this.setBlockAndNotifyAdequately(this.worldObj, new BlockPos(positionData[0], positionData[1], positionData[2]), block.getDefaultState());
            }
        }

    }

    public boolean leafNodeNeedsBase(int par1) {
        return (double) par1 >= (double) this.heightLimit * 0.2D;
    }

    public void generateTrunk() {
        int var1 = this.basePos.getX();
        int var2 = this.basePos.getY();
        int var3 = this.basePos.getY() + this.height;
        int var4 = this.basePos.getZ();
        int[] var5 = new int[]{var1, var2, var4};
        int[] var6 = new int[]{var1, var3, var4};
        this.placeBlockLine(var5, var6, Blocks.LOG);
    }

    public void generateLeafNodeBases() {
        int var1 = 0;
        int var2 = this.leafNodes.length;

        for (int[] var3 = new int[]{this.basePos.getX(), this.basePos.getY(), this.basePos.getZ()}; var1 < var2; ++var1) {
            int[] var4 = this.leafNodes[var1];
            int[] var5 = new int[]{var4[0], var4[1], var4[2]};
            var3[1] = var4[3];
            int var6 = var3[1] - this.basePos.getY();
            if (this.leafNodeNeedsBase(var6)) {
                this.placeBlockLine(var3, var5, Blocks.LOG);
            }
        }

    }

    public int checkBlockLine(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger) {
        int[] var3 = new int[]{0, 0, 0};
        byte var4 = 0;

        byte var5;
        for (var5 = 0; var4 < 3; ++var4) {
            var3[var4] = par2ArrayOfInteger[var4] - par1ArrayOfInteger[var4];
            if (Math.abs(var3[var4]) > Math.abs(var3[var5])) {
                var5 = var4;
            }
        }

        if (var3[var5] == 0) {
            return -1;
        } else {
            byte var6 = otherCoordPairs[var5];
            byte var7 = otherCoordPairs[var5 + 3];
            byte var8;
            if (var3[var5] > 0) {
                var8 = 1;
            } else {
                var8 = -1;
            }

            double var9 = (double) var3[var6] / (double) var3[var5];
            double var11 = (double) var3[var7] / (double) var3[var5];
            int[] positionData = new int[]{0, 0, 0};
            int var14 = 0;

            int var15;
            for (var15 = var3[var5] + var8; var14 != var15; var14 += var8) {
                positionData[var5] = par1ArrayOfInteger[var5] + var14;
                positionData[var6] = MathHelper.floor((double) par1ArrayOfInteger[var6] + (double) var14 * var9);
                positionData[var7] = MathHelper.floor((double) par1ArrayOfInteger[var7] + (double) var14 * var11);
                BlockPos pos = new BlockPos(positionData[0], positionData[1], positionData[2]);
                Block block = this.worldObj.getBlockState(pos).getBlock();
                if (!this.worldObj.isAirBlock(pos) && block != Blocks.LEAVES && block != Blocks.LEAVES2) {
                    break;
                }
            }

            return var14 == var15 ? -1 : Math.abs(var14);
        }
    }

    private boolean validBigTreeLocation() {
        return this.worldObj.getBlockState(this.basePos.down()).getBlock() == WastelandConfig.worldgen.getSurfaceBlock();
    }

    public boolean generate(World world, Random random, BlockPos pos) {
        this.worldObj = world;
        this.rand.setSeed(random.nextLong());
        this.basePos = pos;
        if (this.heightLimit == 0) {
            this.heightLimit = 12 + random.nextInt(5);
        }

        if (this.validBigTreeLocation()) {
            this.generateLeafNodeList();
            this.generateTrunk();
            this.generateLeafNodeBases();
            return true;
        } else {
            return false;
        }
    }
}
