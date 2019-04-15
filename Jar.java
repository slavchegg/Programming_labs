package lab5;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Jar implements Serializable{
	String Content;
	String Taste;
	Shelf Shelf;
	Boolean IsEmpty;
	int Size;
	Point Point;
	Date Date;
	String Color;
	String Name;
	Boolean IsDrawn;
	public Jar (int Size, Point Point, boolean IsEmpty, Shelf Shelf, String Name, String Color){
		//Shelf.Jars.add (this);
		this.Name = Name;
		this.Size = Size;
		this.Point = Point;
		this.Shelf = Shelf;
		this.Date = new Date();
		this.IsEmpty = IsEmpty;
		this.Color = Color;
		this.IsDrawn = false;
	}
}
