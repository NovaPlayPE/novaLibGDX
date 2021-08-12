package net.novatech.novaLibGDX.event.entity;

import net.novatech.novaLibGDX.entity.Entity;

public interface EntityCollisionEvent extends EntityEvent{
	
	boolean handle(Entity entity, Entity other);
	
}