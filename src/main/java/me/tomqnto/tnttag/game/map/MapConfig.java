package me.tomqnto.tnttag.game.map;

import me.tomqnto.tnttag.TNTTag;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapConfig {

    private final static File file = new File(TNTTag.getInstance().getDataFolder(), "map_data.yml");
    private final static YamlConfiguration config;

    static {
        if (!file.exists())
            TNTTag.getInstance().saveResource("map_data.yml", false);

        config = YamlConfiguration.loadConfiguration(file);
        config.options().setHeader(List.of(
                "map folder name: <-- this will be the display name too",
                "           spawn-location = [x,y,z] <-- teleport location when a player joins the game"
        ));
    }

    public static void saveConfig(){
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Set<String> getMaps(){
        Set<String> maps = new HashSet<>();
        for (String map : config.getKeys(false)){
            File mapFolder = new File(new File(TNTTag.getInstance().getDataFolder(),"maps"), map);
            if (mapFolder.exists())
                maps.add(map);
        }
        return maps;
    }

    public static List<Double> getSpawnCoordinates(String map){
        return config.getDoubleList(map + ".spawn-location");
    }
}
