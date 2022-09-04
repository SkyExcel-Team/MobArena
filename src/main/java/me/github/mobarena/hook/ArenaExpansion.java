package me.github.mobarena.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.github.mobarena.MobArena;
import me.github.mobarena.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;


public class ArenaExpansion extends PlaceholderExpansion {

    private MobArena plugin;

    public ArenaExpansion(MobArena plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "Mobarena";
    }

    @Override
    public @NotNull String getAuthor() {
        return "SkyExcel";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }


    @Override
    public String getRequiredPlugin() {
        return "MobArena";
    }

    @Override
    public boolean canRegister() {

        return (this.plugin = (MobArena) Bukkit.getPluginManager().getPlugin(getRequiredPlugin())) != null;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("arena")) {
            PlayerData data = new PlayerData(player.getPlayer());

            return data.getArena();
        }
        return null; // Placeholder is unknown by the expansion
    }
}
