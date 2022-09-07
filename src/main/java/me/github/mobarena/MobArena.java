package me.github.mobarena;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.github.mobarena.cmd.ArenaCmd;
import me.github.mobarena.data.Arena;
import me.github.mobarena.data.PlayerData;
import me.github.mobarena.event.EntityDeathEvent;
import me.github.mobarena.event.JoinEvent;
import me.github.mobarena.event.QuitEvent;
import me.github.mobarena.event.SpawnEvent;
import me.github.mobarena.hook.ArenaExpansion;
import data.Config;
import data.Region;
import data.Time;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
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

    private String MinecraftVersion = Bukkit.getServer().getClass().getPackage().getName();

    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;


        init();
        TeleportPlayer();

        getCommand("arena").setExecutor(new ArenaCmd());
        getCommand("arena").setTabCompleter(new ArenaCmd());


        Listener[] events = {new EntityDeathEvent(), new SpawnEvent(), new JoinEvent(), new QuitEvent()};
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

//    } else
//
//    {
//        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "======================================================================================");
//        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + plugin.getName() + "치명적인 오류 발생!");
//        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SkyExcelCore 플러그인이 존재하지 않습니다.");
//        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "SkyExcelCore 다운로드 링크 - https://discord.gg/TMaAyJsEnQ");
//        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SkyExcelCore 플러그인을 종료합니다..");
//        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "======================================================================================");
//        Bukkit.getPluginManager().disablePlugin(plugin);
//

    }

    @Override
    public void onDisable() {
        super.onDisable();

    }

    public void TeleportPlayer() {
        Bukkit.getOnlinePlayers().forEach(players -> {

            PlayerData data = new PlayerData(players);
            Arena arena = new Arena(data.getArena(), players);
            Region region = new Region(arena.getPos1(), arena.getPos2());

            if (arena.getPlayerUUID().contains(players.getUniqueId().toString())) {
                arena.removePlayer(players);
            }

            if (region.locationIsInRegion(players.getLocation())) {
                players.teleport(arena.getCancelLocation());
                players.sendMessage(prefix + "§c서버가 종료되어 아레나에서 퇴장 합니다.");

                arena.removePlayer(players);
            }
        });
    }

    public void init() {
        config = new Config("config");
        config.setPlugin(this);
        config.loadDefaultPluginConfig();
    }


    public static WorldEditPlugin getWorldEditPlugin() {
        Plugin worldedit = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if (worldedit instanceof WorldEditPlugin) return (WorldEditPlugin) worldedit;
        else return null;
    }


    public enum SupportVersion {
        v1_18_2("v1_18_R2");

        private String version;

        SupportVersion(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }
    }
}
