package me.tomqnto.tnttag.menus;

import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.menus.api.Button;
import me.tomqnto.tnttag.menus.api.PagedMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class PlayerMenu extends PagedMenu {

    private final Game game;

    public PlayerMenu(Game game) {
        super(Rows.FOUR, Component.text("Player List"));
        this.game = game;
    }

    @Override
    public void onSetup() {
        Button[] buttons = new Button[game.getPlayerList().size()];
        for (int x = 0; x<game.getPlayerList().size();x++){
            Player player = game.getPlayerList().get(x);

            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();

            if (player==game.getTaggedPlayer())
                meta.displayName(MiniMessage.miniMessage().deserialize("<red>" + player.getName()).decoration(TextDecoration.ITALIC, false));
            else
                meta.displayName(player.name().color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
            
            if (game.getDeadList().contains(player))
                meta.setLore(List.of("§cDead"));
            else
                meta.setLore(List.of("§aAlive"));
            meta.setOwningPlayer(player);
            skull.setItemMeta(meta);

            Button button = new Button(skull, player1 -> {});
            buttons[x] = button;
        }

        addAll(buttons);
    }
}
