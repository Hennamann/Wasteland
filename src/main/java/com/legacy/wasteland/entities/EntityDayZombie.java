package com.legacy.wasteland.entities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityDayZombie extends EntityZombie {
   public EntityDayZombie(World worldIn) {
      super(worldIn);
      this.isImmuneToFire = true;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15000000417232512D);
   }

   public boolean isVillager() {
      return false;
   }

   public boolean getCanSpawnHere() {
      return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.isDaytime();
   }
}
