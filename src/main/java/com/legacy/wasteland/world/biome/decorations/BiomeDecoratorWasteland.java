package com.legacy.wasteland.world.biome.decorations;

import com.legacy.wasteland.Wasteland;
import com.legacy.wasteland.WastelandEventHandler;
import com.legacy.wasteland.config.WastelandConfig;
import com.legacy.wasteland.world.biome.BiomeGenApocalypse;
import com.legacy.wasteland.world.biome.BiomeGenDesert;
import com.legacy.wasteland.world.biome.BiomeGenForest;
import com.legacy.wasteland.world.biome.BiomeGenMountains;
import com.legacy.wasteland.world.biome.decorations.gen.WorldGenOasis;
import com.legacy.wasteland.world.biome.decorations.gen.WorldGenRandomFire;
import com.legacy.wasteland.world.biome.decorations.gen.WorldGenRandomRubble;
import com.legacy.wasteland.world.biome.decorations.gen.WorldGenWastelandBigTree;
import com.legacy.wasteland.world.biome.decorations.gen.ruins.WorldGenCivilizationRuins;
import com.legacy.wasteland.world.biome.decorations.gen.ruins.WorldGenRuinedRuins;
import com.legacy.wasteland.world.biome.decorations.gen.ruins.WorldGenSurvivalTent;
import com.legacy.wasteland.world.biome.decorations.gen.ruins.WorldGenTreeHouse;
import java.util.Random;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockBed.EnumPartType;
import net.minecraft.block.BlockStone.EnumType;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;

public class BiomeDecoratorWasteland extends BiomeDecorator {
   public BlockPos position;
   public boolean decorating;
   public int firePerChunk;
   public int rubblePerChunk;
   public int deadTreePerChunk;
   public WorldGenerator randomFireGen = new WorldGenRandomFire();
   public WorldGenerator randomRubbleGen = new WorldGenRandomRubble();
   public WorldGenerator deadTreeGen = new WorldGenWastelandBigTree(true);
   public WorldGenerator oasis = new WorldGenOasis();
   public WorldGenerator treeHouse = new WorldGenTreeHouse();
   public WorldGenerator tent = new WorldGenSurvivalTent();
   public WorldGenerator temple = new WorldGenRuinedRuins();
   public WorldGenerator house = new WorldGenCivilizationRuins();

   public BiomeDecoratorWasteland() {
      this.firePerChunk = WastelandConfig.worldgen.randomFirePerChunk;
      this.flowersPerChunk = -999;
      this.grassPerChunk = -999;
      this.deadBushPerChunk = 5;
      this.generateFalls = false;
      this.treesPerChunk = -999;
   }

   public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
      if(this.decorating) {
         throw new RuntimeException("Already decorating");
      } else {
         this.position = pos;
         this.chunkPos = pos;
         this.deadBushPerChunk = 5;
         this.chunkProviderSettings = Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
         this.dirtGen = new WorldGenMinable(Blocks.DIRT.getDefaultState(), this.chunkProviderSettings.dirtSize);
         this.gravelGen = new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), this.chunkProviderSettings.gravelSize);
         this.graniteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, EnumType.GRANITE), this.chunkProviderSettings.graniteSize);
         this.dioriteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, EnumType.DIORITE), this.chunkProviderSettings.dioriteSize);
         this.andesiteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, EnumType.ANDESITE), this.chunkProviderSettings.andesiteSize);
         this.coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), this.chunkProviderSettings.coalSize);
         this.ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), this.chunkProviderSettings.ironSize);
         this.goldGen = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), this.chunkProviderSettings.goldSize);
         this.redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), this.chunkProviderSettings.redstoneSize);
         this.diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), this.chunkProviderSettings.diamondSize);
         this.lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), this.chunkProviderSettings.lapisSize);
         this.genDecorations(biome, worldIn, random);
      }
   }

   protected void genDecorations(Biome biomeGenBaseIn, World world, Random random) {
      if(biomeGenBaseIn instanceof BiomeGenApocalypse) {
         this.decorateWasteland(world, random);
      } else if(biomeGenBaseIn instanceof BiomeGenMountains) {
         this.decorateMountains(world, random);
      } else if(biomeGenBaseIn instanceof BiomeGenForest) {
         this.decorateForest(world, random);
      } else if(biomeGenBaseIn instanceof BiomeGenDesert) {
         this.decorateDesert(world, random);
      }

      if(TerrainGen.decorate(world, random, this.position, EventType.DEAD_BUSH)) {
         for(int size = 0; size < this.deadBushPerChunk; ++size) {
            int x = random.nextInt(16) + 8;
            int z = random.nextInt(16) + 8;
            int y = world.getHeight(this.position.add(x, 0, z)).getY() * 2;
            if(y > 0) {
               int randomY = random.nextInt(y);
               (new WorldGenDeadBush()).generate(world, random, this.position.add(x, randomY, z));
            }
         }
      }

      if(random.nextInt(WastelandConfig.worldgen.oasisRarity) == 0 && WastelandConfig.biomes.oasisEnabled) {
         this.oasis.generate(world, random, world.getHeight(this.position.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8)));
      }

      if(random.nextInt(WastelandConfig.worldgen.wastelandTreeSpawnRate * 15) == 0) {
         this.deadTreeGen.generate(world, random, world.getHeight(this.position.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8)));
      }

      if(random.nextInt(WastelandConfig.worldgen.wastelandRuinRarirty) == 0) {
         this.randomRubbleGen.generate(world, random, world.getHeight(this.position.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8)));
      }

   }

   private void decorateDesert(World world, Random random) {
      for(int j5 = 0; j5 < 20; ++j5) {
         int l9 = random.nextInt(16) + 8;
         int k13 = random.nextInt(16) + 8;
         int l16 = world.getHeight(this.position.add(l9, 0, k13)).getY() * 2;
         if(l16 > 0) {
            int j19 = random.nextInt(l16);
            this.cactusGen.generate(world, random, this.position.add(l9, j19, k13));
         }
      }

   }

   private void decorateWasteland(World world, Random random) {
      for(int size = 0; size < this.firePerChunk; ++size) {
         this.randomFireGen.generate(world, random, world.getHeight(this.position.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8)));
      }

      if(random.nextInt(WastelandConfig.worldgen.wastelandRuinRarirty) == 0) {
         this.house.generate(world, random, world.getHeight(this.position.add(random.nextInt(16), 0, random.nextInt(16))));
      }

   }

   private void decorateMountains(World world, Random random) {
      if(random.nextInt(WastelandConfig.worldgen.mountainRuinRarity * 2) == 0) {
         this.temple.generate(world, random, world.getHeight(this.position.add(random.nextInt(16), 0, random.nextInt(16))));
      }

   }

   private void decorateForest(World world, Random rand) {
      if(rand.nextInt(WastelandConfig.worldgen.forestRuinRarity * 3) == 0) {
         this.tent.generate(world, rand, world.getHeight(this.position.add(rand.nextInt(16), 0, rand.nextInt(16))));
      }

      if(rand.nextInt(WastelandConfig.worldgen.forestRuinRarity * 2) == 0) {
         this.treeHouse.generate(world, rand, world.getHeight(this.position.add(rand.nextInt(16), 0, rand.nextInt(16))));
      }

   }

   public static void spawnBunker(World world) {
      BlockPos pos = WastelandEventHandler.spawnLocation.add(-2, 0, -2);
      int count = 0;
      int worldHeight = world.getHeight(pos).getY();
      Random random = new Random();
      System.out.println(pos.toString());

      int i;
      for(i = 0; i < 5; ++i) {
         for(int currentPos = 0; currentPos < 7; ++currentPos) {
            for(int i1 = 0; i1 < 7; ++i1) {
               if(count == 61) {
                  world.setBlockState(pos.add(i1, i, currentPos), Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.SOUTH));
                  TileEntityChest var11 = (TileEntityChest)world.getTileEntity(pos.add(i1, i, currentPos));

                  for(int treasureSize = 0; treasureSize < 3 + random.nextInt(4); ++treasureSize) {
                     var11.setInventorySlotContents(random.nextInt(var11.getSizeInventory()), WastelandConfig.loot.getLoot(WastelandConfig.loot.startLoot)[random.nextInt(WastelandConfig.loot.startLoot.length)]);
                  }
               } else if(count != 78 && count != 85) {
                  if(count == 136) {
                     world.setBlockState(pos.add(i1, i, currentPos), Blocks.LEVER.getDefaultState());
                  } else if(count == 143) {
                     world.setBlockState(pos.add(i1, i, currentPos), Blocks.REDSTONE_LAMP.getDefaultState());
                  } else if(i != 4 && i != 0 && currentPos != 0 && currentPos != 6 && i1 != 0 && i1 != 6) {
                     world.setBlockToAir(pos.add(i1, i, currentPos));
                  } else {
                     world.setBlockState(pos.add(i1, i, currentPos), random.nextInt(4) == 0?Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, net.minecraft.block.BlockStoneBrick.EnumType.MOSSY):Blocks.STONEBRICK.getDefaultState());
                  }
               } else if (WastelandConfig.worldgen.shouldSpawnBunkerBed) {
                  EnumPartType side = count == 85?EnumPartType.HEAD:EnumPartType.FOOT;
                  world.setBlockState(pos.add(i1, i, currentPos), Blocks.BED.getDefaultState().withProperty(BlockBed.PART, side).withProperty(BlockBed.FACING, EnumFacing.SOUTH));
               }

               ++count;
            }
         }
      }

      world.setBlockState(pos.add(2, 1, 0), Blocks.AIR.getDefaultState());
      world.setBlockState(pos.add(2, 2, 0), Blocks.AIR.getDefaultState());

      for(i = 1; i < 255; ++i) {
         BlockPos var10 = pos.add(2, i, -1);
         if(!world.isAirBlock(var10)) {
            world.setBlockState(var10, Blocks.LADDER.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
         }
      }

      world.setBlockState(pos.add(2, worldHeight, -1), Blocks.AIR.getDefaultState());
      world.setBlockState(pos.add(2, worldHeight + 1, -1), Blocks.AIR.getDefaultState());
      world.setBlockState(pos.add(2, worldHeight + 2, -1), Blocks.AIR.getDefaultState());
   }
}
