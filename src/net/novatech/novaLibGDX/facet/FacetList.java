package net.novatech.novaLibGDX.facet;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

import net.novatech.novaLibGDX.facet.impl.*;

public class FacetList implements Poolable {
	
	public Array<Facet> facets = new Array<Facet>();

	public void add(float layer, SortProvider sort, BaseFacet.DrawFunc draw) {
		BaseFacet r = new BaseFacet(layer, sort, draw);
		r.add();
		facets.add(r);
	}

	public Facet first() {
		return facets.first();
	}

	public void add(Facet renderable) {
		renderable.add();
		facets.add(renderable);
	}

	public void setPosition(float x, float y) {
		for (Facet r : facets)
			r.set(x, y);
	}

	public void free() {
		FacetManager.get().remove(facets);
		facets.clear();
	}

	@Override
	public void reset() {
		facets.clear();
	}
}
