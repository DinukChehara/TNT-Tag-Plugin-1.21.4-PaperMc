package me.tomqnto.tnttag.tasks;

import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.game.GameState;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class TNTTask20Ticks extends BukkitRunnable {

    private final int countdown;
    private final Game game;
    private int timeleft;

    public TNTTask20Ticks(int countdown, Game game) {
        this.countdown = countdown;
        this.game = game;
        this.timeleft = countdown;
    }

    @Override
    public void run() {
        if (game.getGameState() == GameState.STARTED){
            for (Player player : game.playersInGame()){
                player.sendActionBar(MiniMessage.miniMessage().deserialize("<red>T<white>N<red>T<white> exploding in <red>" + timeleft + "s"));
            }
            if (timeleft == 0){
                game.getTaggedPlayer().setHealth(0);
                game.getGameMap().getBukkitWorld().createExplosion(game.getTaggedPlayer().getLocation().add(0, 2,0), -1f, false, false);
                game.getTaggedPlayer().setGameMode(GameMode.SPECTATOR);
                if (game.getAliveList().size()==1){
                    cancel();
                    return;
                }
                timeleft = countdown;
                game.tagRandomly();
            }

            if (timeleft<11){
                game.broadcast("<red>T<white>N<red>T <white>explodes in <yellow>" + timeleft + "s");
            }
            game.getTaggedPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
        }

        timeleft--;
    }

    public int getTimeLeft() {
        return timeleft;
    }

    public void setTimeLeft(int time){
        timeleft = time;
    }

    public int getCountdown(){
        return countdown;
    }
}
