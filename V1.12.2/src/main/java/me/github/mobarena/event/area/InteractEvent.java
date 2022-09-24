package me.github.mobarena.event.area;

import me.github.mobarena.data.Area;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import me.github.mobarena.data.Data;
import org.bukkit.inventory.EquipmentSlot;

public class InteractEvent implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        EquipmentSlot e = event.getHand();

        if (e.equals(EquipmentSlot.HAND)) {
            if (player.getInventory().getItemInHand().getType().equals(Material.STICK) && player.isOp()) {
                Area area = getArea(player);
                if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    Location location = event.getClickedBlock().getLocation();
                    player.sendMessage("몹 아래나 첫번째 영역이 지정되었습니다!");

                    area.setPos1(location);

                    Data.area.put(player.getUniqueId(), area);
                } else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    Location location = event.getClickedBlock().getLocation();
                    player.sendMessage(ChatColor.ITALIC + "몹 아래나 두번째 영역이 지정되었습니다!");

                    area.setPos2(location);
                    Data.area.put(player.getUniqueId(), area);
                }
                event.setCancelled(true);
            }
        }
    }

    public Area getArea(Player player) {
        if (Data.area.get(player.getUniqueId()) == null) {
            Area area = new Area();
            return area;
        } else {
            Area area = Data.getArea(player);
            return area;
        }
    }
}
