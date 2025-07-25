package me.tomqnto.tnttag.victorydance;

import me.tomqnto.tnttag.game.Game;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class FireworkVictoryDance extends BukkitRunnable {

    private final Game game;
    private final Player player;

    public FireworkVictoryDance(Game game) {
        this.game = game;
        this.player = game.getAliveList().getFirst();
    }

    int x = 15;
    @Override
    public void run() {
        if (x<=1 || player.getLocation().getWorld() != game.getGameMap().getBukkitWorld()){
            cancel();
            return;
        }

        Location loc = player.getLocation().add(0, 1, 0);

        Firework firework = (Firework) player.getWorld().spawnEntity(loc, EntityType.FIREWORK_ROCKET);
        FireworkMeta meta = firework.getFireworkMeta();

        meta.addEffect(FireworkEffect.builder()
                .with(FireworkEffect.Type.STAR)
                .withColor(Color.YELLOW)
                .withFade(Color.YELLOW)
                .flicker(true)
                .trail(true)
                .build());

        meta.setPower(1);
        firework.setFireworkMeta(meta);

        x--;
    }
}
