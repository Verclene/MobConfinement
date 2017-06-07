package net.blacklab.mobconfinement.util;

import net.blacklab.lib.config.ConfigList;
import net.blacklab.mobconfinement.MobConfinement;
import net.blacklab.mobconfinement.items.ItemAdvancedMobConf;
import net.blacklab.mobconfinement.items.ItemMobConfinement;
import net.blacklab.mobconfinement.items.ItemUltimateMobConf;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
				setUnlocalizedName("mobconfinement").setCreativeTab(CreativeTabs.MISC);
		//一段階上位(エンダーパール*2＋ブレイズロッド*2+MobConfinement)
		confinementAdv = new ItemAdvancedMobConf()
			.setUnlocalizedName("mobconfinementadvanced")
			.setCreativeTab(CreativeTabs.MISC);
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
					new Object[] {new ItemStack(Items.EGG, 1, 0), new ItemStack(Items.REDSTONE, 1, 0)});
		}else{
			//新レシピで作成
			GameRegistry.addRecipe(
					new ItemStack(confinementItem,1,0),
					" X ",
					"XYX",
					" X ",
					'X',Items.EGG,'Y',Items.REDSTONE);
		}

		GameRegistry.addRecipe(
				new ItemStack(confinementAdv,1,0),
				" X ",
				"YZY",
				" X ",
				'X',Items.ENDER_PEARL,'Y',Items.BLAZE_ROD,'Z',new ItemStack(confinementItem,1,0));

		GameRegistry.addRecipe(
				new ItemStack(confinementUltimate,1,0),
				"XXX",
				"XYX",
				"XXX",
				'X',Blocks.DIAMOND_BLOCK,'Y',new ItemStack(confinementAdv,1,0));
	}

	public static void renderInstances(){
		if(!isNormalEggTexture) {
			ModelResourceLocation confinementR[] = new ModelResourceLocation[]{
					new ModelResourceLocation(MobConfinement.MODID + ":" + "mobconfinement0", "inventory"),
					new ModelResourceLocation(MobConfinement.MODID + ":" + "mobconfinement1", "inventory")
			};
			ModelLoader.registerItemVariants(confinementItem, confinementR);
			ModelLoader.setCustomModelResourceLocation(confinementItem, 0, confinementR[0]);
			ModelLoader.setCustomModelResourceLocation(confinementItem, 1, confinementR[1]);

			ModelResourceLocation advConfinementR[] = new ModelResourceLocation[]{
					new ModelResourceLocation(MobConfinement.MODID + ":" + "adv_mobconfinement0", "inventory"),
					new ModelResourceLocation(MobConfinement.MODID + ":" + "adv_mobconfinement1", "inventory")
			};
			ModelLoader.registerItemVariants(confinementAdv, advConfinementR);
			ModelLoader.setCustomModelResourceLocation(confinementAdv, 0, advConfinementR[0]);
			ModelLoader.setCustomModelResourceLocation(confinementAdv, 1, advConfinementR[1]);

			ModelResourceLocation ultConfinementR[] = new ModelResourceLocation[]{
					new ModelResourceLocation(MobConfinement.MODID + ":" + "ult_mobconfinement0", "inventory"),
					new ModelResourceLocation(MobConfinement.MODID + ":" + "ult_mobconfinement1", "inventory")
			};
			ModelLoader.registerItemVariants(confinementUltimate, ultConfinementR);
			ModelLoader.setCustomModelResourceLocation(confinementUltimate, 0, ultConfinementR[0]);
			ModelLoader.setCustomModelResourceLocation(confinementUltimate, 1, ultConfinementR[1]);
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
