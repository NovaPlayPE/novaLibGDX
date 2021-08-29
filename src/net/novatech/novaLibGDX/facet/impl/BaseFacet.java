package net.novatech.novaLibGDX.facet.impl;

import net.novatech.novaLibGDX.facet.*;

public class BaseFacet extends Facet {
	
	public float layer = 0f;
	public DrawFunc drawable;

	public BaseFacet(float layer, SortProvider sort, DrawFunc draw) {
		this.layer = layer;
		this.drawable = draw;
		this.sort(sort);
	}

	public BaseFacet(float layer, DrawFunc draw) {
		this.layer = layer;
		this.drawable = draw;
		sort(SortProvider.OBJECT);
	}

	public BaseFacet(DrawFunc draw) {
		this.drawable = draw;
		sort(SortProvider.OBJECT);
	}

	public BaseFacet() {

	}

	@Override
	public void reset() {

	}

	@Override
	public void draw() {
		FacetManager.get().requestSort();

		if (drawable != null) {
			drawable.draw(this);
		}
	}

	@Override
	public Facet set(float x, float y) {
		return this;
	}

	@Override
	public float getLayer() {
		return layer;
	}

	public static interface DrawFunc {
		public void draw(BaseFacet l);
	}
}