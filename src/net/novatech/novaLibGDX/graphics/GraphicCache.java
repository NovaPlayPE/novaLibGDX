package net.novatech.novaLibGDX.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntArray;

public class GraphicCache implements Disposable{
	protected Array<SpriteCache> caches = new Array<>();
	protected IntArray cacheIDs = new IntArray();
	
	private int size = 2000;
	private SpriteCache current;
	private int draws = 0;
	private boolean disposed = false;
	
	public GraphicCache(int size){
		this.size = size;
	}
	
	public void render(){
		GraphicManager.render(this);
	}
	
	public void draw(TextureRegion region, float x, float y, float w, float h){
		checkCache();
		current.add(region, x, y, w, h);
		draws ++;
	}
	
	public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation){
		checkCache();
		current.add(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
		draws ++;
	}
	
	public void draw(String region, float x, float y){
		// TODO: Convert region
	}
	
	public void draw(String region, float x, float y, float rotation){
		checkCache();
		// TODO: Convert region
	}
	
	public SpriteCache getCurrent(){
		return current;
	}
	
	protected void begin(){
		checkCache();
	}
	
	protected void end(){
		cacheIDs.add(current.endCache());
	}
	
	private void checkCache(){
		if(current == null){
			current = new SpriteCache(size, true);
			caches.add(current);
			current.beginCache();
		}else{
			if(draws > size-10){
				cacheIDs.add(current.endCache());
				current = null;
				draws = 0;
				checkCache();
			}
		}
		current.setColor(GraphicManager.getColor());
	}

	@Override
	public void dispose(){
		if(disposed) return;
		for(SpriteCache cache : caches){
			cache.dispose();
		}
		disposed = true;
	}
}
