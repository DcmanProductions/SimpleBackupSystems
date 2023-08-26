package chase.minecraft.architectury.simplebackupsystem.fabric;

import chase.minecraft.architectury.simplebackupsystem.SimpleBackupSystem;
import net.fabricmc.api.ModInitializer;

public class SimpleBackupSystemFabric implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		SimpleBackupSystem.init();
	}
}