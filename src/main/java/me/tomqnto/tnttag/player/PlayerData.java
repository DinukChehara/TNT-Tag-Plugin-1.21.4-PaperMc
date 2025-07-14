package me.tomqnto.tnttag.player;

import me.tomqnto.tnttag.TNTTag;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PlayerData {

    private final static File file = new File(TNTTag.getInstance().getDataFolder(), "player_data.yml");
    private final static YamlConfiguration config;

    static {
        if (!file.exists())
            TNTTag.getInstance().saveResource("player_data.yml", false);

        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void saveConfig(){
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getKills(Player player){
        return config.getInt(player.getName() + ".kills");
    }

    public static void addKill(Player player){
        config.set(player.getName() + ".kills", getKills(player) + 1);
        saveConfig();
    }


}
