package me.github.mobarena;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.github.mobarena.cmd.ArenaCmd;
import me.github.mobarena.data.Config;
import me.github.skyexcelcore.SkyExcel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MobArena extends JavaPlugin {

    public static MobArena plugin;
    public static Config ArenaConfig;
    public static WorldEditPlugin WorldEdit;

    public static Config config;

    public static Config Mob;

    public static Config PlayerData;

    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;
        if (getCore() != null) {
            if (Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") != null) {
                WorldEdit = getWorldEditPlugin();
                getCommand("arena").setExecutor(new ArenaCmd());

            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " WorldEdit 플러그인이 존재하지 않습니다. 일부 기능이 작동 하지 않을 수 있습니다.");
            }
            init();
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SkyExcelCore 플러그인이 존재하지 않습니다.");

            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "SkyExcelCore 다운로드 링크 - https://discord.gg/TMaAyJsEnQ");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SkyExcelCore 플러그인을 종료합니다..");

            Bukkit.getPluginManager().disablePlugin(this);


        }

    }

    public void init() {
        config = new Config("config", this);

        config.LoadDefaultPluginConfig();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static WorldEditPlugin getWorldEditPlugin() {
        Plugin worldedit = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if (worldedit instanceof WorldEditPlugin) return (WorldEditPlugin) worldedit;
        else return null;
    }

    public static SkyExcel getCore() {
        Plugin SkyExcelCore = Bukkit.getServer().getPluginManager().getPlugin("SkyExcelCore");
        if (SkyExcelCore instanceof SkyExcel) {
            return (SkyExcel) SkyExcelCore;
        } else {
            return null;
        }
    }

}
