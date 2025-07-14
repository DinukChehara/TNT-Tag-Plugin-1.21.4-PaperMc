package me.tomqnto.tnttag.game;

import me.tomqnto.tnttag.TNTTag;
import me.tomqnto.tnttag.game.map.GameMap;
import me.tomqnto.tnttag.game.map.MapConfig;
import me.tomqnto.tnttag.menus.GamesMenu;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Random;

public class GameManager implements Listener {

    private final HashMap<String,Game> games = new HashMap<>();
    public static int gamesCount = 0;
    public int maxGames = 15;
    private final GamesMenu gamesMenu = new GamesMenu(this);

    public @Nullable Game createGame(@Nullable String mapName){
        if (games.size()<maxGames){
            if (MapConfig.getMaps().isEmpty()){
                Bukkit.getLogger().warning("Could not start game. No maps available");
                return null;
            }
            if (mapName==null)
                mapName = MapConfig.getMaps().stream().toList().get(new Random().nextInt(0, MapConfig.getMaps().size()));
            try{

                GameMap map = new GameMap(mapName);
                Game game = new Game(gamesCount, map, this);

                games.put(game.getId(),game);
                gamesCount++;
                gamesMenu.update();
                return game;
            } catch (Exception e){
                Bukkit.getLogger().warning("Could not find the folder for map: " + mapName);
                return null;
            }
        }
        return null;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e){
        Chunk chunk = e.getChunk();
        if (chunk.getWorld().getPersistentDataContainer().has(new NamespacedKey(TNTTag.getInstance(), "MiniSurvival"))){
            if (chunk.getX() >= 8 || chunk.getZ() >= 8 || chunk.getX() <=0 || chunk.getZ() <=0){
                chunk.unload();
            }
        }
    }

    public HashMap<String,Game> getGames() {
        return games;
    }

    public GamesMenu getGamesMenu() {
        return gamesMenu;
    }
}
