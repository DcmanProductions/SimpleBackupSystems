package chase.minecraft.architectury.simplebackupsystem;

import chase.minecraft.architectury.simplemodconfig.annotation.SimpleConfig;

public class SimpleBackupSystemConfiguration
{
	@SimpleConfig(displayName = "Backups Directory", description = "The directory where all backups are stored", index = 0)
	public String Directory = "backups/";
	@SimpleConfig(displayName = "Backup Interval", description = "The time until every backup is started", index = 1)
	public int Interval = 30;
	@SimpleConfig(displayName = "Backup Interval Format", description = "What the backup interval should be interpreted as", index = 2, options = {"Minutes", "Hours", "Days", "Weeks", "Months"})
	public String IntervalTimeFormat = "Minutes";
	@SimpleConfig(displayName = "Use WorldEdit Compatibility", description = "If the backups should be compatible with WorldEdit snapshots", index = 3)
	public boolean UseWorldEditCompatibility = false;
	@SimpleConfig(displayName = "Compression Level", description = "The compression level of the backup. Smaller is faster but larger file size, larger is slower but smaller file size", index = 4, max = 9, min = 0)
	public int CompressionLevel = 4;
	@SimpleConfig(displayName = "Compression Format", description = "The compression level of the backup. Smaller is faster but larger file size, larger is slower but smaller file size", index = 4, max = 9, min = 0)
	public String ArchiveFormat = "zip";
}
