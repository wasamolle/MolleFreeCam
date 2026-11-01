package mollefreecam;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Entity;

import mollefreecam.Main;
import mollefreecam.PlayerData;

public class PlayerManager
{
    private Main plugin;
    private HashMap<UUID,PlayerData> players;

    public PlayerManager(Main plugin)
    {
        this.plugin = plugin;

        this.players = new HashMap<UUID,PlayerData>();
    }

    public void enterFreecam(UUID playerID)
    {
        if(!players.containsKey(playerID))
        {
            PlayerData playerData = new PlayerData(plugin, playerID);
            players.put(playerID, playerData);
            playerData.runTaskTimer(plugin, 20, 20);
            playerData.enterFreecam();
        }
        else
        {
            players.get(playerID).enterFreecam();
        }
    }

    public boolean isPlayerInFreecam(UUID playerID)
    {
        if(players.containsKey(playerID))
        {
            return players.get(playerID).isInFreecam();
        }
        return false;
    }

    public void exitFreecam(UUID playerID)
    {
        if(players.containsKey(playerID))
        {
            players.get(playerID).exitFreecam();
        }
    }

    public void exitFreecamAll()
    {
        for (PlayerData playerData : players.values())
        {
            playerData.exitFreecam();
        }
    }

    public boolean isPlayerOnCooldown(UUID playerID)
    {
        return getPlayerCooldown(playerID) > 0;
    }

    public int getPlayerCooldown(UUID playerID)
    {
        if(players.containsKey(playerID))
        {
            return players.get(playerID).getCooldown();
        }
        return 0;
    }

    public boolean isEntityManaged(Entity target)
    {
        for (PlayerData playerData : players.values())
        {
            Entity entity = playerData.getEntity();
            return target == entity;
        }
        return false;
    }

    public UUID getEntityOwner(Entity target)
    {
        for (PlayerData playerData : players.values())
        {
            Entity entity = playerData.getEntity();
            if(target == entity)
            {
                return playerData.getPlayerID();
            }
        }
        return null;
    }

}