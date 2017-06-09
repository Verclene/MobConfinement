package net.blacklab.mobconfinement;

import java.io.IOException;

import net.blacklab.lib.config.ConfigList;
import net.blacklab.mobconfinement.util.Util;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = MobConfinement.MODID,
		name = "MobConfinement_EB",
		version = "4.0.0.7",
		dependencies = "required-after:net.blacklab.lib@[6.1.3.6,);required-after:Forge@[1.9.4-12.17.0.1976,)",
		acceptedMinecraftVersions = "[1.9.4,1.10.2]"
)
/*
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = true
)
*/
public class MobConfinement
{
	public static final String MODID = "net.blacklab.mobconfinement";
	public static final String CONFIGFILENAME = "mobconfinement";

	@Instance(MODID)
	public static MobConfinement instance;

	//public static int guiID = 1;
	//public static Block terminal;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Util.conf = new ConfigList();

		try{
			Util.conf.loadConfig(CONFIGFILENAME, event);;
		}
		catch (IOException var5)
		{
			var5.printStackTrace();
		}

		Util.isNormalEggTexture = Util.conf.getBoolean("isNormalEggTexture", false);
		Util.isTraditionalRecipe = Util.conf.getBoolean("isTraditionalRecipe", false);
		//Util.isLMMXDisableFreedom = Util.conf.getProperty("isLMMXDisableFreedom", true).getBoolean();

		try
		{
			Util.conf.saveConfig(CONFIGFILENAME, event);
		}
		catch (IOException var4)
		{
			var4.printStackTrace();
		}
		Util.register();

		//テクスチャ・モデル指定JSONファイル名の登録。
		if (event.getSide().isClient()) {
			Util.renderInstances();
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		Util.addRecipe();

	}
}
