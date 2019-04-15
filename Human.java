package lab5;

public class Human extends Creature{
	Boolean isMale;
	String Name;
	public String Mood;
	String Male;
	public Human (Boolean isMale, String Name, String Mood){
		this.isMale = isMale;
		this.Name = Name;
		this.Mood = Mood;
		if (isMale == true){
			Male = "He";
		}
		else Male = "She";
	}
	static class Author {
		String Name;
		public Author (String Name){
			this.Name = Name;
		}
		public void greet() {
			// TODO Auto-generated method stub
			System.out.println("Hello, I'm " + this.Name + " and I want to tell you a story.");			
		};
		
	}
	class Greeting{
		void greet(){
			Author Fame = new Author(null){
				public void Hello(){
					super.greet();
				}
			};
			Fame.greet();
		}
	}
	public void LookAt (Dir Dir){
		Dir dir  = Dir;
			System.out.println( Male + " looked " + dir + ". ");
	}
	public String LookAt2 (Dir Dir){
		Dir dir  = Dir;
			return ( Male + " looked " + dir + ". ");
	}
	public void Understand (String meaning) {
		System.out.println (Male + " tried to understand" + meaning);
	}
	
	public void Match (Shelf Shelf, Cupboard Cupboard, Wall Wall, Paper Paper, Paper Paper2){
		System.out.println (Male + " matched " + Cupboard.Name +", " + Shelf.Name + ", " + Paper.Name + ", " + Paper2.Name + " instead of the " + Wall.Name + ".");
	}
	public void Explore (Well Well, Wall Wall){
		System.out.println("She started exploring " + Wall.Name + " of the " + Well.Name + ".");
	}
	public void Take (Jar Jar, Shelf Shelf) throws JarException{
		if (Shelf.Jars.size() < 1) {
			throw new JarException(null);
		}
		Shelf.Jars.remove (Jar);
		System.out.println(Male + " took a jar from the " + Shelf.Name + ". It was " + "\"" + Jar.Taste + Jar.Content + "\"" + " written.");
	}
	public void Put (Jar Jar, Shelf Shelf){
		Shelf.Jars.add (Jar);
		System.out.println(Male + " put a " + Jar.Name + " on " + Shelf.Name + ".");
	} 
	public void Throw (Jar Jar){
		if (Jar == null){
			throw new NullPointerException ("Jar has to be!");
		}
		if (Mood == "Sad"){
			System.out.println(Male + " throw a " + Jar.Name + ".");
		}
		else System.out.println(Male + " didn't throw a " + Jar.Name + ".");
		Mood = "Afraid";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Mood == null) ? 0 : Mood.hashCode());
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + ((isMale == null) ? 0 : isMale.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Human other = (Human) obj;
		if (Mood == null) {
			if (other.Mood != null)
				return false;
		} else if (!Mood.equals(other.Mood))
			return false;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		if (isMale == null) {
			if (other.isMale != null)
				return false;
		} else if (!isMale.equals(other.isMale))
			return false;
		return true;
	}
}
