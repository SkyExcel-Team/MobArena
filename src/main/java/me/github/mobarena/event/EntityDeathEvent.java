package me.github.mobarena.event;

import me.github.mobarena.MobArena;
import me.github.mobarena.data.Config;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class EntityDeathEvent implements Listener {
    @EventHandler
    public void onDeath(org.bukkit.event.entity.EntityDeathEvent event) {
        Entity entity = event.getEntity();
        Player player = event.getEntity().getKiller();
        String name = entity.getCustomName();
        if(player instanceof  Player){
            Config mob = MobArena.Mob = new Config("mobs/" + name, MobArena.plugin);
            if (mob.isExist()) {
                event.getDrops().clear();
                event.getDrops().add(mob.getConfig().getObject("dropitem", ItemStack.class));
            }
        }
    }
}
