package net.kenddie.fantasyweapons.config;

public final class WeaponSettings {
    private final double damage;
    private final double speed;
    public static final WeaponSettings DEFAULT = new WeaponSettings(9.0, -2.4);
    public static final double MIN_DAMAGE = 0.0;
    public static final double MAX_DAMAGE = 2048.0;
    public static final double MIN_SPEED = -4.0;
    public static final double MAX_SPEED = 1024.0;

    public WeaponSettings(double damage, double speed) {
        if (!validDamage(damage) || !validSpeed(speed)) {
            throw new IllegalArgumentException("Invalid weapon attributes: " + damage + ", " + speed);
        }
        this.damage = damage;
        this.speed = speed;
    }

    public double damage() { return damage; }
    public double speed() { return speed; }

    @Override public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof WeaponSettings)) return false;
        WeaponSettings settings = (WeaponSettings) other;
        return Double.compare(damage, settings.damage) == 0 && Double.compare(speed, settings.speed) == 0;
    }

    @Override public int hashCode() { return java.util.Objects.hash(damage, speed); }

    @Override public String toString() {
        return "WeaponSettings[damage=" + damage + ", speed=" + speed + "]";
    }

    public static boolean validDamage(double value) {
        return Double.isFinite(value) && value >= MIN_DAMAGE && value <= MAX_DAMAGE;
    }

    public static boolean validSpeed(double value) {
        return Double.isFinite(value) && value >= MIN_SPEED && value <= MAX_SPEED;
    }
}
