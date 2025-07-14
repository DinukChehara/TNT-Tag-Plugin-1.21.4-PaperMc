package me.tomqnto.tnttag.commands;

import me.tomqnto.tnttag.TNTTag;
import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.game.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayersCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (sender instanceof Player player){
            Game game = TNTTag.getInstance().getPlayerTempData(player).getGame();
            if (game == null)
                sender.sendRichMessage("<red>You need to be in a game to run this command");
            else if (game.getGameState()!= GameState.STARTED)
                player.sendRichMessage("<red>You can't use that command at this game state");
            else
                game.getPlayerMenu().open(player);
        } else
            sender.sendRichMessage("<red>Only players can use this command");

        return true;
    }
}
