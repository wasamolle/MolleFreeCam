package mollefreecam;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

import mollefreecam.Constants;
import mollefreecam.Functions;
import mollefreecam.Callbacks;
import mollefreecam.PlayerManager;

public class Main extends JavaPlugin implements Listener
{
	public Functions functions;
	public PlayerManager manager;

	// initialize everything on plugin load
	public void onEnable()
	{
		this.getConfig().options().copyDefaults(true);
		saveDefaultConfig();

		functions = new Functions(this);
		functions.consoleWarning(Constants.MSG_VERSION);

		manager = new PlayerManager(this);

		new Callbacks(this);
	}

	// disable freecam mode for all players when the plugin is unloaded
	public void onDisable()
	{
		manager.exitFreecamAll();
	}
}
