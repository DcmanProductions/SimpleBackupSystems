package chase.minecraft.architectury.simplebackupsystem;

import chase.minecraft.architectury.simplebackupsystem.data.CompressionFormats;
import chase.minecraft.architectury.simplemodconfig.annotation.SimpleConfig;

public class SimpleBackupSystemConfiguration
{
	@SimpleConfig(displayName = "Backups Directory", description = "The directory where all backups are stored", index = 0)
	public String directory = "backups/";
	@SimpleConfig(displayName = "Backup Interval", description = "The time until every backup is started", index = 1)
	public int interval = 30;
	@SimpleConfig(displayName = "Backup Interval Format", description = "What the backup interval should be interpreted as", index = 2, options = {"Minutes", "Hours", "Days", "Weeks", "Months"})
	public String intervalTimeFormat = "Minutes";
	@SimpleConfig(displayName = "Use WorldEdit Compatibility", description = "If the backups should be compatible with WorldEdit snapshots", index = 3)
	public boolean useWorldEditCompatibility = false;
	@SimpleConfig(displayName = "Compression Level", description = "The compression level of the backup. Smaller is faster but larger file size, larger is slower but smaller file size", index = 4, max = 9, min = 0)
	public int compressionLevel = 4;
	@SimpleConfig(displayName = "Compression Format", description = "Zip, lzma, lzma2, gzip, and bzip2 are ways to make files smaller so they take up less space and can be sent or stored more easily.", index = 5, options = {"zip", "lzma", "lzma2", "gzip", "bzip2"})
	public CompressionFormats compressionFormat = CompressionFormats.zip;
	@SimpleConfig(displayName = "Max Backups", description = "The max number of backups that will be stored", index = 6, min = 1)
	public int maxBackups = 10;
	@SimpleConfig(displayName = "Check Player Activity", description = "Only backup the server when players are online or have been online since the last backup", index = 7)
	public boolean whenPlayersOnline = true;
}
