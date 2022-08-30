package me.github.mobarena.Util;

import org.bukkit.ChatColor;

public class Translate {

    public static String translate(String msg) {

        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
