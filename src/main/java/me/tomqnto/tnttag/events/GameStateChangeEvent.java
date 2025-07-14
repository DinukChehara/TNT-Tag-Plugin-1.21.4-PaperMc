package me.tomqnto.tnttag.events;

import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GameStateChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Game game;
    private final GameState gameState;
    private final GameState previousGameState;
    private final List<Player> playerList;

    public GameStateChangeEvent(Game game, GameState gameState, GameState previousGameState, List<Player> playerList) {
        this.game = game;
        this.gameState = gameState;
        this.previousGameState = previousGameState;
        this.playerList = playerList;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public Game getGame() {
        return game;
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameState getPreviousGameState() {
        return previousGameState;
    }
}
