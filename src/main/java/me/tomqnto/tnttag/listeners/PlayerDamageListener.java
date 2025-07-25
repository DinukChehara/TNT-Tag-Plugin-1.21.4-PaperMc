package me.tomqnto.tnttag.listeners;

import me.tomqnto.tnttag.TNTTag;
import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.player.PlayerTempData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player player){
            PlayerTempData data = TNTTag.getInstance().getPlayerTempData(player);
            if (data.isInGame()){
                event.setCancelled(true);
                event.setDamage(0);
                event.setDamage(EntityDamageEvent.DamageModifier.RESISTANCE, -1);
                if (event.getDamageSource().getCausingEntity() instanceof Player tagger){
                    data.getGame().tagPlayer(player);
                }
            }
        }
    }

}
