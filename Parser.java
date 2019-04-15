package lab5;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.io.File;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


public class Parser {
	Scanner scanner = new Scanner (System.in);
	public void parse(ConcurrentLinkedDeque<Jar> x,String g, Server S) throws IOException{
		File j1 = new File(""+ g);
		j1.setExecutable(true);
		j1.setReadable(true);
		j1.setWritable(true);
		String line;
		String in;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(j1)))){
			line=reader.readLine();
			while (line != null){
				Gson gs = new Gson();
				x.add(gs.fromJson(line, Jar.class));
				line=reader.readLine();
			}
		} 
		catch(IOException e){
			Manager z = new Manager();
			z.setColl(x);
			System.out.println("Файл не найден(ЧТЕНИЕ)");
			try{
				while (true) {
					in = scanner.nextLine();
		            String[] message = in.split(" ");
					if (!(message[0].equals("add") || message[0].equals("add_if_max") || message[0].equals("info")||message[0].equals("import")||message[0].equals("load") || message[0].equals("terminate"))) {
		            	System.out.println("Не существует такой команды");
		            	}
		            else {
		            	if (message[0].equals("terminate")) {
		            		System.exit(0);
		            	}
		            	z.Analyze(in, S);
		            }
					S.send(x);
			}}catch(Exception h){System.exit(0);};   
    	}
		catch (JsonSyntaxException e){
			Manager z = new Manager();
			z.setColl(x);
			System.out.println("Неверный формат файла");
			try{
				while (true) {
					in = scanner.nextLine();
		            String[] message = in.split(" ");
					if (!(message[0].equals("add") || message[0].equals("add_if_max") || message[0].equals("info")||message[0].equals("import")||message[0].equals("load") || message[0].equals("terminate"))) {
		            	System.out.println("Не существует такой команды");
		            	}
		            else {
		            	if (message[0].equals("terminate")) {
		            		System.exit(0);
		            	}
		            	z.Analyze(in, S);
		            }
					S.send(x);
			}}catch(Exception h){System.exit(0);}; 
		}
    }
}
