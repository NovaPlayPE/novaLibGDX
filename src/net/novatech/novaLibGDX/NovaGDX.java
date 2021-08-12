package net.novatech.novaLibGDX;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;

import net.novatech.library.utils.SystemOS;

public class NovaGDX {
	
	public static final SystemOS PLATFORM = SystemOS.CURRENT_OS;
	public static final float SIZE = (Gdx.app == null ? 0 : (Gdx.app.getType() == ApplicationType.Desktop ? 1f : Gdx.graphics.getDensity() / 1.5f));
	
	public static void log(Object...objects){
		int i = 0;
		for(Object o : objects){
			System.out.print(o);
			if(i++ != objects.length-1)
				System.out.print(", ");
		}
		System.out.println();
	}
	
	public static Object getPrivate(Object object, String name){
		try{
			Field field = ClassReflection.getField(object.getClass(), name);
			field.setAccessible(true);
			return field.get(object);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
