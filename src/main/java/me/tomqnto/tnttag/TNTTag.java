package me.tomqnto.tnttag;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.tomqnto.tnttag.commands.*;
import me.tomqnto.tnttag.game.GameManager;
import me.tomqnto.tnttag.game.map.GameMap;
import me.tomqnto.tnttag.listeners.*;
import me.tomqnto.tnttag.menus.api.InventoryListener;
import me.tomqnto.tnttag.player.PlayerTempData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class TNTTag extends JavaPlugin {

    private static TNTTag INSTANCE;
    private static GameManager gameManager;
    private HashMap<UUID, PlayerTempData> tempPlayerData = new HashMap<>();
    public List<GameMap> gameMaps = new ArrayList<>();

    public static TNTTag getInstance() {return INSTANCE;}
    public static GameManager getGameManager(){return gameManager;}
    public static ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    @Override
    public void onEnable() {
        INSTANCE = this;

        getDataFolder().mkdirs();
        File mapsFolder = new File(getDataFolder(), "maps");
        if (!mapsFolder.exists())
            mapsFolder.mkdirs();

        gameManager = new GameManager();

        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(gameManager, this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new GameStateChangeListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleportListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);

        getCommand("join").setExecutor(new JoinCommand());
        getCommand("games").setExecutor(new GamesCommand());
        getCommand("leave").setExecutor(new LeaveCommand());
        getCommand("delete_game").setExecutor(new DeleteGameCommand(gameManager));
        getCommand("maps").setExecutor(new MapsCommand());
        getCommand("players").setExecutor(new PlayersCommand());
        getCommand("spectate").setExecutor(new SpectateCommand());
        getCommand("start").setExecutor(new ForceStartCommand());


    }

    @Override
    public void onDisable() {
        for (GameMap map : gameMaps)
            map.unload();
    }

    public PlayerTempData getPlayerTempData(Player player){
        if (tempPlayerData.containsKey(player.getUniqueId()))
            return tempPlayerData.get(player.getUniqueId());
        else{
            tempPlayerData.put(player.getUniqueId(), new PlayerTempData());
            return tempPlayerData.get(player.getUniqueId());
        }
    }

}
