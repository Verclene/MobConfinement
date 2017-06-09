package net.blacklab.mobconfinement.items;

import java.util.List;

import net.blacklab.mobconfinement.util.ModConstants.Items;
import net.blacklab.mobconfinement.util.ModConstants;
import net.blacklab.mobconfinement.util.Util;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemMobConfinement extends Item
{

	public boolean caught = false;

	/*
	public ItemMobConfinement(int id)
	{
		super(id);
		this.setMaxStackSize(64);
	}
	*/

	public boolean isCatchableBase(Entity living, ItemStack pStack){
		if(living instanceof EntityLiving){
			if(((EntityLiving)living).isDead){
				return false;
			}
			return isCatchable(living, pStack);
		}
		return false;
	}

	//ここからがグレードごとに異なる条件
	// Tierによって体力制限
	public boolean isCatchable(Entity living, ItemStack pStack){
		// Tier取得
		int lCurrentTier = 0;
		NBTTagCompound lCompound = pStack.getTagCompound();
		if (lCompound != null) {
			lCurrentTier = lCompound.getInteger(ModConstants.Strings.TIER_KEY);
		}
		
		if(((EntityLiving)living).getHealth() >
				ModConstants.Integers.HP_PER_TIER * (lCurrentTier + 1)) return false;
		return true;
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return EnumActionResult.PASS;
		}
		
		int blockX = pos.getX();
		int blockY = pos.getY();
		int blockZ = pos.getZ();

		blockX += facing.getFrontOffsetX();
		blockY += facing.getFrontOffsetY();
		blockZ += facing.getFrontOffsetZ();
		double advance = 0.0D;

		/*
		if (facing.getIndex() == 1 && block != null && block.getRenderType() == 11)
		{
			advance = 0.5D;
		}
		*/

		if (spawnEntity(stack, playerIn, worldIn, (double)blockX + 0.5D, (double)blockY + advance, (double)blockZ + 0.5D))
		{
			playerIn.playSound(SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.hostile.splash")), 0.2F,
					((playerIn.getRNG().nextFloat() - playerIn.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);

			return EnumActionResult.SUCCESS;
		}else
		{
			return EnumActionResult.PASS;
		}
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hitEntity(ItemStack itemStack, EntityLivingBase living, EntityLivingBase user)
	{
		return super.hitEntity(itemStack, living, user);

		/*
		if(living.isDead){
			living.setHealth(1.0f);
		}
		living.heal(1.0F);

		if (user instanceof EntityPlayer && living instanceof EntityLiving && !itemStack.hasTagCompound())
		{
			EntityPlayer player = (EntityPlayer)user;
			this.executeCatch(player, (EntityLiving)living);
			living.worldObj.playSoundAtEntity(living, "entity.hostile.splash", 0.2F, ((living.getRNG().nextFloat() - living.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
			living.setDead();
			return true;
		}
		else
		{
			return false;
		}
		*/

	}

	@Override
	public boolean onLeftClickEntity(ItemStack itemStack, EntityPlayer user,
			Entity living) {
		if (user instanceof EntityPlayer && living instanceof EntityLiving) {
			if (itemStack.hasTagCompound()) {
				if (itemStack.getTagCompound().getBoolean("Conf")) {
					return false;
				}
			}
			//キャンセル条件(HP20超・敵MOB無効)
			if(!isCatchableBase(living, itemStack)) return false;

			//メイド処理(1.8凍結)
			/*
			try{
				//MODIDで判定はするけど一応NCDFEキャッチ(荒業)
				if(Loader.isModLoaded("lmmx")){
					if(living.getClass().isAssignableFrom(LMM_EntityLittleMaid.class) && Util.isLMMXDisableFreedom){
						if(((LMM_EntityLittleMaid)living).isFreedom()){
							((LMM_EntityLittleMaid)living).setFreedom(false);
						}
					}
				}
			}catch(NoClassDefFoundError e){
				e.printStackTrace();
			}
			*/

			this.executeCatch(user, (EntityLiving)living);
			living.playSound(SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.hostile.splash")), 0.2F,
					((((EntityLivingBase) living).getRNG().nextFloat() - ((EntityLivingBase) living).getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
			living.setDead();
			return true;
		}
		else
		{
			return false;
		}

	}

	protected void executeCatch(EntityPlayer player, EntityLiving living)
	{
		ItemStack myCurrentItem = player.inventory.getCurrentItem();
		if (myCurrentItem != null && myCurrentItem.stackSize > 0){
			// Tier取得
			int lCurrentTier = 0;
			if (myCurrentItem.hasTagCompound()) {
				lCurrentTier = myCurrentItem.getTagCompound().getInteger(ModConstants.Strings.TIER_KEY);
			}
			
			ItemStack itemStack = new ItemStack(ModConstants.Items.confinementItem, 1, 1);
			itemStack.setItemDamage(1);
			itemStack.setTagCompound(new NBTTagCompound());

			NBTTagCompound nbttagcompound = itemStack.getTagCompound();
			NBTTagCompound entityNBT = new NBTTagCompound();
			living.writeToNBT(entityNBT);
			String entityName = EntityList.getEntityString(living);
			entityNBT.setString("id", entityName);
			entityNBT.setShort("HurtTime", (short)0);
			entityNBT.setTag("Motion", this.newDoubleNBTList(new double[] {0.0D, 0.0D, 0.0D}));
			entityNBT.setFloat("FallDistance", 0.0F);
			nbttagcompound.setTag("Mob", entityNBT);
			nbttagcompound.setBoolean("Conf", true);
			nbttagcompound.setInteger(ModConstants.Strings.TIER_KEY, lCurrentTier);

			String customName = living.hasCustomName() ? living.getCustomNameTag() : entityName;

			if (customName != null){
				nbttagcompound.setString("NameTag", customName);
				itemStack.setStackDisplayName("Confinement:"+customName);
			}else{
				nbttagcompound.setString("NameTag", "Unknown");
			}

			myCurrentItem.stackSize--;

			if (myCurrentItem.stackSize <= 0)
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, itemStack);
			}else if (!player.inventory.addItemStackToInventory(itemStack))
			{
				System.out.println("CALLED FROM="+player.getClass().getSimpleName());
				if(player instanceof EntityPlayerMP) player.entityDropItem(itemStack,1);
			}

			living.setDead();

		}else{
			player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
		}
	}

	private boolean spawnEntity(ItemStack itemStack, EntityPlayer player, World world, double x, double y, double z){
		//結構大事な処理なのでprivate
		itemStack.setItemDamage(0);

		if (!itemStack.hasTagCompound())
		{
			return false;
		}
		else
		{
			NBTTagCompound nbttagcompound = itemStack.getTagCompound();
			NBTTagCompound entityNBT = nbttagcompound.getCompoundTag("Mob");

			if (entityNBT == null)
			{
				return false;
			}
			else
			{
				Entity entity = EntityList.createEntityFromNBT(entityNBT, world);

				if (entity instanceof EntityLiving)
				{
					nbttagcompound.getIntArray("littlemaidmobx:textureindex");
					nbttagcompound.getInteger("littlemaidmobx:color");

					entity.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
					((EntityLiving)entity).rotationYawHead = ((EntityLiving)entity).rotationYaw;
					((EntityLiving)entity).renderYawOffset = ((EntityLiving)entity).rotationYaw;
					entity.playSound(SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.hostile.splash")), 0.2F,
							((((EntityLivingBase) entity).getRNG().nextFloat() - ((EntityLivingBase) entity).getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
					((EntityLiving)entity).playLivingSound();

					if (!world.isRemote)
					{
						world.spawnEntityInWorld(entity);
					}

					//名前をデフォルトに戻す
					ItemStack a = new ItemStack(itemStack.getItem(),1,0);
					itemStack.setStackDisplayName(a.getDisplayName());
					onSpawned(itemStack);
					return true;
				}
				else
				{
					onSpawned(itemStack);
					return false;
				}
			}
		}
	}

	protected void onSpawned(ItemStack stack){
		// タグリセットの設定
		int lCurrentTier = 0;
		NBTTagCompound lCompound = stack.getTagCompound();
		if (lCompound != null) {
			lCurrentTier = lCompound.getInteger(ModConstants.Strings.TIER_KEY);
		}

		NBTTagCompound lCompound2 = new NBTTagCompound();
		lCompound2.setInteger(ModConstants.Strings.TIER_KEY, lCurrentTier);
		stack.setTagCompound(lCompound2);
	}

	public boolean func_77636_d(ItemStack itemStack)
	{
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void func_77624_a(ItemStack itemStack, EntityPlayer player, List list, boolean flag)
	{
		NBTTagCompound nbttagcompound = itemStack.getTagCompound();
		
		int lCurrentTier = 0;
		if (nbttagcompound != null)
		{
			lCurrentTier = nbttagcompound.getInteger(ModConstants.Strings.TIER_KEY);
			// Mob
			NBTTagCompound entityNBT = nbttagcompound.getCompoundTag("Mob");

			if (entityNBT != null)
			{
				list.add(entityNBT.getString("id"));
				String nameTag = nbttagcompound.getString("NameTag");

				if (nameTag != null && !nameTag.equals(""))
				{
					list.add(nameTag);
				}
			}
		}
		list.add(String.format("Tier: %d", lCurrentTier + 1));
	}

	protected NBTTagList newDoubleNBTList(double[] arrayOfDouble)
	{
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < arrayOfDouble.length; ++i)
		{
			nbttaglist.appendTag(new NBTTagDouble(arrayOfDouble[i]));
		}

		return nbttaglist;
	}

	@Override
	public boolean isDamageable() {
		// TODO Auto-generated method stub
		return true;
	}

}
