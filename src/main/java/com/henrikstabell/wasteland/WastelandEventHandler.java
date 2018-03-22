package com.henrikstabell.wasteland;

import com.henrikstabell.wasteland.config.WastelandConfig;
import com.henrikstabell.wasteland.world.WastelandWorld;
import com.henrikstabell.wasteland.world.biome.decorations.BiomeDecoratorWasteland;
import com.henrikstabell.wasteland.world.util.WastelandWorldData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class WastelandEventHandler {
    public WastelandWorldData worldFileCache;
    public static BlockPos spawnLocation;
    public static boolean spawnSet;
    public static boolean bunkerSpawned;

    @SubscribeEvent
    public void loadData(Load event) {
        if (event.getWorld().getWorldType() == WastelandWorld.worldtype_wasteland) {
            this.worldFileCache = new WastelandWorldData("saves/" + FMLCommonHandler.instance().getMinecraftServerInstance().getFolderName() + "/data/wasteland_cache.dat");
            if (!this.worldFileCache.checkIfExists()) {
                bunkerSpawned = false;
                spawnSet = false;
                this.worldFileCache.createFile();
            } else if (this.worldFileCache.loadSpawnLoc().getY() != 0) {
                spawnLocation = this.worldFileCache.loadSpawnLoc();
            }
        }
    }

    @SubscribeEvent
    public void onRespawnEvent(LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (!spawnSet && spawnLocation != null) {
                BlockPos upPos = spawnLocation.up(1);
                if (player.getPosition().getY() == upPos.getY()) {
                    spawnSet = true;
                }

                player.moveToBlockPosAndAngles(spawnLocation.up(1), 0.0F, 0.0F);
            }
        }
    }

    @SubscribeEvent
    public void setSpawnpointEvent(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityPlayer && event.getWorld().getWorldType() == WastelandWorld.worldtype_wasteland) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            if (this.isNewPlayer(player)) {
                if (spawnLocation == null && player.getPosition() != BlockPos.ORIGIN) {
                    spawnLocation = player.getPosition().down(9);
                }

                if (!spawnSet && spawnLocation != null) {
                    if (!bunkerSpawned && WastelandConfig.worldgen.shouldSpawnBunker) {
                        if (!event.getWorld().isRemote) {
                            BiomeDecoratorWasteland.spawnBunker(event.getWorld());
                        }

                        this.worldFileCache.saveSpawnLoc(spawnLocation);
                        bunkerSpawned = true;
                    }

                    BlockPos pos = spawnLocation.up();
                    player.setSpawnPoint(pos, true);
                    player.setSpawnChunk(pos, true, 0);
                }

                this.worldFileCache.savePlayerName(player.getDisplayNameString());
            }
        }
    }

    /*
    Stops zombies from burning during daytime.
    This could have been done better, not sure i need all these events, but hey it works!
    */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void zombieOnFire(LivingHurtEvent event) {
        if (event.getEntity() instanceof EntityZombie && event.getSource() == DamageSource.ON_FIRE && event.getEntity().world.getWorldType() == WastelandWorld.worldtype_wasteland && WastelandConfig.worldgen.shouldSpawnDayZombies) {
            event.getEntity().extinguish();
            event.setCanceled(true);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void zombieUpdateFire(LivingUpdateEvent event) {
        if (event.getEntity() instanceof EntityZombie && event.getEntity().isBurning() && event.getEntity().world.getWorldType() == WastelandWorld.worldtype_wasteland && WastelandConfig.worldgen.shouldSpawnDayZombies) {
            event.getEntity().extinguish();
        }
    }

    private boolean isNewPlayer(EntityPlayer player) {
        List loadedPlayers = this.worldFileCache.getPlayerNames();
        return loadedPlayers == null || !loadedPlayers.contains(player.getDisplayNameString());
    }
}