package me.tomqnto.tnttag.listeners;

import me.tomqnto.tnttag.TNTTag;
import me.tomqnto.tnttag.events.GameStateChangeEvent;
import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.game.GameState;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class GameStateChangeListener implements Listener {

    @EventHandler
    public void onStateChange(GameStateChangeEvent event){

        List<Player> players = event.getPlayerList();
        Game game = event.getGame();
        GameState state = event.getGameState();
        GameState previousState = event.getPreviousGameState();

        if (state == GameState.WAITING && previousState == GameState.STARTING){
            game.broadcast("<gray>Not enough players to start");
            for (Player player : players){
                player.clearTitle();
                player.showTitle(Title.title(Component.text("Not enough players to start").color(NamedTextColor.GRAY), Component.space()));
                player.getInventory().clear();
            }
        }

        if (state == GameState.STARTED) {
            game.getGameMap().getBukkitWorld().setPVP(true);
            game.broadcast("<yellow>Game started!");
            game.tagRandomly();
            game.getTntTask().runTaskTimer(TNTTag.getInstance(), 0, 20);
        }

        if (state == GameState.ENDED){
            game.getGameMap().getBukkitWorld().setPVP(false);
        }

    }

}
