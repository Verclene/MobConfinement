package net.blacklab.mobconfinement.util;

import net.blacklab.lib.config.ConfigList;
import net.blacklab.mobconfinement.MobConfinement;
import net.blacklab.mobconfinement.items.ItemAdvancedMobConf;
import net.blacklab.mobconfinement.items.ItemMobConfinement;
import net.blacklab.mobconfinement.items.ItemUltimateMobConf;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Util {
	//追加アイテム
	public static Item confinementItem;
	public static Item confinementAdv;
	public static Item confinementUltimate;

	//各種設定
	//public static int confinementItemId;
	public static boolean isNormalEggTexture;
	public static float confinementDefaultUpLimit;
	public static boolean isTraditionalRecipe;
	public static boolean isLMMXDisableFreedom;

	public static ConfigList conf;

	public static void register(){
		//登録作業
		confinementItem = (new ItemMobConfinement()).
				setUnlocalizedName("mobconfinement").setCreativeTab(CreativeTabs.tabMisc);
		//一段階上位(エンダーパール*2＋ブレイズロッド*2+MobConfinement)
		confinementAdv = new ItemAdvancedMobConf()
			.setUnlocalizedName("mobconfinementadvanced")
			.setCreativeTab(CreativeTabs.tabMisc);
		//最上位
		confinementUltimate = new ItemUltimateMobConf()
			.setUnlocalizedName("mobconfinementultimate")
			/*.setCreativeTab(CreativeTabs.tabAllSearch)*/;
		//総登録
		GameRegistry.registerItem(confinementItem, "mobconfinement");
		GameRegistry.registerItem(confinementAdv, "mobconfinementadvanced");
		GameRegistry.registerItem(confinementUltimate, "confinementultimate");
	}

	public static void addRecipe(){
		//デフォルト
		if(isTraditionalRecipe){
			//以前のレシピで作成
			GameRegistry.addShapelessRecipe(
					new ItemStack(confinementItem,1,0),
					new Object[] {new ItemStack(Items.egg, 1, 0), new ItemStack(Items.redstone, 1, 0)});
		}else{
			//新レシピで作成
			GameRegistry.addRecipe(
					new ItemStack(confinementItem,1,0),
					" X ",
					"XYX",
					" X ",
					'X',Items.egg,'Y',Items.redstone);
		}

		GameRegistry.addRecipe(
				new ItemStack(confinementAdv,1,0),
				" X ",
				"YZY",
				" X ",
				'X',Items.ender_pearl,'Y',Items.blaze_rod,'Z',new ItemStack(confinementItem,1,0));

		GameRegistry.addRecipe(
				new ItemStack(confinementUltimate,1,0),
				"XXX",
				"XYX",
				"XXX",
				'X',Blocks.diamond_block,'Y',new ItemStack(confinementAdv,1,0));
	}

	public static void renderInstances(){
		if(!isNormalEggTexture) {
			ModelBakery.addVariantName(confinementItem, MobConfinement.MODID + ":" + "mobconfinement0", MobConfinement.MODID + ":" + "mobconfinement1");
			ModelLoader.setCustomModelResourceLocation(confinementItem, 0, new ModelResourceLocation(MobConfinement.MODID + ":" + "mobconfinement0", "inventory"));
			ModelLoader.setCustomModelResourceLocation(confinementItem, 1, new ModelResourceLocation(MobConfinement.MODID + ":" + "mobconfinement1", "inventory"));

			ModelBakery.addVariantName(confinementAdv, MobConfinement.MODID + ":" + "adv_mobconfinement0", MobConfinement.MODID + ":" + "adv_mobconfinement1");
			ModelLoader.setCustomModelResourceLocation(confinementAdv, 0, new ModelResourceLocation(MobConfinement.MODID + ":" + "adv_mobconfinement0", "inventory"));
			ModelLoader.setCustomModelResourceLocation(confinementAdv, 1, new ModelResourceLocation(MobConfinement.MODID + ":" + "adv_mobconfinement1", "inventory"));

			ModelBakery.addVariantName(confinementUltimate, MobConfinement.MODID + ":" + "ult_mobconfinement0", MobConfinement.MODID + ":" + "ult_mobconfinement1");
			ModelLoader.setCustomModelResourceLocation(confinementUltimate, 0, new ModelResourceLocation(MobConfinement.MODID + ":" + "ult_mobconfinement0", "inventory"));
			ModelLoader.setCustomModelResourceLocation(confinementUltimate, 1, new ModelResourceLocation(MobConfinement.MODID + ":" + "ult_mobconfinement1", "inventory"));
		} else {
			ModelLoader.setCustomModelResourceLocation(confinementItem, 0, new ModelResourceLocation("minecraft:egg", "inventory"));
			ModelLoader.setCustomModelResourceLocation(confinementItem, 1, new ModelResourceLocation("minecraft:egg", "inventory"));

			ModelLoader.setCustomModelResourceLocation(confinementAdv, 0, new ModelResourceLocation("minecraft:egg", "inventory"));
			ModelLoader.setCustomModelResourceLocation(confinementAdv, 1, new ModelResourceLocation("minecraft:egg", "inventory"));

			ModelLoader.setCustomModelResourceLocation(confinementUltimate, 0, new ModelResourceLocation("minecraft:egg", "inventory"));
			ModelLoader.setCustomModelResourceLocation(confinementUltimate, 1, new ModelResourceLocation("minecraft:egg", "inventory"));
		}
	}
}
