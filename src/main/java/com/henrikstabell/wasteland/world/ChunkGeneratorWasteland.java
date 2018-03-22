package com.henrikstabell.wasteland.world;

import com.henrikstabell.wasteland.WastelandEventHandler;
import com.henrikstabell.wasteland.config.WastelandConfig;
import com.henrikstabell.wasteland.world.biome.decorations.BiomeDecoratorWasteland;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ChunkGeneratorWasteland implements IChunkGenerator {
    protected static final IBlockState STONE;
    private final Random rand;
    private NoiseGeneratorOctaves minLimitPerlinNoise;
    private NoiseGeneratorOctaves maxLimitPerlinNoise;
    private NoiseGeneratorOctaves mainPerlinNoise;
    private NoiseGeneratorPerlin surfaceNoise;
    public NoiseGeneratorOctaves scaleNoise;
    public NoiseGeneratorOctaves depthNoise;
    public NoiseGeneratorOctaves forestNoise;
    private final World worldObj;
    private final boolean mapFeaturesEnabled;
    private final WorldType terrainType;
    private final double[] heightMap;
    private final float[] biomeWeights;
    private ChunkGeneratorSettings settings;
    private double[] depthBuffer = new double[256];
    private MapGenBase caveGenerator = new MapGenCaves();
    private MapGenStronghold strongholdGenerator = new MapGenStronghold();
    private MapGenVillage villageGenerator = new MapGenVillage();
    private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
    private MapGenBase ravineGenerator = new MapGenRavine();
    private Biome[] biomesForGeneration;
    double[] mainNoiseRegion;
    double[] minLimitRegion;
    double[] maxLimitRegion;
    double[] depthRegion;

    public ChunkGeneratorWasteland(World worldIn, long seed, boolean mapFeaturesEnabledIn, String p_i46668_5_) {
        this.caveGenerator = TerrainGen.getModdedMapGen(this.caveGenerator, EventType.CAVE);
        this.strongholdGenerator = (MapGenStronghold) TerrainGen.getModdedMapGen(this.strongholdGenerator, EventType.STRONGHOLD);
        this.villageGenerator = (MapGenVillage) TerrainGen.getModdedMapGen(this.villageGenerator, EventType.VILLAGE);
        this.mineshaftGenerator = (MapGenMineshaft) TerrainGen.getModdedMapGen(this.mineshaftGenerator, EventType.MINESHAFT);
        this.worldObj = worldIn;
        this.mapFeaturesEnabled = mapFeaturesEnabledIn;
        this.terrainType = worldIn.getWorldInfo().getTerrainType();
        this.rand = new Random(seed);
        this.minLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
        this.maxLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
        this.mainPerlinNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);
        this.scaleNoise = new NoiseGeneratorOctaves(this.rand, 10);
        this.depthNoise = new NoiseGeneratorOctaves(this.rand, 16);
        this.forestNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.heightMap = new double[825];
        this.biomeWeights = new float[25];

        for (int ctx = -2; ctx <= 2; ++ctx) {
            for (int j = -2; j <= 2; ++j) {
                float f = 10.0F / MathHelper.sqrt((float) (ctx * ctx + j * j) + 0.2F);
                this.biomeWeights[ctx + 2 + (j + 2) * 5] = f;
            }
        }

        if (p_i46668_5_ != null) {
            this.settings = Factory.jsonToFactory(p_i46668_5_).build();
            worldIn.setSeaLevel(this.settings.seaLevel);
        }

        ContextOverworld var9 = new ContextOverworld(this.minLimitPerlinNoise, this.maxLimitPerlinNoise, this.mainPerlinNoise, this.surfaceNoise, this.scaleNoise, this.depthNoise, this.forestNoise);
        var9 = TerrainGen.getModdedNoiseGenerators(worldIn, this.rand, var9);
        this.minLimitPerlinNoise = var9.getLPerlin1();
        this.maxLimitPerlinNoise = var9.getLPerlin2();
        this.mainPerlinNoise = var9.getPerlin();
        this.surfaceNoise = var9.getHeight();
        this.scaleNoise = var9.getScale();
        this.depthNoise = var9.getDepth();
        this.forestNoise = var9.getForest();
    }

    public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
        this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
        this.generateHeightmap(x * 4, 0, z * 4);

        for (int i = 0; i < 4; ++i) {
            int j = i * 5;
            int k = (i + 1) * 5;

            for (int l = 0; l < 4; ++l) {
                int i1 = (j + l) * 33;
                int j1 = (j + l + 1) * 33;
                int k1 = (k + l) * 33;
                int l1 = (k + l + 1) * 33;

                for (int i2 = 0; i2 < 32; ++i2) {
                    double d1 = this.heightMap[i1 + i2];
                    double d2 = this.heightMap[j1 + i2];
                    double d3 = this.heightMap[k1 + i2];
                    double d4 = this.heightMap[l1 + i2];
                    double d5 = (this.heightMap[i1 + i2 + 1] - d1) * 0.125D;
                    double d6 = (this.heightMap[j1 + i2 + 1] - d2) * 0.125D;
                    double d7 = (this.heightMap[k1 + i2 + 1] - d3) * 0.125D;
                    double d8 = (this.heightMap[l1 + i2 + 1] - d4) * 0.125D;

                    for (int j2 = 0; j2 < 8; ++j2) {
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.25D;
                        double d13 = (d4 - d2) * 0.25D;

                        for (int k2 = 0; k2 < 4; ++k2) {
                            double d16 = (d11 - d10) * 0.25D;
                            double lvt_45_1_ = d10 - d16;

                            for (int l2 = 0; l2 < 4; ++l2) {
                                if ((lvt_45_1_ += d16) > 0.0D) {
                                    primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, STONE);
                                } else if (i2 * 8 + j2 < this.settings.seaLevel) {
                                    primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, WastelandConfig.worldgen.getSurfaceBlock().getDefaultState());
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }

    }

    public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
        if (!net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, this.worldObj)) return;
        double d0 = 0.03125D;
        this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, (double) (x * 16), (double) (z * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                Biome biome = biomesIn[j + i * 16];
                biome.genTerrainBlocks(this.worldObj, this.rand, primer, x * 16 + i, z * 16 + j, this.depthBuffer[j + i * 16]);
            }
        }
    }

    public Chunk generateChunk(int x, int z) {
        this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.setBlocksInChunk(x, z, chunkprimer);
        this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);

        for (int chunk = 0; chunk < this.biomesForGeneration.length; ++chunk) {
            if (this.biomesForGeneration[chunk] == Biomes.OCEAN || this.biomesForGeneration[chunk] == Biomes.RIVER) {
                this.biomesForGeneration[chunk] = WastelandWorld.apocalypse;
            }
        }

        this.replaceBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration);
        if (this.settings.useCaves) {
            this.caveGenerator.generate(this.worldObj, x, z, chunkprimer);
        }

        if (this.mapFeaturesEnabled) {
            if (this.settings.useMineShafts) {
                this.mineshaftGenerator.generate(this.worldObj, x, z, chunkprimer);
            }

            if (this.settings.useVillages) {
                this.villageGenerator.generate(this.worldObj, x, z, chunkprimer);
            }

            if (this.settings.useStrongholds) {
                this.strongholdGenerator.generate(this.worldObj, x, z, chunkprimer);
            }
            if (this.settings.useRavines) {
                this.ravineGenerator.generate(this.worldObj, x, z, chunkprimer);
            }
        }

        Chunk var7 = new Chunk(this.worldObj, chunkprimer, x, z);
        byte[] abyte = var7.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i) {
            abyte[i] = (byte) Biome.getIdForBiome(this.biomesForGeneration[i]);
        }

        var7.generateSkylightMap();
        return var7;
    }

    private void generateHeightmap(int p_185978_1_, int p_185978_2_, int p_185978_3_) {
        this.depthRegion = this.depthNoise.generateNoiseOctaves(this.depthRegion, p_185978_1_, p_185978_3_, 5, 5, (double) this.settings.depthNoiseScaleX, (double) this.settings.depthNoiseScaleZ, (double) this.settings.depthNoiseScaleExponent);
        float f = this.settings.coordinateScale;
        float f1 = this.settings.heightScale;
        this.mainNoiseRegion = this.mainPerlinNoise.generateNoiseOctaves(this.mainNoiseRegion, p_185978_1_, p_185978_2_, p_185978_3_, 5, 33, 5, (double) (f / this.settings.mainNoiseScaleX), (double) (f1 / this.settings.mainNoiseScaleY), (double) (f / this.settings.mainNoiseScaleZ));
        this.minLimitRegion = this.minLimitPerlinNoise.generateNoiseOctaves(this.minLimitRegion, p_185978_1_, p_185978_2_, p_185978_3_, 5, 33, 5, (double) f, (double) f1, (double) f);
        this.maxLimitRegion = this.maxLimitPerlinNoise.generateNoiseOctaves(this.maxLimitRegion, p_185978_1_, p_185978_2_, p_185978_3_, 5, 33, 5, (double) f, (double) f1, (double) f);
        int i = 0;
        int j = 0;

        for (int k = 0; k < 5; ++k) {
            for (int l = 0; l < 5; ++l) {
                float f2 = 0.0F;
                float f3 = 0.0F;
                float f4 = 0.0F;
                Biome biome = this.biomesForGeneration[k + 2 + (l + 2) * 10];

                for (int d7 = -2; d7 <= 2; ++d7) {
                    for (int k1 = -2; k1 <= 2; ++k1) {
                        Biome d8 = this.biomesForGeneration[k + d7 + 2 + (l + k1 + 2) * 10];
                        float f5 = this.settings.biomeDepthOffSet + d8.getBaseHeight() * this.settings.biomeDepthWeight;
                        float d9 = this.settings.biomeScaleOffset + d8.getHeightVariation() * this.settings.biomeScaleWeight;
                        if (this.terrainType == WorldType.AMPLIFIED && f5 > 0.0F) {
                            f5 = 1.0F + f5 * 2.0F;
                            d9 = 1.0F + d9 * 4.0F;
                        }

                        float f7 = this.biomeWeights[d7 + 2 + (k1 + 2) * 5] / (f5 + 2.0F);
                        if (d8.getBaseHeight() > biome.getBaseHeight()) {
                            f7 /= 2.0F;
                        }

                        f2 += d9 * f7;
                        f3 += f5 * f7;
                        f4 += f7;
                    }
                }

                f2 /= f4;
                f3 /= f4;
                f2 = f2 * 0.9F + 0.1F;
                f3 = (f3 * 4.0F - 1.0F) / 8.0F;
                double var35 = this.depthRegion[j] / 8000.0D;
                if (var35 < 0.0D) {
                    var35 = -var35 * 0.3D;
                }

                var35 = var35 * 3.0D - 2.0D;
                if (var35 < 0.0D) {
                    var35 /= 2.0D;
                    if (var35 < -1.0D) {
                        var35 = -1.0D;
                    }

                    var35 /= 1.4D;
                    var35 /= 2.0D;
                } else {
                    if (var35 > 1.0D) {
                        var35 = 1.0D;
                    }

                    var35 /= 8.0D;
                }

                ++j;
                double var36 = (double) f3;
                double var37 = (double) f2;
                var36 += var35 * 0.2D;
                var36 = var36 * (double) this.settings.baseSize / 8.0D;
                double d0 = (double) this.settings.baseSize + var36 * 4.0D;

                for (int l1 = 0; l1 < 33; ++l1) {
                    double d1 = ((double) l1 - d0) * (double) this.settings.stretchY * 128.0D / 256.0D / var37;
                    if (d1 < 0.0D) {
                        d1 *= 4.0D;
                    }

                    double d2 = this.minLimitRegion[i] / (double) this.settings.lowerLimitScale;
                    double d3 = this.maxLimitRegion[i] / (double) this.settings.upperLimitScale;
                    double d4 = (this.mainNoiseRegion[i] / 10.0D + 1.0D) / 2.0D;
                    double d5 = MathHelper.clamp(d2, d3, d4) - d1;
                    if (l1 > 29) {
                        double d6 = (double) ((float) (l1 - 29) / 3.0F);
                        d5 = d5 * (1.0D - d6) + -10.0D * d6;
                    }

                    this.heightMap[i] = d5;
                    ++i;
                }
            }
        }

    }

    public void populate(int x, int z) {
        BlockFalling.fallInstantly = true;
        int i = x * 16;
        int j = z * 16;
        BlockPos blockpos = new BlockPos(i, 0, j);
        Biome biome = this.worldObj.getBiome(blockpos.add(16, 0, 16));
        this.rand.setSeed(this.worldObj.getSeed());
        long k = this.rand.nextLong() / 2L * 2L + 1L;
        long l = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long) x * k + (long) z * l ^ this.worldObj.getSeed());
        boolean flag = false;
        ChunkPos chunkpos = new ChunkPos(x, z);
        ForgeEventFactory.onChunkPopulate(true, this, this.worldObj, this.rand, x, z, flag);
        if (this.mapFeaturesEnabled) {
            if (this.settings.useMineShafts) {
                this.mineshaftGenerator.generateStructure(this.worldObj, this.rand, chunkpos);
            }

            if (this.settings.useVillages) {
                flag = this.villageGenerator.generateStructure(this.worldObj, this.rand, chunkpos);
            }

            if (this.settings.useStrongholds) {
                this.strongholdGenerator.generateStructure(this.worldObj, this.rand, chunkpos);
            }
        }

        if (!WastelandEventHandler.bunkerSpawned && WastelandEventHandler.spawnLocation != null) {
            System.out.println("WAODIJAWODIJ");
            BiomeDecoratorWasteland.spawnBunker(this.worldObj);
            this.worldObj.setSpawnPoint(WastelandEventHandler.spawnLocation);
            WastelandEventHandler.bunkerSpawned = true;
        }

        if (this.settings.useDungeons && TerrainGen.populate(this, this.worldObj, this.rand, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.DUNGEON)) {
            for (int j2 = 0; j2 < this.settings.dungeonChance; ++j2) {
                int i3 = this.rand.nextInt(16) + 8;
                int l3 = this.rand.nextInt(256);
                int l1 = this.rand.nextInt(16) + 8;
                (new WorldGenDungeons()).generate(this.worldObj, this.rand, blockpos.add(i3, l3, l1));
            }
        }

        biome.decorate(this.worldObj, this.rand, new BlockPos(i, 0, j));
        if (TerrainGen.populate(this, this.worldObj, this.rand, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ANIMALS)) {
            WorldEntitySpawner.performWorldGenSpawning(this.worldObj, biome, i + 8, j + 8, 16, 16, this.rand);
        }

        blockpos = blockpos.add(8, 0, 8);
        ForgeEventFactory.onChunkPopulate(false, this, this.worldObj, this.rand, x, z, flag);
        BlockFalling.fallInstantly = false;
    }

    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return WastelandConfig.worldgen.shouldSpawnStructures;
    }

    public List getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        Biome biome = this.worldObj.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    public void recreateStructures(Chunk chunkIn, int x, int z) {
        if (this.mapFeaturesEnabled) {
            if (this.settings.useMineShafts) {
                this.mineshaftGenerator.generate(this.worldObj, x, z, null);
            }

            if (this.settings.useVillages) {
                this.villageGenerator.generate(this.worldObj, x, z, null);
            }

            if (this.settings.useStrongholds) {
                this.strongholdGenerator.generate(this.worldObj, x, z, null);
            }
        }

    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }

    static {
        STONE = Blocks.STONE.getDefaultState();
    }
}
