package me.github.mobarena.data;


import org.bukkit.entity.Player;

import me.github.mobarena.MobArena;
import skyexcel.data.file.Config;

public class Mob {

    private String name;
    private Player player;
    private Config config;

    public Mob(String name, Player player) {
        this.name = name;
        this.player = player;

        config = new Config("mob/" + name);
        config.setPlugin(MobArena.plugin);
    }
    public void SetDropItem() {


        config.saveConfig();
    }

    public void SetType(String type) {

        config.getConfig().set("type", type);
        config.saveConfig();
    }

}
