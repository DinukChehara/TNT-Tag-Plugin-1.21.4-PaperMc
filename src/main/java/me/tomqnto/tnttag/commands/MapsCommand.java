package me.tomqnto.tnttag.commands;

import me.tomqnto.tnttag.game.map.MapConfig;
import me.tomqnto.tnttag.menus.MapsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MapsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player player)){
            List<String> maps = MapConfig.getMaps().stream().toList();
            StringBuilder message = new StringBuilder();
            for (String map : maps) {
                if (maps.getLast().equals(map))
                    message.append(map);
                else
                    message.append(map).append(", ");
            }
            sender.sendRichMessage("<gold>Maps: <yellow>" + message.toString().stripTrailing());
            return true;
        }

        new MapsMenu().open(player);

        return true;
    }
}
