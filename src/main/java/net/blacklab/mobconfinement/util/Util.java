package net.blacklab.mobconfinement.util;

import net.blacklab.mobconfinement.items.ItemMobConfinement;
import net.blacklab.mobconfinement.recipe.RecipeConfinementUpgrade;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Util {
	//各種設定
	//public static int confinementItemId;
	public static boolean isLMMXDisableFreedom;

	public static void register(){
		//登録作業
		ModConstants.Items.confinementItem = (new ItemMobConfinement()).
				setUnlocalizedName("mobconfinement").setCreativeTab(CreativeTabs.MISC);
		//総登録
		GameRegistry.register(ModConstants.Items.confinementItem, new ResourceLocation("mobconfinement"));
	}

	public static void addRecipe(){
		GameRegistry.addRecipe(
				new ItemStack(ModConstants.Items.confinementItem,1,0),
				" X ",
				"XYX",
				" X ",
				'X',Items.EGG,'Y',Items.REDSTONE);

		GameRegistry.addRecipe(new RecipeConfinementUpgrade());

	}

	public static void renderInstances(){
		ModelLoader.setCustomModelResourceLocation(ModConstants.Items.confinementItem, 0, new ModelResourceLocation("minecraft:egg", "inventory"));
		ModelLoader.setCustomModelResourceLocation(ModConstants.Items.confinementItem, 1, new ModelResourceLocation("minecraft:egg", "inventory"));
	}
}
