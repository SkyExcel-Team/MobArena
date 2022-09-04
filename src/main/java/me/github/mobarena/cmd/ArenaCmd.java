package me.github.mobarena.cmd;

import me.github.mobarena.MobArena;
import me.github.mobarena.Util.Translate;
import me.github.mobarena.data.Arena;
import me.github.mobarena.data.Mob;
import me.github.mobarena.hook.MythicMobs;
import me.github.skyexcelcore.data.Config;
import me.github.skyexcelcore.data.NBTMeta;
import me.github.skyexcelcore.data.Region;
import me.github.skyexcelcore.data.Time;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ArenaCmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            String name;
            Arena arena;

            if (args.length > 0) {
                switch (args[0]) {
                    case "영역":
                        if (args.length > 1) {
                            name = args[1];
                            arena = new Arena(name, player);
                            arena.setRegion();
                        } else {
                            player.sendMessage("§c이름을 입력해 주세요!");
                        }
                        break;
                    case "입장":
                        if (args.length > 1) {
                            name = args[1];
                            arena = new Arena(name, player);
                            if (arena.getSpawn() != null) {
                                arena.addPlayer();
                            } else {
                                player.sendMessage("§c해당 아레나는 준비중 입니다!");
                                return false;
                            }
                        } else {
                            player.sendMessage("§c이름을 입력해 주세요!");
                        }
                        break;
                    case "create":
                        if (args.length > 1) {
                            name = args[1];
                            arena = new Arena(name, player);

                            arena.Create();
                        } else {
                            player.sendMessage("§c이름을 입력해 주세요!");
                        }

                        break;
                    case "spawn":
                        if (args.length > 1) {
                            name = args[1];
                            arena = new Arena(name, player);

                            Region region = new Region(arena.getPos1(), arena.getPos2());

                            if (region.locationIsInRegion(player.getLocation())) {
                                arena.SetSpawn();
                            } else {
                                player.sendMessage(MobArena.prefix + Translate.Color(name) + " §c영역 밖에 있습니다!");
                            }
                        }
                        break;
                    case "round":
                        if (args.length > 1) {
                            name = args[1];
                            arena = new Arena(name, player);
                            arena.addRound();

                        }
                        break;
                    case "edit":
                        switch (args[2]) {
                            case "max-player":
                                if (args.length > 3) {
                                    name = args[1];
                                    int amount = Integer.parseInt(args[3]);
                                    arena = new Arena(name, player);
                                    arena.setMaxPlayer(amount);
                                    player.sendMessage(MobArena.prefix + "§amax-player를 " + amount + " 명으로 설정하였습니다!");
                                }
                                break;
                        }
                        break;
                    case "cooltime":
                        if (args.length == 7) {
                            name = args[1];
                            int day = Integer.parseInt(args[2]);
                            int hours = Integer.parseInt(args[3]);
                            int minutes = Integer.parseInt(args[4]);
                            int seconds = Integer.parseInt(args[5]);
                            int round = Integer.parseInt(args[6]);

                            Time time = new Time(day, hours, minutes, seconds);
                            arena = new Arena(name, player);
                            arena.SetCoolTime(round, time);
                        }
                        break;

                    case "mob":
                        if (args.length > 1) {
                            switch (args[1]) {
                                case "spawn":
                                    if (args.length > 5) {
                                        name = args[2];
                                        String type = args[3];
                                        int round = Integer.parseInt(args[4]);
                                        int radius = Integer.parseInt(args[5]);
                                        arena = new Arena(name, player);
                                        arena.SetMonsterRadius(round, type, radius);
                                    } else if (args.length > 4) {
                                        name = args[2];
                                        String type = args[3];
                                        int round = Integer.parseInt(args[4]);
                                        arena = new Arena(name, player);
                                        arena.SetMonsterSpawn(round, type);
                                    }
                                    break;
                                case "create":
                                    if (args.length > 2) {
                                        name = args[2];
                                        Mob mob = new Mob(name, player);
                                        mob.Create();
                                    }

                                    break;
                                case "dropitem":
                                    if (args.length > 2) {
                                        name = args[2];
                                        Mob mob = new Mob(name, player);
                                        mob.SetDropItem();
                                    }
                                    break;
                                case "edit":
                                    if (args.length > 2) {
                                        name = args[2];
                                        Mob mob = new Mob(name, player);
                                        if (args.length > 4) {
                                            mob.SetType(args[4]);
                                        }

                                    }
                                    break;
                                case "add":
                                    if (args.length > 2) {
                                        String type = args[2];
                                        if (args.length > 3) {
                                            name = args[3];
                                            if (args.length > 4) {
                                                int index = Integer.parseInt(args[4]);
                                                arena = new Arena(name, player);
                                                arena.AddMonster(index, type);
                                            }
                                        }
                                    }
                                    break;
                                case "remove":
                                    if (args.length > 2) {
                                        String type = args[2];
                                        if (args.length > 3) {
                                            name = args[3];
                                            if (args.length > 4) {
                                                int index = Integer.parseInt(args[4]);
                                                arena = new Arena(name, player);
                                                arena.RemoveMonster(index, type);

                                                player.sendMessage(Translate.Color(type) + " §a몹을 §7" + index + " §a아레나에 제거 했습니다! ");
                                            }
                                        }
                                    }
                                    break;

                                case "amount":
                                    if (args.length > 5) {
                                        name = args[2];
                                        String type = args[3];
                                        int round = Integer.parseInt(args[4]);
                                        int amount = Integer.parseInt(args[5]);
                                        arena = new Arena(name, player);
                                        arena.SetMonsterAmount(round, amount, type);
                                    }
                                    break;
                            }
                        }
                        break;
                }
            }
        }

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> result = new ArrayList<>();
        Config config;
        if (args.length == 1) {
            if (sender.isOp()) {
                result.add("create");
                result.add("edit");
                result.add("입장");
                result.add("mob");
                result.add("spawn");
                result.add("round");
                result.add("amount");
                result.add("cooltime");

            }
            result.add("입장");
        } else if (args.length == 2) {
            config = new Config("/arena");
            if (sender.isOp()) {
                switch (args[0]) {
                    case "edit":

                        config.setFileListTabComplete(result);
                        return result;

                    case "create":
                        result.add("<이름>");
                        return result;

                    case "mob":
                        result.add("create");
                        result.add("edit");
                        result.add("dropitem");
                        result.add("add");
                        result.add("remove");
                        result.add("amount");
                        result.add("spawn");
                        break;

                    case "round":

                        config.setFileListTabComplete(result);
                        break;

                    case "입장":

                        config.setFileListTabComplete(result);
                        break;
                    case "spawn":

                        config.setFileListTabComplete(result);
                        break;

                    case "cooltime":
                        config.setFileListTabComplete(result);
                        break;

                }
            }
        } else if (args.length == 3) {
            switch (args[0]) {
                case "mob":
                    switch (args[1]) {
                        case "edit":

//                            add(mobfiles, result);
                            break;

                        case "add":
                            for (String names : MythicMobs.getMythicMobList()) {
                                result.add(names);
                            }
                            break;
                        case "remove":

//                            add(mobfiles, result);

                            break;
                        case "amount":
                            config = new Config("/arena");
                            config.setFileListTabComplete(result);

                            break;
                        case "spawn":
                            config = new Config("/arena");
                            config.setFileListTabComplete(result);
                            break;
                    }


                    break;
                case "cooltime":
                    result.add("일 §7<숫자로 입력해주세요>");
                    break;

                case "edit":
                    result.add("max-player");
                    break;
            }
        } else if (args.length == 4) {
            switch (args[0]) {
                case "mob":
                    switch (args[1]) {
                        case "add":
                            config = new Config("/arena");
                            config.setFileListTabComplete(result);
                            break;
                        case "edit":
                            result.add("name");
                            result.add("health");
                            result.add("speed");
                            result.add("damage");
                            result.add("type");
                            result.add("spawn");
                            break;

                        case "spawn":
                            config = new Config("arena/" + args[2] + "/mob/");
                            config.setFileListTabComplete(result);
                            break;

                        case "amount":
                            config = new Config("arena/" + args[2] + "/mob/");
                            config.setFileListTabComplete(result);
                            break;

                    }

                    break;
                case "cooltime":
                    result.add("시간 §7<숫자로 입력해주세요>");
                    break;
                case "edit":
                    result.add("max-player §7<숫자로 입력해주세요>");
                    break;
            }
        } else if (args.length == 5) {
            if (args[0].equals("mob")) {
                switch (args[1]) {

                    case "spawn":
                        config = new Config("arena/" + args[2] + "/" + args[2]);
                        int index = config.getConfig().getConfigurationSection("arena.round").getKeys(false).size();

                        for (int i = 0; i < index; i++) {
                            result.add(String.valueOf(i));
                        }
                        break;
                }

            }
            switch (args[1]) {
                case "add":


                    config = MobArena.ArenaConfig = new Config("arena/" + args[3] + "/" + args[3]);

                    int index = config.getConfig().getConfigurationSection("arena.round").getKeys(false).size();

                    for (int i = 0; i < index; i++) {
                        result.add(String.valueOf(i));
                    }
                    break;

            }
            switch (args[0]) {
                case "cooltime":
                    result.add("분 §7<숫자로 입력해주세요>");
                    break;
            }
            switch (args[3]) {
                case "type":

                    for (EntityType type : EntityType.values()) {
                        result.add(type.name());
                    }
                    break;

                case "add":
                    break;
            }
        } else if (args.length == 6) {
            switch (args[0]) {
                case "cooltime":
                    result.add("초 §7<숫자로 입력해주세요>");
                    break;

                case "mob":
                    if (args[1].equalsIgnoreCase("amount")) {

                    }
                    switch (args[1]) {
                        case "amount":
                            result.add("<스폰량> §7<숫자로 입력해주세요>");
                            break;
                        case "spawn":
                            result.add("렌덤 스폰될 반지름 §7<숫자로 입력해주세요>");
                            break;
                    }
                    break;
            }
        } else if (args.length == 7) {
            switch (args[0]) {
                case "cooltime":
                    config = new Config("arena/" + args[1] + "/" + args[1]);
                    int index = config.getConfig().getConfigurationSection("arena.round").getKeys(false).size();

                    for (int i = 0; i < index; i++) {
                        result.add(String.valueOf(i));
                    }
                    break;
            }
        }
        return result;
    }

}
