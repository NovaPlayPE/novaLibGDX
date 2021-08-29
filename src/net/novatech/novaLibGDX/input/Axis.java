package net.novatech.novaLibGDX.input;

public class Axis implements InputType {
	public Input min, max;

	public Axis(Input axis) {
		this.min = this.max = axis;
	}

	public Axis(Input min, Input max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public InputType copy() {
		return new Axis(min, max);
	}
}