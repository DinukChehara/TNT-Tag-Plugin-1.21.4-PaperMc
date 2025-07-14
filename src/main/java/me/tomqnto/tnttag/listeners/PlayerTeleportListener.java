package me.tomqnto.tnttag.listeners;

import me.tomqnto.tnttag.TNTTag;
import me.tomqnto.tnttag.player.PlayerTempData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        PlayerTempData data = TNTTag.getInstance().getPlayerTempData(event.getPlayer());
        if (data.isInGame() && event.getTo().getWorld()!=data.getGame().getGameMap().getBukkitWorld()){
            event.getPlayer().sendRichMessage("<red>Cannot teleport to a different world during a game");
            event.setCancelled(true);
        }
    }

}
