package chase.minecraft.architectury.simplebackupsystem;

import chase.minecraft.architectury.simplebackupsystem.data.Backup;
import chase.minecraft.architectury.simplebackupsystem.data.BackupCreationResponse;
import chase.minecraft.architectury.simplebackupsystem.data.CompressionFormats;
import dev.architectury.platform.Platform;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

import static chase.minecraft.architectury.simplebackupsystem.SimpleBackupSystem.configHandler;
import static chase.minecraft.architectury.simplebackupsystem.SimpleBackupSystem.log;

public class BackupManager extends Thread implements Runnable
{
	private static final BackupManager instance = new BackupManager();
	private final Timer timer;
	private final File outputDirectory;
	private boolean hasPlayerJoined = false;
	
	protected BackupManager()
	{
		super("Backup Manager Thread");
		timer = new Timer();
		outputDirectory = Path.of(Platform.getGameFolder().toString(), configHandler.getConfig().directory).toFile();
	}
	
	public static BackupManager getInstance()
	{
		return instance;
	}
	
	public void restart()
	{
		timer.cancel();
		
	}
	
	@Override
	public synchronized void start()
	{
		super.start();
		log.info("Starting thread");
		startTimer();
	}
	
	private void startTimer()
	{
		int shortInterval = configHandler.getConfig().interval;
		String intervalTimeFormat = configHandler.getConfig().intervalTimeFormat;
		long interval = switch (intervalTimeFormat)
		{
			case "Minutes" -> shortInterval * 60 * 1000L; // Convert minutes to milliseconds
			case "Hours" -> shortInterval * 60 * 60 * 1000L; // Convert hours to milliseconds
			case "Days" -> shortInterval * 24 * 60 * 60 * 1000L; // Convert days to milliseconds
			case "Weeks" -> shortInterval * 7 * 24 * 60 * 60 * 1000L; // Convert weeks to milliseconds
			case "Months" -> shortInterval * 30 * 24 * 60 * 60 * 1000L; // Assuming a month has 30 days
			default -> 0L; // Default value in case of an unsupported format
		};
		
		timer.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				
				if (hasPlayerJoined)
				{
					log.info("Creating backup via schedule");
					create(false,  configHandler.getConfig().compressionFormat,  configHandler.getConfig().compressionLevel);
				}
			}
		}, interval, 0);
	}
	
	public BackupCreationResponse create(boolean manual, CompressionFormats format, int compressionLevel)
	{
		long time = System.currentTimeMillis();
		File entryDirectory = Path.of(Platform.getGameFolder().toString(), "world").toFile();
		if (outputDirectory.mkdirs())
		{
			log.info("Creating backups directory");
		}
		File file = Path.of(outputDirectory.toString(), "backup-%d%s".formatted(time, format.extension())).toFile();
		
		@Nullable Backup backup = null;
		String message = "";
		
		try (FileOutputStream fileOutputStream = new FileOutputStream(file))
		{
			switch (format)
			{
				case zip:
					try (ZipArchiveOutputStream zipStream = new ZipArchiveOutputStream(fileOutputStream))
					{
						zipStream.setLevel(compressionLevel);
						ArchiveEntry entry = zipStream.createArchiveEntry(entryDirectory, "world");
						zipStream.putArchiveEntry(entry);
						zipStream.flush();
						backup = new Backup(file, format, compressionLevel, manual, time, file.length());
					} catch (Exception e)
					{
						message = e.getMessage();
						log.error("Unable to create zip archive: {} - {}", e.getMessage(), file.getAbsolutePath());
						e.printStackTrace();
					}
					break;
				case bzip2:
					break;
				case gzip:
					break;
				case lzma:
					break;
				case lzma2:
					break;
			}
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		cleanup();
		if (backup != null)
		{
			return new BackupCreationResponse(backup, true, message);
		} else
		{
			return new BackupCreationResponse(null, false, message);
		}
		
	}
	
	private void cleanup()
	{
		File[] backups = outputDirectory.listFiles(pathname -> Arrays.stream(CompressionFormats.extensions()).toList().contains(pathname.getName().substring(pathname.getName().lastIndexOf('.'))));
		assert backups != null;
		if (backups.length > configHandler.getConfig().maxBackups)
		{
			Arrays.sort(backups, Comparator.comparingLong(File::lastModified));
			int backupsToDelete = backups.length - configHandler.getConfig().maxBackups;
			for (int i = 0; i < backupsToDelete; i++)
			{
				File backupToDelete = backups[i];
				if (backupToDelete.delete())
				{
					log.info("Deleted old backup: {}", backupToDelete.getName());
				} else
				{
					log.error("Failed to deleted old backup: {}", backupToDelete.getName());
				}
			}
		}
		hasPlayerJoined = false;
	}
	
	@Override
	public void run()
	{
		super.run();
	}
	
	@Override
	public void interrupt()
	{
		super.interrupt();
		timer.cancel();
	}
	
	public void playerUpdated()
	{
		hasPlayerJoined = true;
	}
}
