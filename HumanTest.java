package lab5;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class HumanTest {
	private Human Alisa = new Human (false, "Alisa", "Good");
	@Test
	public void dir(){
		equals ("She looked down." == Alisa.LookAt2(Dir.down));
	}
	public static void main(String[] args) throws Exception {
	    JUnitCore runner = new JUnitCore();
	    Result result = runner.run(HumanTest.class);
	    System.out.println("run tests: " + result.getRunCount());
	    System.out.println("failed tests: " + result.getFailureCount());
	    System.out.println("ignored tests: " + result.getIgnoreCount());
	    System.out.println("success: " + result.wasSuccessful());
	}
}

