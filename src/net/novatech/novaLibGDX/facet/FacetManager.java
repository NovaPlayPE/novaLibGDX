package net.novatech.novaLibGDX.facet;

import com.badlogic.gdx.utils.Array;

public class FacetManager {

	private static FacetManager instance;
	private FacetContainer container = FacetContainerManager.array;
	private boolean updated;

	private FacetHandler manager = new FacetHandler() {
		public void drawRenderables(Iterable<Facet> renderables) {
			for (Facet renderable : renderables) {
				renderable.draw();
			}
		}
	};

	private FacetManager(){
		instance = this;
	}

	public void renderAll() {
		if (updated) {
			container.sort();
			updated = false;
		}

		manager.drawRenderables(container.getFacets());
	}

	public void forceSort() {
		container.sort();
		updated = false;
	}

	public void setLayerManager(FacetHandler manager) {
		this.manager = manager;
	}

	public void setFacetContainer(FacetContainer cont) {
		this.container = cont;
	}

	public void add(Facet renderable) {
		updated = true;
		container.addFacet(renderable);
	}

	public void remove(Facet renderable) {
		container.removeFacet(renderable);
		renderable.onFree();
	}

	public void remove(Iterable<? extends Facet> list) {
		for (Facet r : list) {
			remove(r);
		}
	}

	public void clear() {
		container.clear();
	}

	public int getSize() {
		return container.size();
	}

	public Iterable<Facet> getFacets() {
		return container.getFacets();
	}

	public Array<Facet> getFacetArray() {
		return container.getFacetArray();
	}

	public void requestSort() {
		updated = true;
	}

	public static FacetManager get() {
		return instance;
	}
}
