package net.novatech.novaLibGDX.input;

public class KeyBind {
	public final String name;
	public final InputType input;
	public final KeyCategory category;

	public KeyBind(String name, InputType input, KeyCategory category) {
		this.name = name;
		this.input = input;
		this.category = category;
	}
}
