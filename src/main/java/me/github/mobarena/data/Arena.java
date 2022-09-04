package me.github.mobarena.data;

import com.google.common.base.Preconditions;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.math.BlockVector3;
import me.github.mobarena.MobArena;
import me.github.mobarena.Util.StringData;
import me.github.mobarena.Util.Translate;
import me.github.mobarena.hook.MythicMobs;
import me.github.mobarena.runnable.Delay;
import me.github.mobarena.runnable.Particle.ParticleShooter;
import me.github.skyexcelcore.data.Config;
import me.github.skyexcelcore.data.Region;
import me.github.skyexcelcore.data.Time;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Arena {
    private String name;

    private Player player;

    private Config config;

    private int total = 0;

    private Delay delay;

    public Arena(String name, Player player) {
        this.name = name;
        this.player = player;
        this.config = MobArena.ArenaConfig = new Config("arena/" + this.name + "/" + this.name);
    }

    public void Create() {
        try {
            if (MobArena.WorldEdit.getSession(player) != null) {
                MobArena.WorldEdit.getSession(player).getSelection().getMaximumPoint();

                BlockVector3 pos1 = MobArena.WorldEdit.getSession(player).getSelection().getBoundingBox().getPos1();
                BlockVector3 pos2 = MobArena.WorldEdit.getSession(player).getSelection().getBoundingBox().getPos2();

                Location location1 = new Location(player.getWorld(), pos1.getX(), pos1.getY(), pos1.getZ());

                Location location2 = new Location(player.getWorld(), pos2.getX(), pos2.getY(), pos2.getZ());
                if (config.isFileExist()) {
                    player.sendMessage(MobArena.prefix + "§c해당 컨피그는 이미 존재 합니다! 편집을 해주세요!");
                } else {
                    config.getConfig().set("arena.name", name);
                    config.getConfig().createSection("arena.round");
                    config.setLocation("arena.pos1", location1);
                    config.setLocation("arena.pos2", location2);
                    config.getConfig().set("arena.spawn", null);
                    config.getConfig().set("arena.single", false);
                    config.saveConfig();
                }
            }
        } catch (IncompleteRegionException e) {
            player.sendMessage(MobArena.prefix + "§c위치를 지정해 주세요!");

        }
    }

    public void setRegion() {
        if (MobArena.WorldEdit.getSession(player) != null) {
            try {
                MobArena.WorldEdit.getSession(player).getSelection().getMaximumPoint();
                BlockVector3 pos1 = MobArena.WorldEdit.getSession(player).getSelection().getBoundingBox().getPos1();
                BlockVector3 pos2 = MobArena.WorldEdit.getSession(player).getSelection().getBoundingBox().getPos2();
                Location location1 = new Location(player.getWorld(), pos1.getX(), pos1.getY(), pos1.getZ());
                Location location2 = new Location(player.getWorld(), pos2.getX(), pos2.getY(), pos2.getZ());
                config.setLocation("arena.pos1", location1);
                config.setLocation("arena.pos2", location2);
                config.saveConfig();
                player.sendMessage(MobArena.prefix + "§a성공적으로 위치를 수정 했습니다!");

            } catch (IncompleteRegionException e) {
                player.sendMessage(MobArena.prefix + "§c위치를 지정해 주세요!");
                throw new RuntimeException(e);
            }
        }
    }

    public Config getConfig() {
        return config;
    }

    public void addRound() {
        ConfigurationSection round = config.getConfig().createSection("arena.round." + config.getConfig().getConfigurationSection("arena.round").getKeys(false).size());
        config.setSection(round);

        if (config.getConfig().getConfigurationSection("arena.round") != null) {

            if (round != null) {
                round.set("mobs", new ArrayList<>());
                round.set("potion", PotionType.POISON.name());

                round.set("cooltime", -1);

                round.set("item", new ItemStack(Material.DIAMOND_SWORD).getType().name());
                config.saveDefualtConfig();
                config.saveConfig();
                player.sendMessage(MobArena.prefix + Translate.Color(name) + "§a영역에 §f" + config.getConfig().getConfigurationSection("arena.round").getKeys(false).size() + "§a라운드가 추가 되었습니다!");
            }
        } else {
            config.getConfig().createSection(round.getCurrentPath() + ".mobs");

            config.getConfig().set("arena.round." + 0 + ".potion", PotionType.POISON.name());

            config.setInteger("arena.round." + 0, -1);

            config.getConfig().set("arena.round." + 0 + ".item", new ItemStack(Material.DIAMOND_SWORD).getType().name());
            config.saveConfig();
        }
    }

    public int getMaxRound() {

        return config.getConfig().getConfigurationSection("arena.round." + config.getConfig().getConfigurationSection("arena.round").getKeys(false).size()).getKeys(false).size();
    }

    public void SetCoolTime(int round, Time time) {
        ConfigurationSection cooltime = config.getConfig().getConfigurationSection("arena.round." + round);
        if (cooltime != null) {
            cooltime.set("cooltime", time.SECOND_IN_MILLIS());
            player.sendMessage(MobArena.prefix + name + "§a아레나의 라운드 §f" + round + "§a에 시간이 등록 되었습니다.");
            config.saveConfig();
        } else {
            player.sendMessage(MobArena.prefix + round + " §c라운드가 존재하지 않습니다!");
        }
    }

    public long getCoolTime(int round) {
        ConfigurationSection cooltime = config.getConfig().getConfigurationSection("arena.round." + round);

        return cooltime.getLong("cooltime");
    }

    public void SetSpawn() {
        if (config.isFileExist()) {
            config.setLocation("arena.spawn", player.getLocation());
            player.sendMessage(MobArena.prefix + "§b자신의 위치로 스폰을 설정하였습니다! 좌표 §f(X§7" + (int) player.getLocation().getX() + "§f,Y§7" + (int) player.getLocation().getY() + "§F,Z§7" + (int) player.getLocation().getZ() + "§F)");
            config.saveConfig();
        }
    }

    public Location getSpawn() {
        if (config != null && config.getConfig().getString("arena.spawn.world") != null)

            return config.getLocation("arena.spawn");
        else {
            return null;
        }
    }

    public Location getPos1() {

        if (config != null) {
            return config.getLocation("arena.pos1");
        } else {
            return null;
        }
    }

    public Location getPos2() {

        if (config != null)

            return config.getLocation("arena.pos2");
        else {
            return null;
        }
    }

    public void startDelay(){
        PlayerData data = new PlayerData(player);
        data.setDelay(0);
    }

    public void TeleportSpawn() {
        player.teleport(getSpawn());
    }

    public void StartRound(String name) {
        PlayerData data = new PlayerData(player);
        data.setArena(name, 0);
        SpawnAtRound(name, data.getRound());

    }

    public void addPlayer() {
        int maxplayer = config.getInteger("arena.maxplayers");
        if (config.get("arena.players") != null) {
            List<String> uuid = (List<String>) config.getConfig().getList("arena.players");
            if (!uuid.contains(player.getUniqueId().toString())) {
                if (uuid.size() > maxplayer) {
                    uuid.add(player.getUniqueId().toString());
                    config.setObject("arena.players", uuid);
                    TeleportSpawn();
                    StringData.sendJoinMessage(player);
                } else {
                    if (!uuid.contains(player.getUniqueId())) {
                        player.sendMessage("§c해당 아레나는 인원이 꽉 찾습니다!");
                    }
                }
            }
        } else {
            List<String> uuid = new ArrayList<>();
            uuid.add(player.getUniqueId().toString());
            config.setObject("arena.players", uuid);

            if (uuid.size() - 1 == maxplayer) {
                StartRound(name);
            }
            TeleportSpawn();
            StringData.sendJoinMessage(player);
        }
    }

    public void removePlayer() {
        if (config.get("arena.players") != null) {
            List<String> uuid = (List<String>) config.getConfig().getList("arena.players");
            uuid.remove(player.getUniqueId());
            config.setObject("arena.players", uuid);
        }
    }


    public void setMaxPlayer(int amount) {
        config.setInteger("arena.max-player", amount);
        config.saveConfig();
    }

    public void AddMonster(int index, String type) {
        Config mob = MobArena.Mob = new Config("arena/" + this.name + "/mob/" + type);

        ConfigurationSection section = config.getConfig().getConfigurationSection("arena.round." + index);

        List<String> mobs = (List<String>) section.getList("mobs");
        if (!mobs.contains(type)) {
            mobs.add(type);
            section.set("mobs", mobs);
            player.sendMessage(MobArena.prefix + Translate.Color(type) + " §a몹을 §7" + index + " §a아레나에 추가 했습니다! ");
        }

        config.saveConfig();

        if (!mob.isFileExist()) {
            mob.getConfig().set("name", type);
            mob.getConfig().createSection("round." + index);

            ConfigurationSection mobsection = mob.getConfig().getConfigurationSection("round." + index);
            mobsection.set("amount", 0);
            mobsection.set("spawn", player.getLocation());
            mobsection.set("radius", 0);
            mob.saveConfig();
        } else {
            mob.getConfig().createSection("round." + index);
            ConfigurationSection mobsection = mob.getConfig().getConfigurationSection("round." + index);
            mobsection.set("amount", 0);
            mobsection.set("spawn", player.getLocation());
            mobsection.set("radius", 0);
            mob.saveConfig();
        }
    }

    public void SpawnAtRound(String name, int round) {
        Arena arena = new Arena(name, player);

        for (String names : arena.getMonster(round)) {
            Config mobs = new Config("arena/" + this.name + "/mob/" + names); // 몹 컨피그를 불러옴.
            ConfigurationSection mobsection = mobs.getConfig().getConfigurationSection("round." + round); //라운드의 mob section을 불러옴.
            if (mobsection != null) {
                Location location = (Location) mobsection.get("spawn"); //몹에 스폰 위치를 불러옴.
                int amount = mobsection.getInt("amount"); // Mob 의 amount값을 불러옴.
                this.total += amount; // 스폰이 되면서 amount 값 만큼 total변수 증가.
                location.add(0, 1, 0);
                for (int i = 0; i < amount; i++) {
                    MythicMobs.spawnMob(names, location);
                }
            }
            setTotal(round); // Total 값을 round에 설정.
        }
    }

    public void setTotal(int round) {
        ConfigurationSection arenasection = config.getConfig().getConfigurationSection("arena.round." + round);
        arenasection.set("total", total);
        config.saveConfig();
    }

    public boolean minTotal(int round) {
        ConfigurationSection arenasection = config.getConfig().getConfigurationSection("arena.round." + round);
        int total = arenasection.getInt("total");
        if (total != 0) {
            total--;
            arenasection.set("total", total);
            config.saveConfig();
            return true;
        }
        return false;
    }

    public int getTotal(int round) {
        ConfigurationSection arenasection = config.getConfig().getConfigurationSection("arena.round." + round);

        return arenasection.getInt("total");
    }

    public void SetMonsterSpawn(int index, String type) {
        Config mob = MobArena.Mob = new Config("arena/" + this.name + "/mob/" + type);
        ConfigurationSection section = mob.getConfig().getConfigurationSection("round." + index);

        section.set("spawn", player.getLocation());

        mob.saveConfig();
        player.sendMessage(MobArena.prefix + "§b몹 스폰을 설정하였습니다! 좌표 §f(X§7" + (int) player.getLocation().getX() + "§f,Y§7" + (int) player.getLocation().getY() + "§F,Z§7" + (int) player.getLocation().getZ() + "§F)");
    }

    public void SetMonsterRadius(int index, String type, int radius) {
        Config mob = MobArena.Mob = new Config("arena/" + this.name + "/mob/" + type);
        ConfigurationSection section = mob.getConfig().getConfigurationSection("round." + index);

        section.set("radius", radius);

        mob.saveConfig();
        new ParticleShooter(player.getEyeLocation(), player.getLocation().getDirection(), section.getDouble("radius"));

        player.sendMessage(MobArena.prefix + "§b몹 영역을 설정 하였습니다! §7반지름 : " + radius);
    }

    public void SetMonsterAmount(int index, int amount, String type) {
        Config mob = MobArena.Mob = new Config("arena/" + this.name + "/mob/" + type);
        ConfigurationSection section = mob.getConfig().getConfigurationSection("round." + index);

        section.set("amount", amount);

        mob.saveConfig();
        player.sendMessage(MobArena.prefix + "§b몹 스폰량을 §f" + amount + " §b으로 설정하였습니다");
    }


    public void RemoveMonster(int index, String type) {
        ConfigurationSection section = config.getConfig().getConfigurationSection("arena.round." + index);
        List<String> list = (List<String>) section.getList("mobs");

        list.remove(type);
        config.getConfig().set("mobs", list);
        config.saveConfig();
    }

    public List<String> getMonster(int round) {
        ConfigurationSection section = config.getConfig().getConfigurationSection("arena.round." + round);
        List<String> list = (List<String>) section.getList("mobs");

        return list;
    }

    public Region getRegion() {
        Region region = new Region(getPos1(), getPos2());

        return region;
    }
}
