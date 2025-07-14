package me.tomqnto.tnttag.commands;

import me.tomqnto.tnttag.TNTTag;
import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpectateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (sender instanceof Player player){
            if (args.length==0){
                player.sendRichMessage("<red>Usage: /spectate <player>");
                return true;
            }
            if (!TNTTag.getInstance().getPlayerTempData(player).isInGame()){
                Player target = Bukkit.getPlayer(args[0]);
                if (target==null){
                    player.sendRichMessage("<red>Could not find " + args[0]);
                } else{
                    Game game = TNTTag.getInstance().getPlayerTempData(target).getGame();
                    if (game!=null){
                        if (game.getGameState() == GameState.WAITING || game.getGameState() == GameState.STARTING)
                            player.sendRichMessage("<red>The game has not started");
                        else if (game.getGameState() == GameState.ENDED)
                            player.sendRichMessage("<red>The game has ended");
                        else
                            game.addSpectator(player);
                    } else
                            player.sendRichMessage("<red>" + args[0] + " is not in a game");

                }
            } else {
                player.sendRichMessage("<red>This command cannot be used during a game");
            }
        } else {
            sender.sendRichMessage("<red>Only players can use this command");
        }
        return true;
    }
}
