package net.novatech.novaLibGDX.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import net.novatech.library.math.Vector3f;
import net.novatech.library.math.Vector3d;
import net.novatech.library.math.Vector3i;

public class GDXVectorUtils {
	
	public static Vector3 convert(Vector3i vec) {
		return convert(vec.asFloat());
	}
	
	public static Vector3 convert(Vector3d vec) {
		return convert(vec.asFloat());
	}
	
	public static Vector3 convert(Vector3f vec) {
		Vector3 v = new Vector3(vec.getX(), vec.getY(), vec.getZ());
		return v;
	}
	
	public static Vector3f convert(Vector3 vec) {
		Vector3f v = new Vector3f(vec.x, vec.y, vec.z);
		return v;
	}
	
	public static Vector2 convert(net.novatech.library.math.Vector2 vec) {
		Vector2 v = new Vector2((float)vec.getX(), (float)vec.getY());
		return v;
	}
	
	public static net.novatech.library.math.Vector2 convert(Vector2 vec){
		net.novatech.library.math.Vector2 v = new net.novatech.library.math.Vector2((double)vec.x, (double)vec.y);
		return v;
	}
	
}