package lab5;

public class JarException extends Exception{
	public JarException (Throwable e){
		this.initCause(e);
	}
}
