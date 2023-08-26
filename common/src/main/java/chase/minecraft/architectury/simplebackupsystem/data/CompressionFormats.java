package chase.minecraft.architectury.simplebackupsystem.data;

import java.util.Arrays;

public enum CompressionFormats
{
	zip(".zip"),
	lzma(".lzma"),
	lzma2(".lzma2"),
	gzip(".gz"),
	bzip2(".bz2");
	
	private final String extension;
	
	CompressionFormats(String extension)
	{
		this.extension = extension;
	}
	
	public String extension()
	{
		return this.extension;
	}
	public static String[] names(){
		return Arrays.stream(CompressionFormats.values()).map(Enum::name).toArray(String[]::new);
	}
	public static String[] extensions(){
		return Arrays.stream(CompressionFormats.values()).map(CompressionFormats::extension).toArray(String[]::new);
	}
}
