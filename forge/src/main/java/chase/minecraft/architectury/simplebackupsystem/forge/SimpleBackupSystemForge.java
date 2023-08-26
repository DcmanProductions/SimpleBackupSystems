package chase.minecraft.architectury.simplebackupsystem.forge;

import chase.minecraft.architectury.simplebackupsystem.SimpleBackupSystem;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SimpleBackupSystem.MOD_ID)
public class SimpleBackupSystemForge
{
	public SimpleBackupSystemForge()
	{
		// Submit our event bus to let architectury register our content on the right time
		EventBuses.registerModEventBus(SimpleBackupSystem.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
		SimpleBackupSystem.init();
	}
}