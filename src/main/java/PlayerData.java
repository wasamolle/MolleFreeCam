package mollefreecam;

import java.util.UUID;

import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import mollefreecam.Main;
import mollefreecam.Constants;
import mollefreecam.Functions;

public class PlayerData extends BukkitRunnable
{
    private Main plugin;
    private UUID playerID;

    private int cooldown;
    private int period;
    private boolean inFreecam;
    private Entity entity;

    public PlayerData(Main plugin, UUID playerID)
    {
        this.plugin = plugin;
        this.playerID = playerID;

        this.cooldown = 0;
        this.period = 0;
        this.inFreecam = false;
        this.entity = null;
    }

    public UUID getPlayerID()
    {
        return playerID;
    }

    public int getCooldown()
    {
        return cooldown;
    }

    public boolean isInFreecam()
    {
        return inFreecam;
    }

    public Entity getEntity()
    {
        return this.entity;
    }

    @Override
    public void run()
    {
        timerTick();
        checkPeriod();
        checkDistance();
    }

    private void timerTick()
    {
        if(period >= 0)
        {
            period--;
        }

        if(cooldown >= 0)
        {
            cooldown--;
        }
        showActionBar();
    }

    private void checkPeriod()
    {
        if(isInFreecam() && period == -1)
        {
            exitFreecam();
        }
    }

    private void checkDistance()
    {
        Player player = plugin.functions.getPlayer(playerID);
        double maxDistance = plugin.getConfig().getDouble("freecam-max-distance");
        if(isInFreecam() && maxDistance > 0.0)
        {
            Location playerLocation = player.getLocation();
            Location entityLocation = entity.getLocation();
            double distance = playerLocation.distance(entityLocation);
            if(distance > maxDistance)
            {
                exitFreecam();
            }
            else if(distance > 0.9 * maxDistance)
            {
                plugin.functions.playerActionBar(player, Constants.TITLE_DISTANCE);
            }
        }
    }

    private void showActionBar()
    {
        Player player = plugin.functions.getPlayer(playerID);

        if(period >= 0)
        {
            String msg = Constants.TITLE_PERIOD.replace("%time%", Integer.toString(period));
            plugin.functions.playerActionBar(player, msg);
        }
        else if(cooldown >= 0)
        {
            String msg = Constants.TITLE_COOLDOWN.replace("%time%", Integer.toString(cooldown));
            plugin.functions.playerActionBar(player, msg);
        }
    }

    public void enterFreecam()
    {
        if(!inFreecam)
        {
            inFreecam = true;
            Player player = plugin.functions.getPlayer(playerID);

            createEntity();
            player.setGameMode(GameMode.SPECTATOR);

            period = plugin.getConfig().getInt("freecam-period");

            plugin.functions.playerInfo(player, Constants.MSG_FREECAM_ENTER);
            plugin.functions.playerSound(player, Constants.SOUND_FREECAM_ENTER);

            showActionBar();
        }
    }

    public void exitFreecam()
    {
        if(inFreecam)
        {
            inFreecam = false;
            period = -1;
            Player player = plugin.functions.getPlayer(playerID);

            player.teleport(getEntity().getLocation());

            player.setGameMode(GameMode.SURVIVAL);
            destroyEntity();

            cooldown = plugin.getConfig().getInt("freecam-cooldown");

            plugin.functions.playerInfo(player, Constants.MSG_FREECAM_EXIT);
            plugin.functions.playerSound(player, Constants.SOUND_FREECAM_EXIT);

            showActionBar();
        }
    }

    private void createEntity()
    {
        Player player = plugin.functions.getPlayer(playerID);
        String playerName = player.getDisplayName();

        if(getEntity() != null)
        {
            plugin.functions.consoleError("Error! Entity already exists for player " + playerName + "...");
        }
        else
        {
            World world = player.getWorld();
            Location location = player.getLocation();
            entity = world.spawnEntity(location, EntityType.VILLAGER, false);

            entity.setSilent(true);
            ((LivingEntity) entity).setAI(false);
            entity.setCustomNameVisible(true);
            entity.setCustomName(playerName);
        }
    }

    private void destroyEntity()
    {
        Player player = plugin.functions.getPlayer(playerID);
        String playerName = player.getDisplayName();

        if(getEntity() == null)
        {
            plugin.functions.consoleError("Error! Entity does not exist for player " + playerName + "...");
        }
        else
        {
            entity.remove();
            entity = null;
        }
    }
}