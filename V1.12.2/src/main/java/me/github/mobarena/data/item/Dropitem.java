package me.github.mobarena.data.item;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Dropitem {

    private Inventory inv;
    private Player player;


    public Dropitem(Player player,String name) {
        this.player = player;
    }

    public void onGUI() {
        inv = Bukkit.createInventory(null, 54, "");

    }

}
