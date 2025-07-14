package me.tomqnto.tnttag.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class GameScoreboard extends BukkitRunnable {

    private final Game game;

    public GameScoreboard(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        for (Player player : game.getPlayerList()){
            if (player.getScoreboard().getObjective(game.getId() + "sb") != null)
                updateScoreboard(player);
            else
                createScoreboard(player);
        }
    }

    public void createScoreboard(Player player){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(game.getId() + "sb", "dummy", Component.text("Game").color(NamedTextColor.YELLOW));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        setValues(player, objective);

        player.setScoreboard(scoreboard);
    }

    public void updateScoreboard(Player player){
        Objective objective = player.getScoreboard().getObjective(game.getId() + "sb");

        assert objective != null;
        setValues(player, objective);
    }

    public Game getGame() {
        return game;
    }

    public void setValues(Player player, Objective objective) {
        Scoreboard scoreboard = objective.getScoreboard();
    }

    private void addTeamEntry(Scoreboard board, Objective obj, String teamName, String entry, int score) {
        addTeamEntry(board, obj, teamName, entry, score, null, null);
    }

    private void addTeamEntry(Scoreboard board, Objective obj, String teamName, String entry, int score, String prefix, String suffix) {
        Team team = board.getTeam(teamName);
        if (team == null) {
            team = board.registerNewTeam(teamName);
            team.addEntry(entry);
        }
        if (prefix != null) team.setPrefix(prefix);
        if (suffix != null) team.setSuffix(suffix);
        obj.getScore(entry).setScore(score);
    }




}
