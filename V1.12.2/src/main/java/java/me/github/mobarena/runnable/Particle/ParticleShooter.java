package java.me.github.mobarena.runnable.Particle;

import me.github.mobarena.MobArena;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


public class ParticleShooter extends BukkitRunnable {
    private static final int DURATION_IN_TICKS = 60;
    private double RADIUS = 10D;
    int points = 100;

    private int ticks = 0;

    private final Vector dir;
    private final Location currentLoc;

    public ParticleShooter(Location origin, Vector dir,double Radius) {
        this.currentLoc = origin.clone();
        this.dir = dir.clone().normalize();

        this.runTaskTimer(MobArena.plugin, 0L, 1L);
        this.RADIUS = Radius;
    }

    @Override
    public void run() {
        if (ticks++ >= DURATION_IN_TICKS || currentLoc.getWorld() == null) {
            this.cancel();
            return;
        }
        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            Location point = currentLoc.clone().add(RADIUS * Math.sin(angle), 0.0d, RADIUS * Math.cos(angle));

            point.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, point, 1);
        }
    }
}
