package net.blacklab.mobconfinement.items;

import net.blacklab.mobconfinement.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;

public class ItemAdvancedMobConf extends ItemMobConfinement {

	@Override
	public boolean isCatchable(Entity living) {
		if(
				living instanceof EntityDragon ||
				living instanceof EntityWither) return false;
		if(((EntityLiving)living).getHealth() >
				Util.confinementDefaultUpLimit*2) return false;
		return true;
	}

	@Override
	public void executeCatch(EntityPlayer player, EntityLiving living) {
		executeCatch(Util.confinementAdv,player, living);
	}

}
