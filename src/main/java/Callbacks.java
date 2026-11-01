package mollefreecam;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;

import mollefreecam.Main;
import mollefreecam.Constants;

public class Callbacks implements CommandExecutor, Listener
{
    private Main plugin;

    public Callbacks(Main plugin)
    {
        this.plugin = plugin;

        plugin.getCommand(Constants.COMMAND).setExecutor(this);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // handle freecam command
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            plugin.functions.consoleError("Non-Player " + sender.getName() + " tried to enter freecam mode.");
        }
        else if (!cmd.getName().equals(Constants.COMMAND))
        {
            plugin.functions.consoleError(sender.getName() + " issued the command /" + cmd.getName() + " which is not registered.");
        }
        else
        {
            Player player = (Player) sender;
            UUID playerID = player.getUniqueId();
            if(plugin.manager.isPlayerInFreecam(playerID))
            {
                plugin.manager.exitFreecam(playerID);
            }
            else if(!playerHasPermission(player))
            {
                plugin.functions.playerError(player, Constants.MSG_NO_PERMISSION);
            }
            else if(plugin.manager.isPlayerOnCooldown(playerID))
            {
                String msg = Constants.MSG_ON_COOLDOWN.replace("%time%", Integer.toString(plugin.manager.getPlayerCooldown(playerID)));
                plugin.functions.playerError(player, msg);
            }
            else
            {
                plugin.manager.enterFreecam(playerID);
            }
        }
        return true;
    }

	private boolean playerHasPermission(Player player)
	{
        if(plugin.getConfig().getBoolean("default-allow"))
        {
            return true;
        }
        if(plugin.getConfig().getBoolean("op-allow") && player.isOp())
        {
            return true;
        }
        if(player.hasPermission("freecam.use"))
        {
            return true;
        }
        return false;
	}

	// prevent usage of commands when in freecam mode
    @EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e)
	{
		Player player = e.getPlayer();
		UUID playerID = player.getUniqueId();
		String cmd = e.getMessage();

        if(plugin.manager.isPlayerInFreecam(playerID) && !cmd.equals("/" + Constants.COMMAND))
		{
            plugin.functions.playerError(player, Constants.MSG_IN_FREECAM);
            e.setCancelled(true);
        }
	}

	// exit freecam mode when player quits
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();
		UUID playerID = player.getUniqueId();
		String playerName = player.getDisplayName();
		if(plugin.manager.isPlayerInFreecam(playerID))
        {
            plugin.manager.exitFreecam(playerID);
            plugin.functions.consoleWarning("Freecam mode from " + playerName + " got cancelled (disconnected).");
        }
	}

    // prevent the player from interacting with anything
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		UUID playerID = player.getUniqueId();
		if(plugin.manager.isPlayerInFreecam(playerID))
		{
            e.setCancelled(true);
		}
	}

	// exit freecam mode when npc get's damge
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e)
	{
		Entity entity = e.getEntity();
		if(plugin.manager.isEntityManaged(entity))
		{
            UUID playerID = plugin.manager.getEntityOwner(entity);
            Player player = plugin.functions.getPlayer(playerID);
            plugin.manager.exitFreecam(playerID);
            plugin.functions.playerError(player, Constants.MSG_ENTITY_DAMAGED);
            e.setCancelled(true);
        }
	}

	// stop chunks from unloading when npc is placed there
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent e)
	{
        Chunk chunk = e.getChunk();
		for(Entity entity : chunk.getEntities())
		{
			if(plugin.manager.isEntityManaged(entity))
			{
				//e.setCancelled(true);
				chunk.load();
			}
		}
	}
}