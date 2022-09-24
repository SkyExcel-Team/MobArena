package me.github.mobarena.runnable;


import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.github.mobarena.Util.StringData;
import me.github.mobarena.data.Arena;
import me.github.mobarena.data.PlayerData;
import skyexcel.data.Time;

public class CoolTime extends BukkitRunnable {
    private Player player;


    private long Cooltime;

    private Time time;

    private PlayerData playerdata;
    private Arena arena;


    public CoolTime(Player player, long CoolTime, PlayerData data) {
        this.player = player;
        this.Cooltime = CoolTime;
        time = new Time(CoolTime);
        this.playerdata = data;


    }

    @Override
    public void run() {
        this.arena = new Arena(playerdata.getArena(), player);
        int total = arena.getTotal();


        if (total != 0) {
            if (time.SECOND() != 0 || time.MINUTE() != 0) {
                Cooltime = time.minSecond(1);

                StringData.sendCoolTimeMessage(player);
                playerdata.setCoolTime(Cooltime);

            } else {
                Cooltime = 0;
                cancel();
            }
        } else {

            cancel();
            if (playerdata.getCoolTime().containsKey(player.getUniqueId()))
                playerdata.getCoolTime().remove(player.getUniqueId());
        }
    }


}
