package lab5;

public abstract class Thing {
	static Boolean IsEmpty;
	String Name;
	static String Empty;
	public static void IAmEmpty (Thing Thing){
		if (IsEmpty == true){
			Empty = "Empty";
		}
		else Empty = "Full";
		System.out.println("The " + Thing.Name + " was " + Empty);
	}
	abstract void ChangeMood (Human Human);
	private void Change(){
		System.out.println("Kek");
	}
}
