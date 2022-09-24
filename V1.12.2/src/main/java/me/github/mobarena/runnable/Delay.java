package me.github.mobarena.runnable;


import me.github.mobarena.MobArena;
import me.github.mobarena.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import skyexcel.data.Time;
import skyexcel.data.file.Config;

public class Delay extends BukkitRunnable {

    private Player player;
    private Config config;

    private PlayerData data;

    private long configdelay;

    private Time time;

    public Delay(Player player, PlayerData data) {
        this.player = player;
        config = MobArena.config;
        this.data = data;
        configdelay = config.getLong("next-round-delay");
        time = new Time(configdelay);
        data.setDelay(configdelay);

    }


    @Override
    public void run() {
        if(time.SECOND() != -1){
            if (time.SECOND() != 1) {
                time.minSecond(1);
                System.out.println(time.SECOND());
            } else{
                data.nextRound();

                cancel();
            }
        }
    }

    public long getConfigDelay() {
        return configdelay;
    }
}
