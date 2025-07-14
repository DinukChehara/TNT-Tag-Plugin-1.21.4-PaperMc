package me.tomqnto.tnttag;

import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.game.GameManager;
import me.tomqnto.tnttag.game.GameState;
import me.tomqnto.tnttag.game.map.MapConfig;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Actions {

    private final static GameManager gameManager = TNTTag.getGameManager();

    public static void joinGame(Player player){
        if (gameManager.getGames().isEmpty()){
            gameManager.createGame(null);
        }
        for (Game game : gameManager.getGames().values()){
            if ((game.getGameState() == GameState.WAITING) && (game.getPlayerCount() < game.getMaxPlayers())){
                game.join(player);
                break;
            } else {
                if (gameManager.getGames().values().stream().toList().getLast() == game){
                    Game newGame = gameManager.createGame(null);
                    newGame.join(player);

                }
            }
        }
    }

    public static void joinGame(Player player, Game game){
        if (game.getGameState() == GameState.WAITING){
            if (game.getPlayerCount() < game.getMaxPlayers())
                game.join(player);
            else
                player.sendRichMessage("<gray>This game is full");

        } else
            player.sendRichMessage("<gray>This game has already started");

    }

    public static void joinGameById(Player player, String id){
        HashMap<String, Game> games = gameManager.getGames();
        Game game = games.get(id);
        if (game!=null)
            joinGame(player, game);
        else
            player.sendRichMessage("<red>This game does not exist");

    }

    public static void joinGameByMap(Player player, String map){
        if (!MapConfig.getMaps().contains(map))
            player.sendRichMessage("<red>This map does not exist");
        else {
            List<Game> games = gameManager.getGames().values().stream().filter(game1 -> game1.getGameMap().getName().equals(map) && game1.getPlayerCount() < game1.getMaxPlayers()).toList();
            if (games.isEmpty())
                Objects.requireNonNull(gameManager.createGame(map)).join(player);
            else
                games.get(new Random().nextInt(0, games.size()-1)).join(player);

        }
    }
}
