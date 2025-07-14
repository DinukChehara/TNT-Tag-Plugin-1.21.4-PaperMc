package me.tomqnto.tnttag.commands;

import me.tomqnto.tnttag.TNTTag;
import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.player.PlayerTempData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LeaveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player player)){
            sender.sendRichMessage("<red>Only players can use this command");
            return true;
        }

        PlayerTempData data = TNTTag.getInstance().getPlayerTempData(player);
        Game game = data.getGame();
        if (game!=null && data.isInGame()){
            game.leave(player);
            player.sendRichMessage("<green>Left game: " + game.getId());
            return true;
        }

        player.sendRichMessage("<gray>You are not in a game");

        return true;
    }
}
