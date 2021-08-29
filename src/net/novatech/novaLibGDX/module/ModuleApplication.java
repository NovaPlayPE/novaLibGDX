package net.novatech.novaLibGDX.module;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import net.novatech.novaLibGDX.GDXSystem;
import net.novatech.novaLibGDX.graphics.Graphics;

public abstract class ModuleApplication extends ApplicationAdapter {

	private static ModuleApplication instance;
	protected ObjectMap<Class<? extends Module>, Module> modules = new ObjectMap<>();
	protected Array<Module> modulearray = new Array<Module>();
	
	public ModuleApplication() {
		instance = this;
	}

	abstract public void init();
	public void preInit() {}
	public void postInit() {}
	public void update() {}

	protected <N extends Module> void module(N t) {
		try {
			modules.put((Class<? extends Module>) t.getClass(), t);
			modulearray.add(t);
			t.preInit();
		} catch (RuntimeException e) {
			throw e;
		}
	}

	@Override
	public void resize(int width, int height) {
		Module.screen.setX(width);
		Module.screen.setY(height);
		Graphics.resize();
		for (Module module : modulearray) {
			module.resize(width, height);
		}
	}

	@Override
	public final void create() {
		init();
		preInit();
		for (Module module : modulearray) {
			module.init();
		}
		postInit();
	}

	@Override
	public void render() {
		for (Module module : modulearray) {
			module.update();
		}

		update();
	}

	@Override
	public void pause() {
		for (Module module : modulearray)
			module.pause();
	}

	@Override
	public void resume() {
		for (Module module : modulearray)
			module.resume();
	}

	@Override
	public void dispose() {
		for(Module module : modulearray) {
			module.dispose();
		}
		GDXSystem.dispose();
	}
}