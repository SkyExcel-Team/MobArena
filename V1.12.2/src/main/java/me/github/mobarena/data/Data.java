package me.github.mobarena.data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Data {

    public static HashMap<UUID, Area> area = new HashMap<>();

    public static Area getArea(Player player) {
        return area.get(player.getUniqueId());
    }
}
