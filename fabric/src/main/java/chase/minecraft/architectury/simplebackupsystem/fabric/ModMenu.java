package chase.minecraft.architectury.simplebackupsystem.fabric;

import chase.minecraft.architectury.simplebackupsystem.SimpleBackupSystem;
import chase.minecraft.architectury.simplemodconfig.client.gui.screen.ConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screens.Screen;

public class ModMenu implements ModMenuApi
{
	@Override
	public ConfigScreenFactory<ConfigScreen> getModConfigScreenFactory()
	{
		return screen -> new ConfigScreen(SimpleBackupSystem.configHandler, screen);
	}
}
