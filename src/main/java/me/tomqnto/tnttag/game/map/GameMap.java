package me.tomqnto.tnttag.game.map;

import me.tomqnto.tnttag.TNTTag;
import net.kyori.adventure.util.TriState;
import org.bukkit.*;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;

public class GameMap {

    private final File sourceWorldFolder;
    private final String worldName;
    private File activeWorldFolder;

    private World bukkitWorld;

    public GameMap(String worldName) {
        this.worldName = worldName;
        this.sourceWorldFolder = new File(new File(TNTTag.getInstance().getDataFolder(),"maps"), worldName);
        load();
    }

    public boolean load(){
        if (isLoaded()) return true;

        System.out.println(activeWorldFolder);
        this.activeWorldFolder = new File(
                Bukkit.getWorldContainer() ,
                sourceWorldFolder.getName() + "_active_" + System.currentTimeMillis()
        );

        try{
            FileUtils.copyDirectoryStructure(sourceWorldFolder, activeWorldFolder);
            TNTTag.getInstance().gameMaps.add(this);
            this.bukkitWorld = Bukkit.createWorld(new WorldCreator(activeWorldFolder.getName()).keepSpawnLoaded(TriState.FALSE));
            bukkitWorld.setPVP(false);
            bukkitWorld.setGameRule(GameRule.KEEP_INVENTORY, true);

            if (bukkitWorld!=null){
                this.bukkitWorld.setAutoSave(false);
                bukkitWorld.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Could not load map from source folder");
            e.printStackTrace();

            System.out.println(sourceWorldFolder);
            System.out.println(activeWorldFolder);
            System.out.println(bukkitWorld);
            return false;
        }

        return isLoaded();
    }

    public void unload(){
        if (bukkitWorld!=null) Bukkit.unloadWorld(bukkitWorld, false);
        if (activeWorldFolder!=null) {
            try {
                FileUtils.deleteDirectory(activeWorldFolder);
                TNTTag.getInstance().gameMaps.remove(this);
            } catch (IOException e) {
                Bukkit.getLogger().severe("Could not delete active map");
                e.printStackTrace();
            }
        }

        bukkitWorld = null;
        activeWorldFolder = null;
    }

    public boolean resetWorld(){
        unload();
        return load();
    }

    private boolean isLoaded() {
        return bukkitWorld!=null;
    }

    public World getBukkitWorld(){
        return bukkitWorld;
    }

    public String getName() {
        return worldName;
    }

    public Location getSpawnLocation(){
        double x = MapConfig.getSpawnCoordinates(worldName).getFirst();
        double y = MapConfig.getSpawnCoordinates(worldName).get(1);
        double z = MapConfig.getSpawnCoordinates(worldName).getLast();

        return new Location(bukkitWorld, x,y,z);
    }
}
