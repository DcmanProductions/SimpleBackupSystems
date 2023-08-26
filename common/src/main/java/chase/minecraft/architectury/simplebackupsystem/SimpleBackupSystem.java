package chase.minecraft.architectury.simplebackupsystem;

import chase.minecraft.architectury.simplemodconfig.SimpleModConfigBuilder;
import chase.minecraft.architectury.simplemodconfig.handlers.ConfigHandler;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleBackupSystem
{
	public static final String MOD_ID = "simplebackupsystem";
	public static final Logger log = LogManager.getLogger(MOD_ID);
	public static SimpleModConfigBuilder builder;
	public static ConfigHandler<SimpleBackupSystemConfiguration> configHandler;
	
	public static void init()
	{
		log.info("Starting {}", MOD_ID);
		
		configHandler = new ConfigHandler<>("simple-backup-system", new SimpleBackupSystemConfiguration());
		builder = new SimpleModConfigBuilder(configHandler, "Simple Backup System")
				.withCommand("backup-config", Component.literal("Simple Backup System"), 4);
	}
	public static void initServer(){
		CommandRegistrationEvent.EVENT.register((dispatcher, registry, selection) -> SimpleBackupSystemCommand.register(dispatcher));
		BackupManager.getInstance().start();
		LifecycleEvent.SERVER_STOPPING.register(instance -> {
			BackupManager.getInstance().interrupt();
		});
	}
	
	public static ResourceLocation getModResource(String name)
	{
		return new ResourceLocation(MOD_ID, name);
	}
}