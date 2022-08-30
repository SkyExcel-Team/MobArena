package me.github.mobarena.data;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.math.BlockVector3;
import me.github.mobarena.MobArena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private String name;

    private Player player;

    public Arena(String name, Player player) {
        this.name = name;
        this.player = player;
    }

    public void Create() throws IncompleteRegionException {
        Config config = MobArena.ArenaConfig = new Config("arena/" + name, MobArena.plugin);

        if (MobArena.WorldEdit.getSession(player) != null) {
            MobArena.WorldEdit.getSession(player).getSelection().getMaximumPoint();

            BlockVector3 pos1 = MobArena.WorldEdit.getSession(player).getSelection().getBoundingBox().getPos1();
            BlockVector3 pos2 = MobArena.WorldEdit.getSession(player).getSelection().getBoundingBox().getPos2();

            Location location1 = new Location(player.getWorld(), pos1.getX(), pos1.getY(), pos1.getZ());

            Location location2 = new Location(player.getWorld(), pos2.getX(), pos2.getY(), pos2.getZ());
            if (config.isExist()) {
                player.sendMessage("§c해당 컨피그는 이미 존재 합니다! 편집을 해주세요!");
            } else {
                config.getConfig().set("arena.name", name);


                List<String> list = new ArrayList<>();

                list.add("test");
                list.add("test");
                list.add("test");
                config.getConfig().set("arena.round.1", "");

                config.getConfig().set("arena.round." + 1 + ".mobs", list);
                config.getConfig().set("arena.round." + 1 + ".potion", PotionType.POISON.name());
                config.getConfig().set("arena.round." + 1 + ".cooltime", -1);
                config.getConfig().set("arena.round." + 1 + ".item", new ItemStack(Material.DIAMOND_SWORD).getType().name());
                config.getConfig().set("arena.pos1.x", location1.getX());
                config.getConfig().set("arena.pos1.y", location1.getY());
                config.getConfig().set("arena.pos1.z", location1.getZ());

                config.getConfig().set("arena.pos2.x", location2.getX());
                config.getConfig().set("arena.pos2.y", location2.getY());
                config.getConfig().set("arena.pos2.z", location2.getZ());

                config.getConfig().set("arena.spawn", null);
                config.saveConfig();
                player.sendMessage("" + config.getConfig().getConfigurationSection("arena.round").getKeys(true).size());
            }
        } else {
            player.sendMessage("위치를 지정해 주세요!");
        }
    }

    public void SetSpawn() {
        Config config = MobArena.ArenaConfig = new Config("arena/" + name, MobArena.plugin);
        if (config.isExist()) {
            config.getConfig().set("arena.spawn.world", player.getWorld().getName());
            config.getConfig().set("arena.spawn.x", player.getLocation().getX());
            config.getConfig().set("arena.spawn.y", player.getLocation().getY());
            config.getConfig().set("arena.spawn.z", player.getLocation().getZ());
            player.sendMessage("§a자신의 위치로 스폰을 설정하였습니다!");
            config.saveConfig();
        }
    }

    public Location getSpawn() {
        Config config = MobArena.ArenaConfig = new Config("arena/" + name, MobArena.plugin);
        if (config != null)
            return new Location(Bukkit.getWorld(config.getConfig().getString("arena.spawn.world")),
                    config.getConfig().getDouble("arena.spawn.x"),
                    config.getConfig().getDouble("arena.spawn.y"),
                    config.getConfig().getDouble("arena.spawn.z"));
        else {
            return null;
        }
    }

    public void setArena() {
        Config config = MobArena.PlayerData = new Config("data/" + player.getUniqueId(), MobArena.plugin);
        if (config.isExist()) {
            config.getConfig().set("name", name);
        } else {
            config.getConfig().set("name", name);
        }

        config.saveConfig();
    }

    public void Monster() {
        Config config = MobArena.ArenaConfig = new Config("arena/" + name, MobArena.plugin);
        player.sendMessage( config.getConfig().getStringList("arena.round.1.mobs") + "");
    }


}
