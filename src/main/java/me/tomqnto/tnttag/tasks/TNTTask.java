package me.tomqnto.tnttag.tasks;

import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.game.GameState;
import org.bukkit.GameMode;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class TNTTask extends BukkitRunnable {

    private final int countdown;
    private final Game game;
    private int timeleft;

    public TNTTask(int countdown, Game game) {
        this.countdown = countdown;
        this.game = game;
        this.timeleft = countdown;
    }

    @Override
    public void run() {
        if (game.getGameState() == GameState.STARTED){
            if (timeleft == 0){
                game.getTaggedPlayer().setHealth(0);
                game.getGameMap().getBukkitWorld().createExplosion(game.getTaggedPlayer(), -1f, false, false);
                game.getTaggedPlayer().setGameMode(GameMode.SPECTATOR);
                if (game.getAliveList().size()==1){
                    cancel();
                    return;
                }
                timeleft = countdown;
                game.tagRandomly();
            }

            if (timeleft<10){
                game.broadcast("<red>T<white>N<red>T <white>explodes in <yellow>" + timeleft + "s");
            }
            game.getTaggedPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
        }

        timeleft--;
    }

    public int getCountdown() {
        return countdown;
    }
}
