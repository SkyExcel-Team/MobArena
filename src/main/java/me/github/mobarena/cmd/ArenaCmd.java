package me.github.mobarena.cmd;

import com.sk89q.worldedit.IncompleteRegionException;
import me.github.mobarena.data.Arena;
import me.github.mobarena.data.Mob;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ArenaCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            String name;
            Arena arena;
            if (args.length > 0) {
                switch (args[0]) {

                    case "입장":
                        if (args.length > 1) {
                            name = args[1];
                            arena = new Arena(name, player);
                            if(arena.getSpawn() != null){
                                player.teleport(arena.getSpawn());
                                arena.setArena();
                                arena.Monster();
                            }

                        } else {
                            player.sendMessage("§c이름을 입력해 주세요!");
                        }
                        break;
                    case "create":
                        if (args.length > 1) {
                            name = args[1];
                            arena = new Arena(name, player);
                            try {
                                arena.Create();
                            } catch (IncompleteRegionException e) {
                                player.sendMessage("위치를 지정해 주세요!");
                                throw new RuntimeException(e);

                            }
                        } else {
                            player.sendMessage("§c이름을 입력해 주세요!");
                        }

                        break;
                    case "spawn":
                        if (args.length > 1) {
                            name = args[1];
                            arena = new Arena(name, player);
                            arena.SetSpawn();

                        }
                        break;
                    case "mob":
                        if (args.length > 1) {
                            switch (args[1]) {
                                case "create":
                                    if (args.length > 2) {
                                        name = args[2];
                                        Mob mob = new Mob(name, player);
                                        mob.Create();


                                    }

                                    break;

                                case "edit":

                                    break;
                            }
                        }
                        break;
                }
            }
        }

        return false;
    }
}
