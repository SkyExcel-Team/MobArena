package me.github.mobarena.data;

import me.github.mobarena.MobArena;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Mob {

    private String name;
    private Player player;

    public Mob(String name, Player player) {
        this.name = name;
        this.player = player;
    }

    public void Create(){
        Config config = MobArena.Mob = new Config("mobs/" + name, MobArena.plugin);

        config.getConfig().set("name",name);
        config.getConfig().set("health",100);
        config.getConfig().set("speed",100);
        config.getConfig().set("damage", 100);
        config.getConfig().set("type", EntityType.SKELETON.name());
        config.getConfig().set("spawn", 50);

        config.getConfig().set("dropitem", new ItemStack(Material.AIR));
        config.getConfig().set("dropexp", 10);
        config.getConfig().set("Ai", true);

        config.saveConfig();
    }
}
