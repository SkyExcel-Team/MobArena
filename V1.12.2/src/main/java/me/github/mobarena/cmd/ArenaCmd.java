package me.github.mobarena.cmd;

import me.github.mobarena.MobArena;
import me.github.mobarena.Util.Translate;
import me.github.mobarena.data.Mob;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.github.mobarena.data.Arena;
import skyexcel.data.Time;
import skyexcel.data.file.Config;
import skyexcel.data.location.Region;

import java.util.ArrayList;
import java.util.List;

public class ArenaCmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            String name;
            Arena arena;
            String type;
            int round;
            if (args.length > 0) {
                switch (args[0]) {
                    case "test":

                        Config config = new Config("test");
                        config.setPlugin(MobArena.plugin);
                        if (config.getInventory("INVENTORY") != null) {
                            Inventory inv = config.getInventory("INVENTORY");
                            player.openInventory(inv);
                        } else {
                            Inventory inv = Bukkit.createInventory(null, 45, "MYINV");
                            player.openInventory(inv);
                        }

                        break;
                    case "cancel":
                        if (args.length > 1) {
                            name = args[1];
                            arena = new Arena(name, player);
                            arena.setCancelLocation();
                        } else {
                            player.sendMessage("§c이름을 입력해 주세요!");
                        }
                        break;
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
                        switch (args[1]) {
                            case "max-player":
                                if (args.length > 3) {
                                    name = args[2];
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
                            round = Integer.parseInt(args[6]);

                            Time time = new Time(day, hours, minutes, seconds);
                            arena = new Arena(name, player);
                            arena.SetCoolTime(round, time);
                        }
                        break;

                    case "mob":
                        if (args.length > 1) {
                            switch (args[1]) {
                                case "spawn":

                                    name = args[2];
                                    type = args[3];
                                    round = Integer.parseInt(args[4]);
                                    arena = new Arena(name, player);
                                    arena.SetMonsterSpawn(round, type);

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
                                        type = args[2];
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
                                        type = args[2];
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
                                        type = args[3];
                                        round = Integer.parseInt(args[4]);
                                        int amount = Integer.parseInt(args[5]);
                                        arena = new Arena(name, player);
                                        arena.SetMonsterAmount(round, amount, type);
                                    }
                                    break;
                                case "dropitem":
                                    if (args.length > 4) {
                                        name = args[2];
                                        type = args[3];
                                        round = Integer.parseInt(args[4]);

                                        arena = new Arena(name, player);
                                        arena.AddMonsterDropItem(round, type);
                                    }
                                    break;

                            }
                        }
                        break;
                }
            } else {
                player.sendMessage(
                        MobArena.prefix + " 커맨드 도움말 \n" +
                                "/arena create <이름> - 아래나를 생성 합니다.\n" +
                                "\n" +
                                ChatColor.GRAY + "아레나 생성 전, 월드에딧으로 아레나 영역을 지정해야 합니다. \n" + ChatColor.WHITE +
                                "/arena edit max-player <이름> <amount>- 해당 아레나 max-player를 설정합니다. \n" +
                                "/arena spawn <이름>  - 플레이어가 이동 될 아레나의 위치를 지정합니다. \n" +
                                "/arena round <이름> - 라운드를 추가합니다. \n" +
                                "/arena cooltime <이름> <cooltime> <round> - 라운드 제한시간 입니다. \n" +
                                "/arena cancel <이름> - 아레나에서 퇴출될시 좌표를 설정합니다." +
                                "\n" +
                                "/arena mob <몹 이름> add <아레나 이름> <round> - 몹을 아레나 라운드에 추가합니다. \n" +
                                "/arena mob <몹 이름> remove <아레나 이름> <round> - 몹을 아레나 라운드에 제거합니다. \n" +
                                "/arena mob create <이름> - 몹을 생성합니다.\n" +
                                "/arena mob edit <이름> - 몹을 편집하는 GUI를 엽니다.\n" +
                                "/arena mob dropitem <몬스터 이름> <라운드> - 몬스터가 죽었을때 떨구는 아이템을 설정하는 GUI를 엽니다.\n" +
                                ChatColor.GRAY + "쿨타임 시간 안에 몬스터를 못 잡을 경우 아레나에서 퇴장 당합니다. \n" +
                                "§f/arena mob spawn <이름> <타입> <라운드> - 몹 스폰을 설정합니다.\n" +

                                "\n");
            }
        }

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> result = new ArrayList<>();
        Config config = new Config("/arena");
        config.setPlugin(MobArena.plugin);
        if (args.length == 1) {
            if (sender.isOp()) {
                result.add("입장");
                result.add("create");
                result.add("edit");
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
                        result.add("add");
                        result.add("remove");
                        result.add("amount");
                        result.add("spawn");
                        break;

                    case "round":

                        config.setFileListTabComplete(result);
                        break;

                    case "입장":
                        config.setPlugin(MobArena.plugin);
                        config.setFileListTabComplete(result);
                        break;
                    case "spawn":

                        config.setFileListTabComplete(result);
                        break;

                    case "cooltime":
                        config.setFileListTabComplete(result);
                        break;
                    case "cancel":
                        config.setFileListTabComplete(result);
                        break;

                }
            } else {
                switch (args[0]) {
                    case "입장":
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
                            for (EntityType entities : EntityType.values()) {
                                result.add(entities.name());
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
                            config.setPlugin(MobArena.plugin);
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
                            config.setPlugin(MobArena.plugin);
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
                            config.setPlugin(MobArena.plugin);
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
                        config.setPlugin(MobArena.plugin);
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
                    config.setPlugin(MobArena.plugin);
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
