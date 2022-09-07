package me.github.mobarena.event;

import me.github.mobarena.MobArena;
import me.github.mobarena.Util.StringData;
import me.github.mobarena.data.Arena;
import me.github.mobarena.data.PlayerData;
import data.Config;
import data.Region;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class EntityDeathEvent implements Listener {

    private HashMap<UUID, Location> deathlocation = new HashMap<>();

    @EventHandler
    public void onDeath(org.bukkit.event.entity.EntityDeathEvent event) {
        Entity entity = event.getEntity();
        Player player = event.getEntity().getKiller();
        String name = entity.getCustomName();

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
                int round = data.getRound();
                Arena arena = new Arena(arenaName, player);

                if (arena.minTotal(round)) {
                    StringData.sendKillMessage(player, entity, arena.getTotal());
                    if (arena.getTotal() == 0) {
                        long i = arena.getCoolTime(round);
                        if (i == -1) {
                            data.nextRound();
                            StringData.sendNextRoundMessage(player);
                        }
                    }

                }
            }
        }
    }

    @EventHandler
    public void RespawnEvent(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

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

    private CraftItemStack craftItemStack;

    @EventHandler(priority = EventPriority.HIGH)
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();


    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Config config = new Config("test");
        config.setPlugin(MobArena.plugin);
        if (event.getView().getTitle().equalsIgnoreCase("MYINV")) {
            config.saveInventory("INVENTORY", event.getInventory());
            player.sendMessage("MYINV 인벤토리가 저장 되었습니다!");
        }


    }
}
