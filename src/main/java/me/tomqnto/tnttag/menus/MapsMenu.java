package me.tomqnto.tnttag.menus;

import me.tomqnto.tnttag.game.map.MapConfig;
import me.tomqnto.tnttag.menus.api.Button;
import me.tomqnto.tnttag.menus.api.PagedMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MapsMenu extends PagedMenu {

    public MapsMenu() {
        super(Rows.SIX, Component.text("Maps in rotation"));
    }

    @Override
    public void onSetup() {
        Button[] buttons = new Button[MapConfig.getMaps().size()];
        int x = 0;
        for (String map : MapConfig.getMaps()){
            ItemStack item = new ItemStack(Material.MAP);
            ItemMeta meta = item.getItemMeta();
            meta.itemName(Component.text(map).color(NamedTextColor.GOLD));
            meta.setLore(List.of("", "§eClick to join"));
            item.setItemMeta(meta);

            Button button = new Button(item, player -> player.performCommand("join map " + map));

            buttons[x] = button;
            x++;
            }
        addAll(buttons);
    }
}
