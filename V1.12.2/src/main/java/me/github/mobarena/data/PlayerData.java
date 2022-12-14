package me.github.mobarena.data;

import me.github.mobarena.runnable.CoolTime;
import org.bukkit.entity.Player;

import me.github.mobarena.MobArena;
import me.github.mobarena.runnable.Delay;
import skyexcel.data.file.Config;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData {
    private HashMap<UUID, me.github.mobarena.runnable.CoolTime> CoolTime = new HashMap<>();
    private HashMap<UUID, Delay> delay = new HashMap<>();
    private Player player;
    private Config config;
    private Arena arena;
    private long cooltime;


    public PlayerData(Player player) {
        this.player = player;
        config = MobArena.PlayerData = new Config("data/" + player.getUniqueId());
        config.setPlugin(MobArena.plugin);
    }

    public void setArena(String name, int round) {
        Arena arena = new Arena(name, player);
        arena.TeleportSpawn();

        long cooltime = arena.getCoolTime(round);
        this.cooltime = cooltime;

        config.setString("arena.name", name);
        config.setInteger("arena.round", round);
        config.setLong("arena.cooltime", cooltime);
        config.saveConfig();

        if (cooltime != -1) { // 라운드에 쿨타임이 없을 경우.
            minsec();
        }
        this.arena = arena;
    }

    public void minsec() {
        CoolTime coolTime = new CoolTime(player, this.cooltime, this);

        if (!this.CoolTime.containsKey(player.getUniqueId())) {
            this.CoolTime.put(player.getUniqueId(), coolTime);
            this.CoolTime.get(player.getUniqueId()).runTaskTimer(MobArena.plugin, 0, 20);
        }
    }

    public void cancel() {
        if (!this.CoolTime.containsKey(player.getUniqueId()))
            this.CoolTime.remove(player.getUniqueId());
    }

    public void delayStart() {
        Delay delay = new Delay(player, this);

        if (!this.delay.containsKey(player.getUniqueId()))
            this.delay.put(player.getUniqueId(), delay);
        if (this.delay.containsKey(player.getUniqueId()))
            this.delay.get(player.getUniqueId()).runTaskTimer(MobArena.plugin, 0, 20);
    }


    public int getRound() {
        return config.getInteger("arena.round");
    }

    public String getArena() {
        return config.getString("arena.name");
    }


    public long getCooltime() {
        return config.getLong("arena.cooltime");
    }

    public int getTotal() {
        Arena arena = new Arena(getArena(), player);
        return arena.getTotal();
    }

    public void nextRound() {
        String name = getArena();
        Arena arena = new Arena(name, player);
        int round = config.getInteger("arena.round");

        if (arena.getCoolTime(getRound()) != -1) {
            CoolTime coolTime = new CoolTime(player, this.cooltime, this);
            if (!this.CoolTime.containsKey(player.getUniqueId())) {
                this.CoolTime.put(player.getUniqueId(), coolTime);
                this.CoolTime.get(player.getUniqueId()).runTaskTimer(MobArena.plugin, 0, 20);
            }
        }
        config.setInteger("arena.round", round + 1);
        config.saveConfig();

        arena.SpawnAtRound(getArena(), getRound()); 
    }

    public void setDelay(long delay) {
        if (config.getConfig().get("arena.delay") != null)
            config.setLong("arena.delay", delay);
        config.saveConfig();
    }

    public long getDelay() {
        return config.getLong("arena.delay");
    }

    public void setCoolTime(long Cooltime) {
        config.getConfig().set("arena.cooltime", Cooltime);
        config.saveConfig();
    }

    public void init() {
        if (!config.isFileExist()) {
            config.getConfig().set("options.particle", false);
            config.getConfig().set("player", player.getDisplayName());
            config.getConfig().set("arena.name", "");
            config.setLong("arena.cooltime", (long) -1);
            config.setLong("arena.round", (long) -1);
            config.saveConfig();
        }
    }

    public HashMap<UUID, CoolTime> getCoolTime() {
        return CoolTime;
    }

}
