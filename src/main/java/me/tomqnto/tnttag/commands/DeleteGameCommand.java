package me.tomqnto.tnttag.commands;

import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DeleteGameCommand implements CommandExecutor {

    private final GameManager gameManager;

    public DeleteGameCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (sender instanceof ConsoleCommandSender || sender.isOp()){
            if (args.length==0)
                sender.sendRichMessage("<red>You must specify the game id");
            else {
                String id = args[0];
                if (gameManager.getGames().containsKey(id)){
                    Game game = gameManager.getGames().get(id);
                    for (Player player : game.getPlayerList()){
                        try {
                            player.sendRichMessage("<bold><red>This game has been deleted<br>Kicking every player");
                            game.leave(player);
                        } catch (Exception e){
                            Bukkit.getLogger().warning("Could not remove player: " + player.getName() + " from the game: " + id);
                        }
                    }
                    game.delete();
                    sender.sendRichMessage("<green>Successfully deleted game: " + id);
                } else
                    sender.sendRichMessage("<red>This game does not exist");
            }
        } else
            sender.sendRichMessage("<red>You do not have permission to run this command");

        return true;
    }
}
