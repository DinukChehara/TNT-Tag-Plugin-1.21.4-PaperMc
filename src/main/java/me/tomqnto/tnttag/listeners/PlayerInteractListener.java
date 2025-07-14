package me.tomqnto.tnttag.listeners;

import me.tomqnto.tnttag.TNTTag;
import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.player.PlayerTempData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        PlayerTempData data = TNTTag.getInstance().getPlayerTempData(player);

        if (data.isInGame()){
            Game game = data.getGame();
            if (game.getDeadList().contains(player))
                event.setCancelled(true);
        }
    }
}
