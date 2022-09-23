package java.me.github.mobarena.data;

import data.Config;
import org.bukkit.Location;

import java.me.github.mobarena.MobArena;

public class Area {

    private String name;
    private Location pos1;
    private Location pos2;

    private Config config;

    public Area() {

    }

    public Area(String name) {
        this.name = name;
        this.config = MobArena.ArenaConfig = new Config("arena/" + this.name + "/" + this.name);
    }

    public Area(String name, Location pos1, Location pos2) {
        this.name = name;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.config = MobArena.ArenaConfig = new Config("arena/" + this.name + "/" + this.name);
    }

    public void saveData() {
        config.setLocation("arena.pos1", pos1);
        config.setLocation("arena.pos2", pos2);
        config.saveConfig();
    }

    public Location getPos1() {
        return config.getLocation("arena.pos1");
    }

    public Location getPos2() {
        return config.getLocation("");
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }
}
