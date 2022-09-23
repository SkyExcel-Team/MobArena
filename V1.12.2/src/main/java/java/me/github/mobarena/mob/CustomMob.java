package java.me.github.mobarena.mob;

import data.Config;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomMob {

    private String name;

    private String type;

    private ItemStack dropItem;

    private String types;

    private static List<String> typelist = new ArrayList<>();

    private Config config;

    static {
        Arrays.stream(EntityType.values()).filter(type -> !typelist.contains(type)).forEach(type ->
                typelist.add(type.name()));
    }

    public CustomMob(String name, String type) {
        this.name = name;
        this.type = type;
        config = new Config("mob/" + name);
    }

    public void saveMob() {

    }

}
