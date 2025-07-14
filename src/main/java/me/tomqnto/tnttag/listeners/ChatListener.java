package me.tomqnto.tnttag.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.tomqnto.tnttag.TNTTag;
import me.tomqnto.tnttag.player.PlayerTempData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event){
        Player player = event.getPlayer();
        PlayerTempData playerData = TNTTag.getInstance().getPlayerTempData(player);

        if (playerData.isInGame()){
            event.viewers().clear();
            event.viewers().addAll(playerData.getGame().getPlayerList());

            event.renderer(((sender, name, message, audience) ->
                    Component.text("[GAME] ", NamedTextColor.GOLD).append(name.color(NamedTextColor.YELLOW)).append(Component.text(": ").color(NamedTextColor.YELLOW)).append(message.color(NamedTextColor.YELLOW))
                    ));

        }
    }

}
