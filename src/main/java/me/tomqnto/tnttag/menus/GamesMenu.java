package me.tomqnto.tnttag.menus;

import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.game.GameManager;
import me.tomqnto.tnttag.game.GameState;
import me.tomqnto.tnttag.menus.api.Button;
import me.tomqnto.tnttag.menus.api.PagedMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class GamesMenu extends PagedMenu {

    private final GameManager gameManager;

    public GamesMenu(GameManager gameManager) {
        super(Rows.SIX, Component.text("Games"));
        this.gameManager = gameManager;
    }

    @Override
    public void onSetup() {
        HashMap<String, Game> games = gameManager.getGames();
        Button[] buttons = new Button[games.size()];

        int num = 0;
        for (String id : games.keySet()){

            Game game = games.get(id);

            if (game.getGameState() == GameState.STARTED || game.getGameState() == GameState.ENDED)
                continue;

            ItemStack item = new ItemStack(Material.MAP);
            ItemMeta meta =  item.getItemMeta();
            meta.itemName(Component.text("Game").color(NamedTextColor.GOLD));

            List<String> lore = List.of("§8" + id, "", "§ePlayers: " + game.getPlayerCount() + "/" + game.getMaxPlayers(), "§eMap: " + game.getGameMap().getName(), "§eState: " + game.getGameState().name().toLowerCase() ,"", "§e§lClick to join");

            if (game.getGameState()!=GameState.WAITING)
                meta.itemName(Component.text("Game (" + game.getGameState().name().toLowerCase() + ")").color(NamedTextColor.GRAY));

            meta.setLore(lore);
            item.setItemMeta(meta);
            if (game.getPlayerCount()>0)
                item.setAmount(game.getPlayerCount());
            else
                item.setAmount(1);

            buttons[num] = new Button(item, player1 -> player1.performCommand("join id " + id));
            num++;
        }
        addAll(buttons);

    }

}
