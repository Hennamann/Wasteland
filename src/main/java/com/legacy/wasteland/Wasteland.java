package com.legacy.wasteland;

import com.legacy.wasteland.CommonProxy;
import com.legacy.wasteland.WastelandEventHandler;
import com.legacy.wasteland.config.LootConfig;
import com.legacy.wasteland.config.WorldConfig;
import com.legacy.wasteland.entities.WastelandEntityRegistry;
import com.legacy.wasteland.world.WastelandWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.legacy.wasteland.Wasteland.MOD_ID;
import static com.legacy.wasteland.Wasteland.VERSION;

@Mod(name = "Wasteland Mod", modid = MOD_ID, version = VERSION, acceptedMinecraftVersions = "[1.12.2]")
public class Wasteland {

   public static final String MOD_ID = "wastelands";
   public static final String VERSION = "1.0.0";

   @Instance("wastelands")
   public static Wasteland INSTANCE;

   @SidedProxy(clientSide = "com.legacy.wasteland.client.ClientProxy", serverSide = "com.legacy.wasteland.CommonProxy")

   public static CommonProxy PROXY;

   @EventHandler
   public static void preInit(FMLPreInitializationEvent event) {
      LootConfig.init(event.getModConfigurationDirectory());
      WorldConfig.init(event.getModConfigurationDirectory());
      PROXY.preInit();
   }


   @EventHandler
   public static void init(FMLInitializationEvent event) {
      WastelandWorld.init();
      WastelandEntityRegistry.init();
      registerEvent(new WastelandEventHandler());
   }

   public static void registerEvent(Object event) {
      MinecraftForge.EVENT_BUS.register(event);
   }
}