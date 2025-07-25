package me.tomqnto.tnttag.tasks;

import me.tomqnto.tnttag.game.Game;
import org.bukkit.scheduler.BukkitRunnable;

public class EndGameCountdown extends BukkitRunnable {

    private final Game game;

    public EndGameCountdown(Game game) {
        this.game = game;
    }

    int x = 15;
    @Override
    public void run() {
        if (x<=0) {
            game.delete();
            cancel();
            return;
        }
        if (x<6)
            game.broadcast("<red>Game closing in " + x + "s");
        x--;
    }
}
