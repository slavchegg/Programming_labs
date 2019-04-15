package lab5;

import java.util.ArrayList;

public class Shelf extends Thing {
	Wall Wall;
	static Shelf[] Shelves = new Shelf [100];
	ArrayList <Jar> Jars = new ArrayList <Jar>();
	public Shelf (Wall Wall, String Name, Integer Number ){
		this.Name = Name;
		this.Wall = Wall;
		Shelves[Number] = this;
		if (this.Wall != null){
			Wall.Shelfs.add (this);
		}
		if (Jars.size() == 0){
			IsEmpty = true;
		}
		else IsEmpty = false;
	}
	@Override
	public void ChangeMood(Human Human) {
		// TODO Auto-generated method stub
		
	}
	public static Shelf parseShelf(String n) {
		// TODO Auto-generated method stub
		for (Shelf i:Shelves){
			if (i!= null) {
				//+System.out.println(i.Name);
				if (n != i.Name){
					continue;
				}
				else return i;
			}
		}
		//йняршкэ
		Shelf NewShelf = new Shelf (null, n, 2);
		//System.out.println(n);
		return NewShelf;
	} 
}
