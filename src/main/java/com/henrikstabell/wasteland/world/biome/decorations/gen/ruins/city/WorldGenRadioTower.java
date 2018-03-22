package com.henrikstabell.wasteland.world.biome.decorations.gen.ruins.city;

import com.henrikstabell.wasteland.Wasteland;
import com.henrikstabell.wasteland.config.WastelandConfig;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Map;
import java.util.Random;

/**
 * Created by Hennamann(Ole Henrik Stabell) on 12/03/2018.
 */
public class WorldGenRadioTower extends WorldGenerator {

    protected Random rand = new Random();
    private double chance = 0.05D;

    @Override
    public boolean generate(World world, Random rand, BlockPos position) {
        WorldServer worldserver = (WorldServer) world;
        MinecraftServer minecraftserver = world.getMinecraftServer();
        TemplateManager templatemanager = worldserver.getStructureTemplateManager();
        Template template = templatemanager.getTemplate(minecraftserver, new ResourceLocation(Wasteland.MOD_ID + ":" + "radio_tower"));

        if (template == null) {
            System.out.println("NO STRUCTURE");
            return false;
        }

        if (this.canSpawnStructureAtCoords(template.getSize().getX(), template.getSize().getZ())) {
            IBlockState iblockstate = world.getBlockState(position);
            world.notifyBlockUpdate(position, iblockstate, iblockstate, 3);

            PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
                    .setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk((ChunkPos) null)
                    .setReplacedBlock((Block) null).setIgnoreStructureBlock(false);

            template.getDataBlocks(position, placementsettings);
            template.addBlocksToWorld(world, position.add(0, 1, 0), placementsettings);


            Map<BlockPos, String> map = template.getDataBlocks(position, placementsettings);

            for (Map.Entry<BlockPos, String> entry : map.entrySet()) {
                if ("chest".equals(entry.getValue())) {
                    BlockPos blockpos2 = entry.getKey();
                    world.setBlockState(blockpos2.up(), Blocks.AIR.getDefaultState(), 3);
                    TileEntity tileentity = world.getTileEntity(blockpos2);

                    if (tileentity instanceof TileEntityChest) {
                        for (int l1 = 0; l1 < 2 + rand.nextInt(2); ++l1) {
                            ((TileEntityChest) tileentity).setInventorySlotContents(rand.nextInt(((TileEntityChest) tileentity).getSizeInventory()), WastelandConfig.loot.getLoot(WastelandConfig.loot.seedLoot)[rand.nextInt(WastelandConfig.loot.seedLoot.length)]);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        return this.rand.nextDouble() < this.chance && this.rand.nextInt(80) < Math.max(Math.abs(chunkX), Math.abs(chunkZ));
    }
}
