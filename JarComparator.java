package lab5;

import java.util.Comparator;

public class JarComparator implements Comparator<Jar> {

	@Override
	public int compare(Jar a, Jar b) {
		if (a.Size > b.Size){
			return 1;
		}
		if (a.Size == b.Size){
			return 0;
		}
		else return -1;
	}
	
}
