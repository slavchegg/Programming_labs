package lab5;

public class Remover {
	public static String removeChar(String s, char a,char b,char c,char d,char e) {
		String r = "";
		for (int i = 0; i < s.length(); i ++) {
			if (s.charAt(i) != a &&s.charAt(i) != b&&s.charAt(i) != c&&s.charAt(i) != d&&s.charAt(i) != e){ 
				r += s.charAt(i);
			}
        }
        return r;
    }
	public String removefrom(String s) {
		String h = removeChar(s, '}','{','[',']','"');
		return h;
    }
    /*public String removefields(String s,char t){
    	String r = "";
    	boolean mu=false;
    	for (int i = 0; i < s.length(); i ++) {
    		if (mu) {r += s.charAt(i);}
    		if (s.charAt(i)==t) {
    			mu=true;
    		}
    	}
    	return r;   
    }*/
}
