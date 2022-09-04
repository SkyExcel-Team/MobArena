package me.github.mobarena.event;

import me.github.mobarena.data.Arena;
import me.github.mobarena.data.PlayerData;
import me.github.skyexcelcore.data.Region;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDeathEvent implements Listener {
    @EventHandler
    public void onDeath(org.bukkit.event.entity.EntityDeathEvent event) {
        Entity entity = event.getEntity();
        Player player = event.getEntity().getKiller();
        String name = entity.getCustomName();

        if (entity instanceof Player) { // 플레이어가 죽었을때

        } else {
            if (player instanceof Player) {  // 플레이어가 죽였을때
                PlayerData data = new PlayerData(player);

                String arenaName = data.getArena();
                int round = data.getRound();
                Arena arena = new Arena(arenaName, player);
                if (arena.minTotal(round)) {
                    player.sendMessage(arena.getTotal(round) + " 마리 남았습니다. 라운드 : " + round);
                }
            }
        }
    }
}
