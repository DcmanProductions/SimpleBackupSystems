package chase.minecraft.architectury.simplebackupsystem;

import chase.minecraft.architectury.simplebackupsystem.data.BackupCreationResponse;
import chase.minecraft.architectury.simplebackupsystem.data.CompressionFormats;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;

import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class SimpleBackupSystemCommand
{
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
	{
		dispatcher.register(
				literal("backup").then(literal("create")
						.requires(commandSourceStack -> commandSourceStack.hasPermission(4))
						.then(
								argument("format", string())
										.then(
												argument("level", IntegerArgumentType.integer(0, 9))
														.executes(context -> create(context, Enum.valueOf(CompressionFormats.class, StringArgumentType.getString(context, "format")), IntegerArgumentType.getInteger(context, "level")))
										)
										.executes(context -> create(context, Enum.valueOf(CompressionFormats.class, StringArgumentType.getString(context, "format"))))
						)
						.executes(SimpleBackupSystemCommand::create))
		);
	}
	
	private static int create(CommandContext<CommandSourceStack> context)
	{
		return create(context, SimpleBackupSystem.configHandler.getConfig().compressionFormat, SimpleBackupSystem.configHandler.getConfig().compressionLevel);
	}
	
	private static int create(CommandContext<CommandSourceStack> context, CompressionFormats format)
	{
		return create(context, format, SimpleBackupSystem.configHandler.getConfig().compressionLevel);
	}
	
	
	private static int create(CommandContext<CommandSourceStack> context, CompressionFormats format, int level)
	{
		context.getSource().hasPermission(4);
		BackupCreationResponse response = BackupManager.getInstance().create(true, format, level);
		if (response.success())
		{
			Component name = Component.literal(response.backup().file().getName()).withStyle(style ->
			{
				style.withColor(ChatFormatting.GOLD);
				style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(response.backup().file().getAbsolutePath())));
				return style;
			});
			
			Component levelComponent = Component.literal("Compression Level: " + response.backup().compressionLevel()).withStyle(style ->
			{
				style.withColor(ChatFormatting.GREEN);
				style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(ChatFormatting.GOLD + "The compression level of the backup. Smaller is faster but larger file size, larger is slower but smaller file size")));
				return style;
			});
			Component formatComponent = Component.literal("Format: " + response.backup().compressionLevel()).withStyle(style ->
			{
				style.withColor(ChatFormatting.AQUA);
				style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(ChatFormatting.GOLD + "Zip, lzma, lzma2, gzip, and bzip2 are ways to make files smaller so they take up less space and can be sent or stored more easily.")));
				return style;
			});
			context.getSource().sendSystemMessage(name.copy().append(" - ").append(formatComponent).append(" (" + levelComponent + ")"));
		}
		return response.success() ? 0 : 1;
	}
}
