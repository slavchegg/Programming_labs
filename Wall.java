package lab5;

import java.lang.reflect.Method;
import java.util.ArrayList;

import java.util.ArrayList;

public class Wall implements Environment {
	String Name;
	ArrayList <Shelf> Shelfs = new ArrayList <Shelf>();
	ArrayList <Paper> Papers = new ArrayList <Paper>();
	ArrayList <Cupboard> Cupboards = new ArrayList <Cupboard>();
	public void StealHuman (Human Human){
		
	}
	public Wall (Well Well, String Name){
		Well.Walls.add (this);
		this.Name = Name;
	}
}
