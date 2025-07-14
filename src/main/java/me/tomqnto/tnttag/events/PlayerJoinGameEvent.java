package me.tomqnto.tnttag.events;

import me.tomqnto.tnttag.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinGameEvent extends Event {

    private final HandlerList handlers = new HandlerList();

    private final Game game;
    private final Player player;

    public PlayerJoinGameEvent(Game game, Player player) {
        this.game = game;
        this.player = player;
    }


    public HandlerList getHandlerList(){
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }
}
