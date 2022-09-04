package me.github.mobarena.hook;

import io.lumine.mythic.api.MythicProvider;
import io.lumine.mythic.api.exceptions.InvalidMobTypeException;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAPIHelper;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class MythicMobs {
    static BukkitAPIHelper mmAPI = new BukkitAPIHelper();


    public static MythicMob getMobByName(String name) {
        return mmAPI.getMythicMob(name);
    }

    public static void spawnMob(String name, Location location) {
        try {
            mmAPI.spawnMythicMob(name, location);
        } catch (InvalidMobTypeException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getMythicMobList() {
        List<String> mobs = new ArrayList<>();
        for (MythicMob mobType : MythicProvider.get().getMobManager().getMobTypes())
            mobs.add(mobType.getInternalName());
        return mobs;
    }
}
