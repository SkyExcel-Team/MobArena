package me.github.mobarena;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.github.mobarena.cmd.ArenaCmd;
import me.github.mobarena.event.EntityDeathEvent;
import me.github.mobarena.hook.ArenaExpansion;
import me.github.skyexcelcore.SkyExcel;
import me.github.skyexcelcore.data.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Arrays;

public class MobArena extends JavaPlugin implements Listener {

    public static MobArena plugin;
    public static Config ArenaConfig;
    public static WorldEditPlugin WorldEdit;

    public static String prefix = "§e[§6MobArena§e] §f";
    public static Config config;

    public static Config Mob;

    public static Config PlayerData;

    public static boolean MythicUse = true;

    private boolean free = false;


    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;
        if (getCore() != null) {
            SkyExcel.setNewPlugin(this);
            init();
            getCommand("arena").setExecutor(new ArenaCmd());
            getCommand("arena").setTabCompleter(new ArenaCmd());

            Listener[] events = {new EntityDeathEvent()};
            PluginManager pm = Bukkit.getPluginManager();
            Arrays.stream(events).forEach(classes -> {
                pm.registerEvents(classes, this);
            });
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                new ArenaExpansion(this).register();
                Bukkit.getConsoleSender().sendMessage("§a등록 완료  §b%cashshop_cash% §a키워드로 사용 가능합니다!");
            } else {

                Bukkit.getConsoleSender().sendMessage("§cPLACEHOLDER API 기능이 해제 됩니다 (API 없음)");
            }
            if (Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") != null) {
                WorldEdit = getWorldEditPlugin();
            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " WorldEdit 플러그인이 존재하지 않습니다. 일부 기능이 작동 하지 않을 수 있습니다.");
            }

        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "======================================================================================");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + plugin.getName() + "치명적인 오류 발생!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SkyExcelCore 플러그인이 존재하지 않습니다.");
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "SkyExcelCore 다운로드 링크 - https://discord.gg/TMaAyJsEnQ");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SkyExcelCore 플러그인을 종료합니다..");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "======================================================================================");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }

    }

    public void init() {
        config = new Config("config");

        config.loadDefaultPluginConfig();
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
