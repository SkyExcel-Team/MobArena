package me.github.mobarena.event;

import me.github.mobarena.MobArena;
import me.github.mobarena.Util.StringData;
import me.github.mobarena.data.PlayerData;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import java.io.File;
import me.github.mobarena.data.Arena;
import skyexcel.data.location.Region;

public class EntityDeathEvent implements Listener {
    @EventHandler
    public void onDeath(org.bukkit.event.entity.EntityDeathEvent event) {
        Entity entity = event.getEntity();
        Player player = event.getEntity().getKiller();

        if (entity instanceof Player) { // 플레이어가 죽었을때
            Player player1 = (Player) event.getEntity();
            PlayerData data = new PlayerData(player1);
            String arenaName = data.getArena();
            Arena arena = new Arena(arenaName, player1);

            Region region = new Region(arena.getPos1(), arena.getPos2());

            if (region.locationIsInRegion(player1.getLocation())) {

                player1.spigot().respawn();
                player1.teleport(arena.getCancelLocation());
            }
        } else {
            if (player instanceof Player) {  // 플레이어가 죽였을때

                PlayerData data = new PlayerData(player);
                String arenaName = data.getArena();

                Arena arena = new Arena(arenaName, player);
                int total = arena.getConfig().getConfig().getInt("total");
                total--;

                arena.getConfig().removeKey("total");
                StringData.sendKillMessage(player, entity, arena.getTotal());
                if (total == 0) {
                    data.nextRound();
                }
                arena.getConfig().setInteger("total",total);
            }
        }
    }

    @EventHandler
    public void Damage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        File file = new File(MobArena.plugin.getDataFolder(), "arena/");
        for (String name : file.list()) {
            Arena arena = new Arena(name);
            Region region = new Region(arena.getPos1(), arena.getPos2());
            if (region.locationIsInRegion(entity.getLocation())) {
                if (!(damager instanceof Player) && !(entity instanceof Player)) {
                    event.setCancelled(true);
                }
                if (event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
                    Arrow arrow = (Arrow) event.getDamager();
                    if (arrow.getShooter() instanceof Player) {
                        event.setCancelled(false);
                    }
                }
            }
        }
    }
}
