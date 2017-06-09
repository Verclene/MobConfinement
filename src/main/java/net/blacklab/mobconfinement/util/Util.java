package net.blacklab.mobconfinement.util;

import net.blacklab.lib.config.ConfigList;
import net.blacklab.mobconfinement.MobConfinement;
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
	public static boolean isNormalEggTexture;
	public static boolean isTraditionalRecipe;
	public static boolean isLMMXDisableFreedom;

	public static ConfigList conf;

	public static void register(){
		//登録作業
		net.blacklab.mobconfinement.util.ModConstants.Items.confinementItem = (new ItemMobConfinement()).
				setUnlocalizedName("mobconfinement").setCreativeTab(CreativeTabs.MISC);
		//総登録
		GameRegistry.register(net.blacklab.mobconfinement.util.ModConstants.Items.confinementItem, new ResourceLocation("mobconfinement"));
	}

	public static void addRecipe(){
		//デフォルト
		if(isTraditionalRecipe){
			//以前のレシピで作成
			GameRegistry.addShapelessRecipe(
					new ItemStack(net.blacklab.mobconfinement.util.ModConstants.Items.confinementItem,1,0),
					new Object[] {new ItemStack(Items.EGG, 1, 0), new ItemStack(Items.REDSTONE, 1, 0)});
		}else{
			//新レシピで作成
			GameRegistry.addRecipe(
					new ItemStack(net.blacklab.mobconfinement.util.ModConstants.Items.confinementItem,1,0),
					" X ",
					"XYX",
					" X ",
					'X',Items.EGG,'Y',Items.REDSTONE);
		}
		
		GameRegistry.addRecipe(new RecipeConfinementUpgrade());

	}

	public static void renderInstances(){
		if(!isNormalEggTexture) {
			ModelResourceLocation confinementR[] = new ModelResourceLocation[]{
					new ModelResourceLocation(MobConfinement.MODID + ":" + "mobconfinement0", "inventory"),
					new ModelResourceLocation(MobConfinement.MODID + ":" + "mobconfinement1", "inventory")
			};
			ModelLoader.registerItemVariants(net.blacklab.mobconfinement.util.ModConstants.Items.confinementItem, confinementR);
			ModelLoader.setCustomModelResourceLocation(net.blacklab.mobconfinement.util.ModConstants.Items.confinementItem, 0, confinementR[0]);
			ModelLoader.setCustomModelResourceLocation(net.blacklab.mobconfinement.util.ModConstants.Items.confinementItem, 1, confinementR[1]);

		} else {
			ModelLoader.setCustomModelResourceLocation(net.blacklab.mobconfinement.util.ModConstants.Items.confinementItem, 0, new ModelResourceLocation("minecraft:egg", "inventory"));
			ModelLoader.setCustomModelResourceLocation(net.blacklab.mobconfinement.util.ModConstants.Items.confinementItem, 1, new ModelResourceLocation("minecraft:egg", "inventory"));
		}
	}
}
