package com.legacy.wasteland.world.biome;

import com.henrikstabell.mistcore.api.IBiomeMist;
import com.legacy.wasteland.world.biome.BiomeGenWastelandBase;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class BiomeGenApocalypseMist extends BiomeGenWastelandBase implements IBiomeMist {
    public BiomeGenApocalypseMist(BiomeProperties properties) {
        super(properties);
    }

    @Override
    public float getMistDensity(int i, int i1, int i2) {
        return 0.1F;
    }

    @Override
    public int getMistColour(int i, int i1, int i2) {
        return 16777215;
    }
}
