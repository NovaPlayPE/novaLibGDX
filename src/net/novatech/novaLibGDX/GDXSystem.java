package net.novatech.novaLibGDX;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectMap;

import net.novatech.novaLibGDX.graphics.GraphicManager;
import net.novatech.novaLibGDX.graphics.Graphics;


public class GDXSystem {
	public static OrthographicCamera camera = new OrthographicCamera();
	public static Batch batch = new SpriteBatch();
	public static BitmapFont font;
	public static int cameraScale = 1;

	static {
		for(String s : new ObjectMap.Keys<String>(Colors.getColors())) {
			if(s != null) {
				Colors.put(s.toLowerCase().replace("_", ""), Colors.get(s));
			}
		}
		Colors.put("crimson", Color.SCARLET);
		Colors.put("scarlet", Color.SCARLET);
	}

	public static void dispose() {
		Graphics.dispose();
		GraphicManager.dispose();

		batch.dispose();

		if(font != null) {
			font.dispose();
		}
	}
}