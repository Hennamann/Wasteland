package com.henrikstabell.wasteland.config;

import com.henrikstabell.wasteland.Wasteland;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

@Config(modid = Wasteland.MOD_ID, name = "wasteland_mod/Wasteland")
@Config.LangKey("wastelands.config.title")
public class WastelandConfig {

    @Config.Comment("Add item names chest loot. Do NOT add blank entries. Format for items:\\nmod_name:item_name,max,min\\nCheck mod language registry for item names. max = maximum stack size, min = minimum stack size. If max/min stack size > game stack limit, game will choose the stack limit.\"")
    public static Loot loot = new Loot();

    public static Worldgen worldgen = new Worldgen();

    public static Biomes biomes = new Biomes();

    public static class Worldgen {

        @Config.Comment("Should vanilla structures spawn?")
        public boolean shouldSpawnStructures = true;

        @Config.Comment("Dead Tree Rarity")
        public int wastelandTreeSpawnRate = 2;

        @Config.Comment("Wasteland fires per chunk")
        public int randomFirePerChunk = 1;

        @Config.Comment("Wasteland ruins rarity")
        public int wastelandRuinRarirty = 50;

        @Config.Comment("Forest tent/treehouse/ruins rarity")
        public int forestRuinRarity = 50;

        @Config.Comment("Mountain ruins rarity")
        public int mountainRuinRarity = 50;

        @Config.Comment("Oasis rarity")
        public int oasisRarity = 50;

        @Config.Comment("Wasteland Top Block")
        public String surfaceBlock = "minecraft:dirt";

        @Config.Comment("Wasteland Fill Block")
        public String fillerBlock = "minecraft:stone";

        @Config.Comment("Spawn in underground bunker")
        public boolean shouldSpawnBunker = true;

        @Config.Comment("Should the bunker spawn with a bed?")
        public boolean shouldSpawnBunkerBed = true;

        @Config.Comment("Enable cities")
        public boolean shouldSpawnCities = true;

        @Config.Comment("Allow zombies to spawn in daylight")
        public boolean shouldSpawnDayZombies = true;

        @Config.Comment("Allow CyberZombies from the CyberWare mod to spawn, this is only in effect if the CyberWare mod is installed")
        public boolean shouldSpawnCyberZombies = true;

        public Block getSurfaceBlock() {
            return Block.REGISTRY.getObject(new ResourceLocation(surfaceBlock));
        }

        public Block getFillerBlock() {
            return Block.REGISTRY.getObject(new ResourceLocation(fillerBlock));
        }
    }

    public static class Biomes {

        @Config.Comment("Should the Wasteland Mountains biome be enabled?")
        public boolean wastelandMountainsEnabled = true;

        @Config.Comment("Should the Wasteland Forest biome be enabled?")
        public boolean wastelandForestEnabled = true;

        @Config.Comment("Should the Wasteland City biome be enabled?")
        public boolean wastelandCityEnabled = true;

        @Config.Comment("Should the Oasis structure be enabled?")
        public boolean oasisEnabled = true;

        @Config.Comment({"Should Wasteland Biomes be able to spawn in the overworld?", "NOTE: Setting this to true can cause issues with other mod's structures!"})
        public boolean shouldWastelandBiomesSpawnInOverworld = false;
    }

    public static class Loot {

        @Config.Comment("Common ruins chest loot items")
        public String[] ruinEasyLoot = new String[]{"minecraft:mushroom_stew,1,1", "minecraft:bread,4,1", "minecraft:wheat,2,1", "minecraft:apple,3,1", "minecraft:string,6,2", "minecraft:rotten_flesh,8,2"};

        @Config.Comment("Rare ruins chest loot items")
        public String[] ruinRareLoot = new String[]{"minecraft:bucket,1,1", "minecraft:cooked_porkchop,3,2", "minecraft:cooked_beef,4,1", "minecraft:feather,8,2", "minecraft:iron_ingot,3,1"};

        @Config.Comment("Ruins seed chest loot items")
        public String[] seedLoot = new String[]{"minecraft:wheat_seeds,8,2"};

        @Config.Comment("Start bunker chest loot items")
        public String[] startLoot = new String[]{"minecraft:stone_sword,1,1", "minecraft:bread,2,2", "minecraft:leather_helmet,1,1", "minecraft:leather_boots,1,1", "minecraft:cooked_chicken,3,2", "minecraft:glass_bottle,2,1"};

        public ItemStack[] getLoot(String[] rawStringArray) {
            ItemStack[] items = new ItemStack[rawStringArray.length];

            for (int i = 0; i < rawStringArray.length; ++i) {
                if (rawStringArray[i].length() > 0) {
                    String[] split = rawStringArray[i].split(",");
                    int max;
                    int min;
                    if (split.length == 3) {
                        max = Integer.parseInt(split[1]);
                        min = Integer.parseInt(split[2]);
                    } else {
                        max = 1;
                        min = 1;
                    }

                    int range = max - min <= 0 ? 1 : max - min;
                    items[i] = GameRegistry.makeItemStack(split[0], 0, (new Random()).nextInt(range) + 1, null);
                }
            }

            return items;
        }
    }

    @Mod.EventBusSubscriber(modid = Wasteland.MOD_ID)
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChangedEvent(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Wasteland.MOD_ID)) {
                ConfigManager.sync(Wasteland.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
