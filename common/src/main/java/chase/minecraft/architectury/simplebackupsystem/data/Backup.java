package chase.minecraft.architectury.simplebackupsystem.data;

import chase.minecraft.architectury.simplebackupsystem.data.CompressionFormats;

import java.io.File;

public record Backup(File file, CompressionFormats format, int compressionLevel, boolean manual, long created, long fileSize)
{
}
