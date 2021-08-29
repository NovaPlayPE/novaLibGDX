package net.novatech.novaLibGDX.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import net.novatech.library.math.Vector3f;
import net.novatech.library.math.Vector3d;
import net.novatech.library.math.Vector3i;

import java.util.Map;
import java.util.HashMap;

public class GDXVectorUtils {
	
	public static Map<net.novatech.library.math.Vector2, Vector2> vectors2d = new HashMap<net.novatech.library.math.Vector2, Vector2>();

	public static net.novatech.library.math.Vector2 createVector(int x, int y){
		net.novatech.library.math.Vector2 vec = new net.novatech.library.math.Vector2(x,y);
		vectors2d.put(vec,  new Vector2(x,y));
		return vec;
	}
	
	public static Vector2 getGDXVector2(net.novatech.library.math.Vector2 vec) {
		return vectors2d.get(vec);
	}
	
	public static Vector2 update(net.novatech.library.math.Vector2 vec) {
		Vector2 v = vectors2d.get(vec);
		v.set((float)vec.getX(), (float)vec.getY());
		return v;
	}
	
	public static void removeVector(net.novatech.library.math.Vector2 vec) {
		if(vectors2d.containsKey(vec)) {
			vectors2d.remove(vec);
		}
	}
	
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