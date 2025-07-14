package me.tomqnto.tnttag.tasks;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.MinecraftKey;
import me.tomqnto.tnttag.game.Game;
import me.tomqnto.tnttag.game.GameState;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;

import static me.tomqnto.tnttag.TNTTag.protocolManager;

public class StartingCountdown extends BukkitRunnable {

    private final Game game;
    private int time;

    public StartingCountdown(Game game, int time) {
        this.game = game;
        this.time = time;
    }

    @Override
    public void run() {
        Component title = Component.text("Starting in " + time + "s").color(NamedTextColor.YELLOW);

        if (time < 1){
            title = Component.text("Game Started!").color(NamedTextColor.YELLOW);
            game.changeGameState(GameState.STARTED);
            cancel();
        }

        if (time==20 || time == 15 || time <= 10 ){
            for (Player player : game.getPlayerList()){
                player.showTitle(Title.title(title, Component.space(), Title.Times.times(Duration.ofMillis(100),Duration.ofMillis(1000), Duration.ofMillis(100))));
            }
        }

        time--;
    }

    public static void playCountdownSound(Player player) {
        Location l = player.getLocation();
        ProtocolManager pm = protocolManager;

        PacketContainer packet = pm.createPacket(PacketType.Play.Server.NAMED_SOUND_EFFECT);
        packet.getMinecraftKeys().write(0, MinecraftKey.fromHandle("minecraft:block.note_block.pling"));
        packet.getSoundCategories().write(0, EnumWrappers.SoundCategory.MASTER);
        packet.getIntegers().write(0, (int) (l.getX() * 8));
        packet.getIntegers().write(1, (int) (l.getY() * 8));
        packet.getIntegers().write(2, (int) (l.getZ() * 8));
        packet.getFloat().write(0, 1.0f);
        packet.getFloat().write(1, 1.0f);

        try {
            pm.sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTime(){
        return time;
    }
}
