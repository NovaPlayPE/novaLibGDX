package net.novatech.novaLibGDX.facet;

import com.badlogic.gdx.utils.Pool.Poolable;

public abstract class Facet implements Comparable<Facet>, Poolable{
	public SortProvider provider = SortProvider.TILE;
	
	public abstract void draw();
	
	public abstract Facet set(float x, float y);
	
	public abstract float getLayer();
	
	public <T extends Facet> T add() {
		FacetManager.get().add(this);
		return (T) this;
	}

	public void remove() {
		FacetManager.get().remove(this);
	}
	
	public Facet sort(SortProvider provider){
		this.provider = provider;
		return this;
	}
	
	public void add(FacetList list){
		list.add(this);
	}
	
	public void add(String name, FacetMap group){
		group.add(name, this);
	}
	
	public void onFree(){
		
	}

	public int compareTo(Facet o){
		return provider.compare(this, o);
	}
}
