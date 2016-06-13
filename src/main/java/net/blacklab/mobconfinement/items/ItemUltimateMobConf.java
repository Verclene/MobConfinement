package net.blacklab.mobconfinement.items;

import net.blacklab.mobconfinement.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

public class ItemUltimateMobConf extends ItemMobConfinement {
	@Override
	public boolean isCatchable(Entity living) {
		//さらに強化版。殴れるなら何でも捕まえられる。コスト超高い。
		return true;
	}

	@Override
	public void executeCatch(EntityPlayer player, EntityLiving living) {
		// TODO Auto-generated method stub
		super.executeCatch(Util.confinementUltimate, player, living);
	}



}
