package net.blacklab.mobconfinement.recipe;

import net.blacklab.mobconfinement.util.ModConstants;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeConfinementUpgrade extends ShapedRecipes {

	public RecipeConfinementUpgrade() {
		super(3, 3, new ItemStack[]{
				null, new ItemStack(Items.DYE, 1, 4), null,
				new ItemStack(Items.DYE, 1, 4), new ItemStack(ModConstants.Items.confinementItem, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.DYE, 1, 4),
				null, new ItemStack(Items.DYE, 1, 4), null
		}, new ItemStack(ModConstants.Items.confinementItem));
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		// 現在のレベル
		int lCurrentTier = 0;
		NBTTagCompound lCompound = null;
		
		ItemStack lStack = inv.getStackInRowAndColumn(1, 1).copy();
		lStack.stackSize = 1;

		if (lStack != null && lStack.getItem() == ModConstants.Items.confinementItem) {
			// 該当アイテムなら
			lCompound = lStack.getTagCompound();
			if (lCompound != null) {
				// 現在のTierを取得
				lCurrentTier = lCompound.getInteger(ModConstants.Strings.TIER_KEY);
			} else {
				lCompound = new NBTTagCompound();
			}
			
			// 新たなTierを設定
			lCompound.setInteger(ModConstants.Strings.TIER_KEY, ++lCurrentTier);
			lStack.setTagCompound(lCompound);
			
			return lStack;
		}
		
		return super.getCraftingResult(inv);
	}

}
