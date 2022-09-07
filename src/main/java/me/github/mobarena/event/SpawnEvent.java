package me.github.mobarena.event;

import me.github.mobarena.MobArena;
import me.github.mobarena.data.Arena;
import data.Region;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import java.io.File;

public class SpawnEvent implements Listener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        Location loc = entity.getLocation();


        File file = new File(MobArena.plugin.getDataFolder(), "arena/");

        World w = entity.getWorld();


        for (String name : file.list()) {
            Arena arena = new Arena(name);
            Region region = new Region(arena.getPos1(), arena.getPos2());
            if (region.locationIsInRegion(loc)) {
                if (!entity.getType().equals(EntityType.DROPPED_ITEM)) {
                    System.out.println(arena.getMob());
                }
            }
        }
    }
}
