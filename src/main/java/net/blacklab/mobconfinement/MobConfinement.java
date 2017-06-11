package net.blacklab.mobconfinement;

import net.blacklab.mobconfinement.util.Util;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = MobConfinement.MODID,
		name = "MobConfinement_EB",
		version = "4.0.0.8",
		dependencies = "required-after:net.blacklab.lib@[6.1.4.7,);"
				+ "required-after:Forge@[1.9.4-12.17.0.1976,)",
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
		Util.register();

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
