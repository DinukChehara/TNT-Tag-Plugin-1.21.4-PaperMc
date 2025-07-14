package me.tomqnto.tnttag.game;

import me.tomqnto.tnttag.TNTTag;
import me.tomqnto.tnttag.events.GameStateChangeEvent;
import me.tomqnto.tnttag.game.map.GameMap;
import me.tomqnto.tnttag.menus.PlayerMenu;
import me.tomqnto.tnttag.tasks.EndGameCountdown;
import me.tomqnto.tnttag.tasks.StartingCountdown;
import me.tomqnto.tnttag.tasks.TNTTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Game implements Listener {

    private GameState gameState = GameState.WAITING;
    private final int gameNumber;
    private final GameMap map;
    private final List<Player> playerList = new ArrayList<>();
    private final int maxPlayers = 4;
    private final int minPlayers = 2;
    private StartingCountdown countdown;
    private final List<Player> deadList = new ArrayList<>();
    private final PlayerMenu playerMenu = new PlayerMenu(this);
    private final List<Player> spectators = new ArrayList<>();
    private final GameManager gameManager;
    private final GameScoreboard gameScoreboard = new GameScoreboard(this);
    private Player taggedPlayer;
    private final TNTTask tntCountdown = new TNTTask(35, this);

    public Game(int gameNumber, GameMap map, GameManager gameManager) {
        this.gameNumber = gameNumber;
        this.map = map;
        this.gameManager = gameManager;
        this.countdown = new StartingCountdown(this, 20);
        gameScoreboard.runTaskTimer(TNTTag.getInstance(), 0, 20);
        Bukkit.getServer().getPluginManager().registerEvents(this, TNTTag.getInstance());
    }


    public List<Player> getPlayerList() {
        return playerList;
    }

    public String getId(){
        return "MS" + gameNumber;
    }

    public int getPlayerCount(){
        return playerList.size();
    }

    public GameState getGameState(){
        return gameState;
    }

    public int getMaxPlayers(){return maxPlayers;}


    public void join(Player player){

        if  (map.getBukkitWorld()==null){
            player.sendRichMessage("<red><bold>Error: </bold>World could not load");
            return;
        }

        playerList.add(player);
        TNTTag.getInstance().getPlayerTempData(player).setGame(this);

        Location spawnLocation = map.getSpawnLocation();
        player.teleport(spawnLocation);

        broadcast("<green>" + player.getName() + " joined | (" + getPlayerCount() + "/" + maxPlayers + ")");

        // handling countdown as players join
        if (getPlayerCount()==minPlayers) {
            changeGameState(GameState.STARTING);
            try {
                countdown.cancel();
                countdown = new StartingCountdown(this, 20);
                countdown.runTaskTimer(TNTTag.getInstance(), 0, 20);
            } catch (Exception e) {
                countdown.runTaskTimer(TNTTag.getInstance(), 0, 20);
            }
        }

        if (getPlayerCount()==maxPlayers){
            countdown.cancel();
            countdown = new StartingCountdown(this, 10);
            countdown.runTaskTimer(TNTTag.getInstance(), 0, 10);
        }
    }

    public void leave(Player player){
        player.setGameMode(GameMode.SURVIVAL);
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        TNTTag.getInstance().getPlayerTempData(player).setGame(null);

        if (gameState==GameState.STARTED){
            player.setHealth(0);
            if (spectators.contains(player)){
                spectators.remove(player);

            } else {
                deadList.add(player);
            }

            if (getAliveList().size()==1){
                changeGameState(GameState.ENDED);
                endGame();
            }

        } else{
            playerList.remove(player);
        }

        player.teleport(Bukkit.getWorld("world").getSpawnLocation());

        if (getPlayerCount()==0)
            delete();

        if (getPlayerCount()<minPlayers && gameState==GameState.STARTING){
            changeGameState(GameState.WAITING);
            countdown.cancel();
        }

        if (gameState==GameState.WAITING || gameState==GameState.STARTING)
            broadcast("<gray>" + player.getName() + " left | (" + getPlayerCount() + "/" + maxPlayers + ")");
        else if (!spectators.contains(player))
            broadcast("<gray>" + player.getName() + " disconnected");

    }

    public void addSpectator(Player player){
        if (!map.getBukkitWorld().getPlayers().contains(player))
            player.teleport(map.getSpawnLocation());
        spectators.add(player);
        player.setGameMode(GameMode.SPECTATOR);
        player.sendRichMessage("<gray><italic>You are spectating this game");
    }

    public List<Player> getAliveList(){
        List<Player> tempList = playerList;
        tempList.removeAll(deadList);
        return tempList;
    }


    public void delete(){

        for (Player player : map.getBukkitWorld().getPlayers()){
            player.getInventory().clear();
            player.setHealth(0);
            TNTTag.getInstance().getPlayerTempData(player).setGame(null);
        }

        playerList.clear();
        deadList.clear();
        spectators.clear();

        map.unload();
        TNTTag.getGameManager().getGames().remove(getId());
        gameManager.getGamesMenu().update();
    }

    public void changeGameState(GameState newGameState){
        Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(this, newGameState, gameState, playerList));
        gameState = newGameState;
    }

    public GameMap getGameMap(){
        return map;
    }

    public List<Player> playersInGame(){
        List<Player> players = new ArrayList<>();
        players.addAll(getAliveList());
        players.addAll(spectators);
        return players;
    }

    public void broadcast(String message){
        for (Player player : playersInGame()){
            player.sendRichMessage(message);
        }
    }

    public void broadcast(Component message){
        for (Player player : playersInGame()){
            player.sendMessage(message);
        }
    }

    public StartingCountdown getCountdown() {
        return countdown;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public List<Player> getDeadList() {
        return deadList;
    }

    public PlayerMenu getPlayerMenu() {
        return playerMenu;
    }

    public List<Player> getSpectators() {
        return spectators;
    }

    public GameScoreboard getGameScoreboard() {
        return gameScoreboard;
    }

    public void endGame(){
        getTntTask().cancel();
        getGameScoreboard().cancel();
        broadcast("<gold><bold>GAME ENDED");
        broadcast("<gold><bold>" + getAliveList().getFirst().getName() + " won!");

        for (Player player : playersInGame()){
            player.clearTitle();
            if (getAliveList().contains(player))
                player.showTitle(Title.title(Component.text("You Won!").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD), Component.space()));
            else
                player.showTitle(Title.title(Component.text("Gave Over!").color(NamedTextColor.RED).decorate(TextDecoration.BOLD), Component.space()));
        }

        EndGameCountdown endCountdown = new EndGameCountdown(this);
        endCountdown.runTaskTimer(TNTTag.getInstance(), 0, 20);
    }

    public void tagPlayer(Player player){
        player.getInventory().setHelmet(new ItemStack(Material.TNT));
        if (taggedPlayer!=null)
            taggedPlayer.getInventory().setHelmet(null);
        taggedPlayer = player;
        player.clearTitle();
        player.showTitle(Title.title(Component.text("You have been tagged").color(NamedTextColor.RED).decorate(TextDecoration.BOLD), Component.space()));
        broadcast( "<gold>" + player.getName() + "<red> was tagged!");
    }

    public Player getTaggedPlayer() {
        return taggedPlayer;
    }

    public void tagRandomly(){
        Player random =  getAliveList().get(new Random().nextInt(0,getAliveList().size()));
        tagPlayer(random);
    }

    public TNTTask getTntTask() {
        return tntCountdown;
    }
}
