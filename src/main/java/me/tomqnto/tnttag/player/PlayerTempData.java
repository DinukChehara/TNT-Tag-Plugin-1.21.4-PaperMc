package me.tomqnto.tnttag.player;

import me.tomqnto.tnttag.game.Game;
import org.bukkit.Bukkit;

import java.util.UUID;

public class PlayerTempData {

    private UUID uuid;
    private int coinsEarned;
    private Game game;

    public PlayerTempData(){
        this.game = null;
        this.coinsEarned = 0;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isInGame() {
        return this.game!=null;
    }


    public int getCoinsEarned() {
        return coinsEarned;
    }

    public void setCoinsEarned(int coinsEarned) {
        this.coinsEarned = coinsEarned;
    }

    public Game getGame() {
        return game;
    }

    public boolean isSpectating(){
        return game != null && game.getSpectators().contains(Bukkit.getPlayer(uuid));
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
