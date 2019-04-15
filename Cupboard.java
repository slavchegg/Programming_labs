package lab5;

public class Cupboard extends Thing {
	Boolean IsEmpty;
	public Cupboard (Wall Wall, Boolean IsEmpty, String Name){
		this.IsEmpty = IsEmpty;
		this.Name = Name;
		Wall.Cupboards.add(this);
	}

	@Override
	void ChangeMood(Human Human) {
		// TODO Auto-generated method stub
		
	}
}
