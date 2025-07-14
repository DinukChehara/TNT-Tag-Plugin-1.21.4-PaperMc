package me.tomqnto.tnttag.commands;

import me.tomqnto.tnttag.Actions;
import me.tomqnto.tnttag.TNTTag;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JoinCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (TNTTag.getInstance().getPlayerTempData(player).isInGame()) {
                player.sendRichMessage("<red>This command cannot be used during a game");
                return true;
            }

            if (args.length == 0) {
                Actions.joinGame(player);

                return true;
            }

            if (args.length < 2) {
                player.sendRichMessage("<red>Missing arguments<br>Usage: /join <id | map> <id | map name>");
                return true;
            }

            if (!(args[0].equals("id") || args[0].equals("map"))) {
                player.sendRichMessage("<red>Invalid arguments<br>Usage: /join <id | map> <id | map name>");
                return true;
            }

            if (args[0].equals("id")) {
                String id = args[1];
                Actions.joinGameById(player, id);
                return true;

            } else{
                StringBuilder strBuilder = new StringBuilder();
                for (int x = 1; x<args.length;x++)
                    strBuilder.append(args[x] + " ");
                String map = strBuilder.toString().stripTrailing();

                Actions.joinGameByMap(player, map);
                return true;
            }
        } else{
            sender.sendRichMessage("<red>Only players can run this command");
        }
        return true;
    }
}
