package com.henrikstabell.wasteland;

import com.henrikstabell.wasteland.proxy.CommonProxy;
import com.henrikstabell.wasteland.world.WastelandWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import static com.henrikstabell.wasteland.Wasteland.MOD_ID;
import static com.henrikstabell.wasteland.Wasteland.VERSION;

@Mod(name = "Wasteland Mod", modid = MOD_ID, version = VERSION, acceptedMinecraftVersions = "[1.12.2]", dependencies = "after:cyberware")
public class Wasteland {

    public static final String MOD_ID = "wasteland";
    public static final String VERSION = "1.2.2";

    public static Logger wastelandLogger;

    @Instance("wastelands")
    public static Wasteland INSTANCE;

    @SidedProxy(clientSide = "com.henrikstabell.wasteland.proxy.ClientProxy", serverSide = "com.henrikstabell.wasteland.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        wastelandLogger = event.getModLog();
        PROXY.preInit();
    }

    @EventHandler
    public static void init(FMLInitializationEvent event) {
        WastelandWorld.init();
        registerEvent(new WastelandEventHandler());
    }

    public static void registerEvent(Object event) {
        MinecraftForge.EVENT_BUS.register(event);
    }
}