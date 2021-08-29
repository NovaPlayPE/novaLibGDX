package net.novatech.novaLibGDX.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.IntFloatMap;

import net.novatech.novaLibGDX.input.InputManager.ControllerType;
import net.novatech.novaLibGDX.input.InputManager.DeviceType;

public class InputDevice {
	public final DeviceType type;
	public final String name;
	public final Controller controller;
	public final ControllerType controllerType;
	public final Bits pressed = new Bits();
	public final Bits released = new Bits();
	public final IntFloatMap axes = new IntFloatMap();

	public InputDevice(DeviceType type, String name) {
		this(type, name, null);
	}

	public InputDevice(DeviceType type, String name, Controller controller) {
		this.type = type;
		this.name = name;
		this.controller = controller;
		if (controller != null) {
			controllerType = InputManager.getControllerType(controller);
		} else {
			controllerType = null;
		}
	}
}