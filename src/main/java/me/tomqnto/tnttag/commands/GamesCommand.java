package me.tomqnto.tnttag.commands;

import me.tomqnto.tnttag.TNTTag;
import me.tomqnto.tnttag.game.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
public class GamesCommand implements CommandExecutor {

    private final GameManager gameManager = TNTTag.getGameManager();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(sender instanceof Player player)) {
            sender.sendRichMessage("<red>Only players can use this command");
            return false;
        }

        gameManager.getGamesMenu().open(player);

        return true;
    }
}
