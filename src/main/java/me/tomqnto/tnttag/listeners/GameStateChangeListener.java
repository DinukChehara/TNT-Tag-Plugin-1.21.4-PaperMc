package me.tomqnto.tnttag.listeners;

import me.tomqnto.tnttag.TNTTag;
import me.tomqnto.tnttag.events.GameStateChangeEvent;
import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.game.GameState;
import me.tomqnto.tnttag.victorydance.FireworkVictoryDance;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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
            game.broadcastTitle(Component.text("Not enough players to start").color(NamedTextColor.GRAY), Component.space(), true);
        }

        if (state == GameState.STARTED) {
            game.getTNT20TicksTask().runTaskTimer(TNTTag.getInstance(), 0, 20);
            game.getGameMap().getBukkitWorld().setPVP(true);
            game.broadcast("<yellow>Game started!");
            game.tagRandomly();
            game.broadcastTitle(Component.text(game.getTaggedPlayer().getName()).color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD), Component.text("was tagged!").color(NamedTextColor.RED), true);
        }

        if (state == GameState.ENDED){
            FireworkVictoryDance dance = new FireworkVictoryDance(game);
            dance.runTaskTimer(TNTTag.getInstance(), 0, 20);
            game.getGameMap().getBukkitWorld().setPVP(false);
        }

    }

}
