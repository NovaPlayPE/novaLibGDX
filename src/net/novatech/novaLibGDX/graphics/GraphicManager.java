package net.novatech.novaLibGDX.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import net.novatech.novaLibGDX.GDXSystem;

public class GraphicManager {
	private static Array<GraphicCache> caches = new Array<>();
	private static GraphicCache current;
	private static Color color = Color.WHITE.cpy();

	public static void render(GraphicCache cache) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		for (int i = 0; i < cache.caches.size; i++) {
			cache.caches.get(i).setProjectionMatrix(GDXSystem.batch.getProjectionMatrix());

			cache.caches.get(i).begin();
			cache.caches.get(i).draw(cache.cacheIDs.get(i));
			cache.caches.get(i).end();
		}
	}

	public static Color getColor() {
		return color;
	}

	public static void color(Color color) {
		GraphicManager.color.set(color);
	}

	public static void color() {
		color(Color.WHITE);
	}

	public static void draw(TextureRegion region, float x, float y, float w, float h) {
		checkCache();
		current.draw(region, x, y, w, h);
	}

	public static void draw(TextureRegion region, float x, float y, float originX, float originY, float width,
			float height, float scaleX, float scaleY, float rotation) {
		checkCache();
		current.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
	}

	public static void draw(String region, float x, float y) {
		checkCache();
		current.draw(region, x, y);
	}

	public static void draw(String region, float x, float y, float rotation) {
		checkCache();
		current.draw(region, x, y, rotation);
	}

	public static void begin() {
		begin(2000);
	}

	public static void begin(int size) {
		if(current != null) {
			throw new RuntimeException("Call end() before begin()!");
		}
		
		current = new GraphicCache(size);
		current.begin();
		caches.add(current);
	}

	public static GraphicCache end() {
		if(current == null) {
			throw new RuntimeException("Call begin() before end()!");
		}
		
		current.end();
		GraphicCache c = current;
		current = null;

		return c;
	}

	public static void dispose() {
		for(GraphicCache cache : caches) {
			cache.dispose();
		}

		caches.clear();
	}

	private static void checkCache() {
		if(current == null) {
			throw new RuntimeException("Call begin() before drawing!");
		}
	}
}
