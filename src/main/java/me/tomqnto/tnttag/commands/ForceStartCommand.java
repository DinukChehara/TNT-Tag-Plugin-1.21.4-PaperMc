package me.tomqnto.tnttag.commands;

import me.tomqnto.tnttag.TNTTag;
import me.tomqnto.tnttag.game.GameState;
import me.tomqnto.tnttag.player.PlayerTempData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ForceStartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (sender instanceof Player player){
            if (player.isOp()){
                PlayerTempData data = TNTTag.getInstance().getPlayerTempData(player);
                if (data.isInGame()){
                    if (data.getGame().getPlayerCount()>1){
                        data.getGame().changeGameState(GameState.STARTING);
                        data.getGame().getCountdown().runTaskTimer(TNTTag.getInstance(), 0, 20);
                        data.getGame().broadcast("<gold>Someone force started this game!");
                    }else
                        player.sendRichMessage("<red>At least 2 players must in the lobby");
                } else
                    player.sendRichMessage("<red>You have to be in a game to run this command");
            } else
                player.sendRichMessage("<red>You do not have permission to run this command");
        } else
            sender.sendRichMessage("<red>Only players are allowed to run this command");

        return true;
    }
}
