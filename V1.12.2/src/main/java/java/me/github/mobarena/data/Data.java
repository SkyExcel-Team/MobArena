package java.me.github.mobarena.data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Data {

    public static HashMap<UUID, Area> area = new HashMap<>();

    public static Area getArena(Player player) {
        return area.get(player.getUniqueId());
    }
}
