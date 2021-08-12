package net.novatech.novaLibGDX.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

public class FrameBufferBuffer implements Disposable {
	
	private ObjectMap<String, FrameBuffer> buffers = new ObjectMap<String, FrameBuffer>();

	public FrameBuffer get(String name) {
		if(!buffers.containsKey(name)){
			return add(name, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
		}
		return buffers.get(name);
	}

	public FrameBuffer add(String name, int width, int height) {
		if(buffers.containsKey(name)){
			return buffers.get(name);
		}
		FrameBuffer buffer = new FrameBuffer(Format.RGBA8888, width, height, true);
		buffer.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		buffers.put(name, buffer);
		return buffer;
	}

	public void begin(String name) {
		get(name).begin();
	}

	public void end(String name) {
		get(name).end();
	}

	public Texture texture(String name) {
		return get(name).getColorBufferTexture();
	}

	public void remove(String name) {
		get(name).dispose();
		buffers.remove(name);
	}

	public void dispose() {
		for (FrameBuffer buffer : buffers.values()) {
			buffer.dispose();
		}
	}
}