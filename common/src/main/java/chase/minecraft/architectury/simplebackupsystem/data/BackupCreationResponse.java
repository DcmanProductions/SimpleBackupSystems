package chase.minecraft.architectury.simplebackupsystem.data;

public record BackupCreationResponse(Backup backup, boolean success, String message)
{
}
