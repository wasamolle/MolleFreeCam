package mollefreecam;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cryptomorin.xseries.messages.ActionBar;

import mollefreecam.Main;
import mollefreecam.Constants;

public class Functions
{
    private Main plugin;

    public Functions(Main plugin)
    {
        this.plugin = plugin;
    }

    public void consoleInfo(String msg)
    {
        msg = Constants.CONSOLE_PREFIX + msg;
        consoleMessage(msg);
    }

    public void consoleWarning(String msg)
    {
        msg = Constants.CONSOLE_PREFIX + Constants.COLOR_YELLOW + msg;
        consoleMessage(msg);
    }

    public void consoleError(String msg)
    {
        msg = Constants.CONSOLE_PREFIX + Constants.COLOR_DARK_RED + msg;
        consoleMessage(msg);
    }

    private void consoleMessage(String msg)
    {
        plugin.getServer().getConsoleSender().sendMessage(encodeColor(msg));
    }

    public void playerInfo(Player player, String msg)
    {
        playerMessage(player, Constants.COLOR_AQUA + msg);
    }

    public void playerError(Player player, String msg)
    {
        playerMessage(player, Constants.COLOR_DARK_RED + msg);
    }

    private void playerMessage(Player player, String msg)
    {
        if(player != null)
        {
            player.sendMessage(encodeColor(msg));
        }
    }

    public void playerActionBar(Player player, String msg)
    {
        if(player != null)
        {
            ActionBar.sendActionBar(player, encodeColor(Constants.COLOR_AQUA + msg));
        }
    }

    private static String encodeColor(String msg)
    {
        msg = ChatColor.translateAlternateColorCodes('&',msg);
        return msg;
    }

    public void playerSound(Player player, String sound)
    {
        if(player != null)
        {
            player.playSound(player.getLocation(), sound, Constants.SOUND_VOLUME, Constants.SOUND_PITCH);
        }
    }

    public Player getPlayer(UUID playerID)
    {
        return Bukkit.getPlayer(playerID);
    }
}