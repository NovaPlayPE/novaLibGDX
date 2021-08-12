package net.novatech.novaLibGDX.event.entity;

import net.novatech.novaLibGDX.entity.DamageCause;
import net.novatech.novaLibGDX.entity.Entity;

public interface EntityDamageEvent extends EntityEvent {
	
	boolean handle(Entity damager, Entity source, DamageCause cause, int damage);
	
}