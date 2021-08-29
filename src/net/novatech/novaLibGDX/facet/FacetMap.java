package net.novatech.novaLibGDX.facet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Values;

public class FacetMap {
	
	public ObjectMap<String, Facet> map = new ObjectMap<String, Facet>();

	public Iterable<? extends Facet> list() {
		return map.values();
	}

	public Facet first() {

		return map.values().next();
	}

	public Facet get(String name) {
		return map.get(name);
	}

	public void add(String name, Facet renderable) {
		renderable.add();
		map.put(name, renderable);
	}

	public void setPosition(float x, float y) {
		for (Facet r : map.values())
			r.set(x, y);
	}

	public void free() {
		FacetManager.get().remove(map.values());
	}
}
