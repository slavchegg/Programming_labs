package lab5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Paper extends Thing{
	Method privateStringMethod;
	public Paper (Wall Wall, String Name) {
		Wall.Papers.add(this);
		this.Name = Name;
		try {
			privateStringMethod = Thing.class.
			getDeclaredMethod("Change", null);
			privateStringMethod.setAccessible(true);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Change(){
		Thing lol = new Thing(){

			@Override
			void ChangeMood(Human Human) {
				// TODO Auto-generated method stub
				
			}
			
		};
		try {
			privateStringMethod.invoke(lol);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	void ChangeMood(Human Human) {
		// TODO Auto-generated method stub
		
	}
}
