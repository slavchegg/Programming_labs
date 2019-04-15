package lab5;

public interface Environment {
	void StealHuman (Human Human);
	default void Suck () {
		System.out.println("Luck");
	}
}
