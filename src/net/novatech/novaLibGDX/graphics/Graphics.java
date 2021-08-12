package net.novatech.novaLibGDX.graphics;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

import net.novatech.library.math.Vector2;
import net.novatech.library.math.Vector3f;
import static net.novatech.novaLibGDX.GDXSystem.batch;

import net.novatech.novaLibGDX.GDXSystem;
import net.novatech.novaLibGDX.graphics.surface.CustomSurface;
import net.novatech.novaLibGDX.graphics.surface.Surface;
import net.novatech.novaLibGDX.utils.GDXVectorUtils;


public class Graphics {
	private static Vector3f vec3 = new Vector3f(0,0,0);
	private static Vector2 mouse = new Vector2(0,0);
	private static Vector2 size = new Vector2(0,0);

	private static TextureRegion tempregion = new TextureRegion();

	private static Stack<Batch> batches = new Stack<Batch>();

	private static ObjectMap<String, Surface> surfaces = new ObjectMap<>();
	private static Stack<Surface> surfaceStack = new Stack<>();

	private static Shaders[] currentShaders;

	public static Vector2 mouse() {
		mouse.setX(Gdx.input.getX());
		mouse.setY(Gdx.graphics.getHeight() - Gdx.input.getY());
		return mouse;
	}

	public static Vector2 mouseWorld() {
		vec3.setX(Gdx.input.getX());
		vec3.setY(Gdx.input.getY());
		GDXSystem.camera.unproject(GDXVectorUtils.convert(vec3));
		mouse.setX(vec3.x);
		mouse.setY(vec3.y);
		return mouse;
	}

	public static Vector2 world(float screenx, float screeny) {
		vec3.setX(screenx);
		vec3.setY(screeny);
		GDXSystem.camera.unproject(GDXVectorUtils.convert(vec3));
		mouse.setX(vec3.x);
		mouse.setY(vec3.y);
		return mouse;
	}

	public static Vector2 screen(float worldx, float worldy) {
		vec3.setX(worldx);
		vec3.setY(worldy);
		GDXSystem.camera.project(GDXVectorUtils.convert(vec3));
		mouse.setX(vec3.x);
		mouse.setY(vec3.y);
		return mouse;
	}

	public static Vector2 size() {
		size.setX(Gdx.graphics.getWidth());
		size.setY(Gdx.graphics.getHeight());
		return size;
	}

	public static void clear(Color color) {
		Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}

	public static void clear(float r, float g, float b) {
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}

	public static void useBatch(Batch batch) {
		if(batches.isEmpty()) {
			batches.push(GDXSystem.batch);
		}
		batches.push(batch);
		GDXSystem.batch = batch;
	}

	public static void popBatch() {
		batches.pop();
		GDXSystem.batch = batches.peek();
	}

	public static Surface currentSurface() {
		return surfaceStack.isEmpty() ? null : surfaceStack.peek();
	}

	public static void addSurface(CustomSurface surface) {
		surfaces.put(surface.name(), surface);
	}

	public static void addSurface(String name) {
		Graphics.addSurface(name, 1, 0);
	}

	public static void addSurface(String name, int scale) {
		Graphics.addSurface(name, scale, 0);
	}

	public static void addSurface(String name, int scale, int bind) {
		surfaces.put(name, new Surface(name, scale, bind));
	}

	public static Surface getSurface(String name) {
		if(!surfaces.containsKey(name)) {
			throw new IllegalArgumentException("The surface \"" + name + "\" does not exist!");
		}
		return surfaces.get(name);
	}

	public static void surface(String name) {
		surface(name, true);
	}

	public static void surface(String name, boolean clear) {
		if(!surfaceStack.isEmpty()) {
			end();
			surfaceStack.peek().end(false);
		}

		Surface surface = getSurface(name);
		surfaceStack.push(surface);
		if(drawing()) {
			end();
		}
		
		surface.begin(clear);
		begin();
	}

	public static void surface() {
		Graphics.surface(false);
	}

	public static void surface(boolean end) {
		Graphics.checkSurface();
		Surface surface = surfaceStack.pop();
		end();
		surface.end(true);
		if(!end) {
			Surface current = surfaceStack.empty() ? null : surfaceStack.peek();
			if(current != null) {
				current.begin(false);
			}
			begin();
		}
	}

	public static void flushSurface() {
		Graphics.flushSurface(null);
	}

	public static void flushSurface(String name) {
		Graphics.checkSurface();
		Surface surface = surfaceStack.pop();
		end();
		surface.end(true);
		Surface current = surfaceStack.empty() ? null : surfaceStack.peek();
		if(current != null) {
			current.begin(false);
		}
		
		Graphics.setScreen();
		if(name != null) {
			surface(name);
		}
		batch.draw(surface.texture(), 0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), -Gdx.graphics.getHeight());
		if(name != null) {
			surface();
		}
		end();
		beginCam();
	}

	public static void setScreen() {
		boolean drawing = batch.isDrawing();

		if(drawing) {
			end();
		}
		batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		begin();
	}

	static void checkSurface() {
		if(surfaceStack.isEmpty()) {
			throw new RuntimeException("Surface stack is empty! Set a surface first.");
		}
	}

	public static void beginShaders(Shaders... types) {
		currentShaders = types;
		batch.flush();
		surface("effects1");
	}

	public static void endShaders() {
		if(currentShaders.length == 1) {
			Shaders shader = currentShaders[0];
			tempregion.setRegion(currentSurface().texture());

			Graphics.shader(shader);
			shader.program().begin();
			shader.region = tempregion;
			shader.apply();
			shader.program().end();
			flushSurface();
			Graphics.shader();
		} else {
			int i = 0;
			int index = 2;

			for(Shaders shader : currentShaders) {
				boolean ending = i == currentShaders.length - 1;

				tempregion.setRegion(currentSurface().texture());

				Graphics.shader(shader);
				shader.program().begin();
				shader.region = tempregion;
				shader.apply();
				shader.program().end();
				flushSurface(ending ? null : ("effects" + index));
				Graphics.shader();

				if(!ending) {
					surface("effects" + index);
				}
				
				index = (index == 2 ? 1 : 2);
				i++;
			}
		}
	}

	public static void flush() {
		GDXSystem.batch.flush();
	}

	public static void shader(Shaders shader) {
		shader(shader, true);
	}

	public static void shader(Shaders shader, boolean applyOnce) {

		batch.setShader(shader.program());
		if(applyOnce) {
			shader.program().begin();
			shader.apply();
			shader.program().end();
		}
	}

	public static void shader() {
		batch.setShader(null);
	}

	public static void resize() {
		if(!surfaces.containsKey("effects1")) {
			addSurface("effects1", GDXSystem.cameraScale);
			addSurface("effects2", GDXSystem.cameraScale);
		}

		for(Surface surface : surfaces.values()) {
			surface.resize();
		}
	}

	public static void beginCam() {
		batch.setProjectionMatrix(GDXSystem.camera.combined);
		batch.begin();
	}

	public static void begin() {
		batch.begin();
	}

	public static void end() {
		batch.end();
	}

	public static boolean drawing() {
		return batch.isDrawing();
	}

	public static void dispose() {
		for(Surface surface : surfaces.values()) {
			surface.dispose();
		}
	}
}
