package me.tomqnto.tnttag.listeners;

import me.tomqnto.tnttag.TNTTag;
import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.game.GameState;
import me.tomqnto.tnttag.player.PlayerTempData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.time.Duration;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getPlayer();
        PlayerTempData data = TNTTag.getInstance().getPlayerTempData(player);

        if (data.isInGame()) {
            Game game = data.getGame();
            if (game.getGameState() == GameState.STARTED) {

                game.getDeadList().add(player);
                game.addSpectator(player);
                game.getPlayerMenu().update();
                player.setGameMode(GameMode.SPECTATOR);
                player.getInventory().clear();

                if (game.getTaggedPlayer() != player)
                    game.broadcast("<gold>" + player.getName() + " <red>died");
                else {
                    game.broadcast("<gold><bold>" + player.getName() + " <red>blew up");
                }

                player.showTitle(Title.title(Component.text("You died").color(NamedTextColor.RED).decorate(TextDecoration.BOLD), Component.space(), Title.Times.times(Duration.ofMillis(500), Duration.ofMillis(3000), Duration.ofMillis(300))));

                if (game.getAliveList().size()==1){
                    game.changeGameState(GameState.ENDED);
                    game.endGame();
                } else if (game.getAliveList().size()>1)
                    game.tagRandomly();

                event.setCancelled(true);


            } else
                event.setCancelled(true);
        }
    }

}
