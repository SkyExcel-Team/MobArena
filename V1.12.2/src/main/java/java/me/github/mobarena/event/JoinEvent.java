package java.me.github.mobarena.event;

import data.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.me.github.mobarena.data.Arena;
import java.me.github.mobarena.data.PlayerData;

public class JoinEvent implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData data = new PlayerData(player);
        String name = data.getArena();
        Arena arena = new Arena(name, player);

        Region region = new Region(arena.getPos1(), arena.getPos2());
        if (region.locationIsInRegion(player.getLocation())) {
            if (data.getCooltime() == -1) {
                player.teleport(arena.getCancelLocation());
            }
        }
    }
}
