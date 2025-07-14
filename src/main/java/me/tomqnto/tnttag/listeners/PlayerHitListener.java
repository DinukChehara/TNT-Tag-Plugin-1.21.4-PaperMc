package me.tomqnto.tnttag.listeners;

import me.tomqnto.tnttag.TNTTag;
import me.tomqnto.tnttag.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerHitListener implements Listener {

    @EventHandler
    public void onHit(EntityDamageEvent event){
        if (event.getEntity() instanceof Player player){
            if (event.getDamageSource().getCausingEntity() instanceof Player tagger){
                if (TNTTag.getInstance().getPlayerTempData(tagger).isInGame()){
                    Game game = TNTTag.getInstance().getPlayerTempData(tagger).getGame();
                    if (game.getTaggedPlayer() == tagger){
                        game.tagPlayer(player);
                        tagger.sendRichMessage("<gold>You tagged <yellow>" + player.getName());
                        event.setDamage(-1);
                    }
                }
            }
        }
    }

}
