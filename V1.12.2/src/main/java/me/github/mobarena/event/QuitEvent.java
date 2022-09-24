package me.github.mobarena.event;



import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.github.mobarena.data.Arena;
import me.github.mobarena.data.PlayerData;
import skyexcel.data.location.Region;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuite(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData data = new PlayerData(player);
        String name = data.getArena();

        Arena arena = new Arena(name, player);

        Region region = new Region(arena.getPos1(), arena.getPos2());
        if (region.locationIsInRegion(player.getLocation())) {
            data.setCoolTime(-1);
        }
    }
}
