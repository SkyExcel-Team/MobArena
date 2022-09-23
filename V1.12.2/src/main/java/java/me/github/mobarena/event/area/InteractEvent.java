package java.me.github.mobarena.event.area;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.me.github.mobarena.data.Area;
import java.me.github.mobarena.data.Data;

public class InteractEvent implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            Location location = event.getClickedBlock().getLocation();
            player.sendMessage("몹 아래나 첫번재 영역이 지정되었습니다!");
            if (Data.area.containsKey(player.getUniqueId())) {
                Data.getArena(player).setPos1(location);
            } else {
                Data.area.put(player.getUniqueId(), new Area());
                Data.getArena(player).setPos1(location);
            }

        } else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Location location = event.getClickedBlock().getLocation();
            player.sendMessage("몹 아래나 두번재 영역이 지정되었습니다!");
        }
    }
}
