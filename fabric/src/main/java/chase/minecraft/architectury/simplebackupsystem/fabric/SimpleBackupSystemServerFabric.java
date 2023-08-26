package chase.minecraft.architectury.simplebackupsystem.fabric;

import chase.minecraft.architectury.simplebackupsystem.SimpleBackupSystem;
import net.fabricmc.api.DedicatedServerModInitializer;

public class SimpleBackupSystemServerFabric implements DedicatedServerModInitializer
{
	@Override
	public void onInitializeServer()
	{
		SimpleBackupSystem.initServer();
	}
}
