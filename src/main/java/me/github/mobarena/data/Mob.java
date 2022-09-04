package me.github.mobarena.data;

import me.github.mobarena.MobArena;
import me.github.skyexcelcore.data.Config;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Mob {

    private String name;
    private Player player;

    public Mob(String name, Player player) {
        this.name = name;
        this.player = player;
    }

    public void Create() {
        Config config = new Config("mob/" + name);
        ConfigurationSection round = config.getConfig().createSection("arena.round." + config.getConfig().getConfigurationSection("arena.round").getKeys(false).size());
        config.setSection(round);
        if (config.getConfig().getConfigurationSection("arena.round") != null) {
            round.set("round.spawn", null);
            round.set("round.amount", null);

        } else {
            config.getConfig().set("name", name);
            config.getConfig().set("round.spawn", null);
            config.getConfig().set("round.amount", null);


        }
        config.saveConfig();
    }

    public void SetDropItem() {
        Config config = MobArena.Mob = new Config("mobs/" + name);
        config.getConfig().set("dropitem", player.getInventory().getItemInMainHand());
        config.saveConfig();
    }

    public void SetType(String type) {

        Config config = MobArena.Mob = new Config("mobs/" + name);
        config.getConfig().set("type", type);
        config.saveConfig();
    }

    public Location getMonsterSpawn(int index, String type) {
        Config mob = MobArena.Mob = new Config("arena/" + this.name + "/mob/" + type);
        ConfigurationSection section = mob.getConfig().getConfigurationSection("round." + index);

        return (Location) section.get("spawn");
    }
}
