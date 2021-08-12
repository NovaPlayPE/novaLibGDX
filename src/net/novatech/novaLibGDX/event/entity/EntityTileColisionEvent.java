package net.novatech.novaLibGDX.event.entity;

import net.novatech.library.math.Vector2;
import net.novatech.novaLibGDX.entity.Entity;
import net.novatech.novaLibGDX.event.Event;

public interface EntityTileColisionEvent extends EntityEvent {
	
	boolean handle(Entity entity, Vector2 tile);
	
}