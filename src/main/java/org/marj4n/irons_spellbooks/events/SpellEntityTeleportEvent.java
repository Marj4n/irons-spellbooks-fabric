package org.marj4n.irons_spellbooks.events;

import net.minecraft.world.entity.Entity;

public abstract class SpellEntityTeleportEvent extends SpellEvent {
    private final Entity entity;
    private double x, y, z;
    protected SpellEntityTeleportEvent(Entity entity, double x, double y, double z) {
        this.entity = entity;
        this.x = x; this.y = y; this.z = z;
    }
    public Entity getEntity() { return entity; }
    public double getTargetX() { return x; }
    public double getTargetY() { return y; }
    public double getTargetZ() { return z; }
    public void setTargetX(double value) { x = value; }
    public void setTargetY(double value) { y = value; }
    public void setTargetZ(double value) { z = value; }
    @Override public boolean isCancelable() { return true; }
}
