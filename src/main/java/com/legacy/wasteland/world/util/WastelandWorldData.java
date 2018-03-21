package com.legacy.wasteland.world.util;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WastelandWorldData {
    private File file;

    public WastelandWorldData(String filename) {
        this.file = new File(filename);
    }

    public WastelandWorldData() {
        this.file = null;
    }

    public void setFile(String filename) {
        this.file = new File(filename);
    }

    public boolean checkIfExists() {
        return this.file.exists();
    }

    public void createFile() {
        NBTTagCompound playerTags = new NBTTagCompound();
        NBTTagCompound spawnTag = new NBTTagCompound();
        NBTTagCompound wastelandData = new NBTTagCompound();
        wastelandData.setTag("PlayerTags", playerTags);
        wastelandData.setTag("SpawnTag", spawnTag);

        try {
            this.file.createNewFile();
            FileOutputStream e = new FileOutputStream(this.file);
            CompressedStreamTools.writeCompressed(wastelandData, e);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    public List getPlayerNames() {
        ArrayList names = new ArrayList();

        try {
            FileInputStream e = new FileInputStream(this.file);
            NBTTagCompound wastelandData = CompressedStreamTools.readCompressed(e);
            names.addAll(wastelandData.getCompoundTag("PlayerTags").getKeySet());
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return names.isEmpty() ? null : names;
    }

    public void savePlayerNames(List names) {
        try {
            FileInputStream e = new FileInputStream(this.file);
            NBTTagCompound wastelandData = CompressedStreamTools.readCompressed(e);
            NBTTagCompound playerTags = wastelandData.getCompoundTag("PlayerTags");

            for (int fileStreamOut = 0; fileStreamOut < names.size(); ++fileStreamOut) {
                playerTags.setString((String) names.get(fileStreamOut), "NA");
            }

            FileOutputStream var7 = new FileOutputStream(this.file);
            CompressedStreamTools.writeCompressed(wastelandData, var7);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public void savePlayerName(String name) {
        ArrayList names = new ArrayList();
        names.add(name);
        this.savePlayerNames(names);
    }

    public void saveSpawnLoc(BlockPos spawn) {
        try {
            FileInputStream e = new FileInputStream(this.file);
            NBTTagCompound wastelandData = CompressedStreamTools.readCompressed(e);
            NBTTagCompound spawnTag = wastelandData.getCompoundTag("spawnTag");
            spawnTag.setInteger("spawnX", spawn.getX());
            spawnTag.setInteger("spawnY", spawn.getY());
            spawnTag.setInteger("spawnZ", spawn.getZ());
            FileOutputStream fileStreamOut = new FileOutputStream(this.file);
            CompressedStreamTools.writeCompressed(wastelandData, fileStreamOut);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public BlockPos loadSpawnLoc() {
        BlockPos spawn = null;

        try {
            FileInputStream e = new FileInputStream(this.file);
            NBTTagCompound wastelandData = CompressedStreamTools.readCompressed(e);
            NBTTagCompound spawnTag = wastelandData.getCompoundTag("spawnTag");
            spawn = new BlockPos(spawnTag.getInteger("spawnX"), spawnTag.getInteger("spawnY"), spawnTag.getInteger("spawnZ"));
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return spawn;
    }
}