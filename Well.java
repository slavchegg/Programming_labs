package lab5;

import java.util.ArrayList;

public class Well implements Environment{
	ArrayList <Wall> Walls = new ArrayList <Wall>();
	public void StealHuman (Human Human){
		
	}
	String Atmosphere;
	String Name;
	public Well ( String Atmosphere, String Name){
		this.Atmosphere = Atmosphere;
		this.Name = Name;
	}
}
