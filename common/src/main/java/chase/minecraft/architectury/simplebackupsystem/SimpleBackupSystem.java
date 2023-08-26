package chase.minecraft.architectury.simplebackupsystem;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleBackupSystem
{
	public static final String MOD_ID = "simplebackupsystem";
	public static final Logger log = LogManager.getLogger(MOD_ID);
	public static void init() {
		log.info("Starting {}", MOD_ID);
		
	}
	public static ResourceLocation getModResource(String name){
		return new ResourceLocation(MOD_ID, name);
	}
}