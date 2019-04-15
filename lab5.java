package lab5;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.*;

import lab5.Human.Author;

public class lab5 {

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException{
		// TODO Auto-generated method stub
		Server client = new Server(6666);
        client.listen();
        ConcurrentLinkedDeque<Jar> JarDeque = new <Jar>ConcurrentLinkedDeque();
        Manager z = new Manager();
        z.setColl(JarDeque);
        class ShutdownHook extends Thread {
            public void run() {
                if (z.fi != ""){ 
                    z.ex_port();
                    System.out.println("���������� ���������");
                } else {
                    System.out.println("���������� ���������");
                }
            }
        }   
        ShutdownHook shutdownHook = new ShutdownHook();
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        /*Logger log = Logger.getLogger("my.logger");
        log.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter());
        log.addHandler(handler);*/
		Human Alisa = new Human (false, "Alisa", "Good");
		Well BigWell = new Well ("Dark", "Well");
		Wall LongWall = new Wall (BigWell, "Wall");
		Shelf OneOfTheShelves = new Shelf (LongWall, "One of the shelves", 0);
		Shelf AnotherShelf = new Shelf (LongWall, "Another shelf", 1);
		Cupboard Cupboard = new Cupboard (LongWall, false, "Cupboard");
		Jar OrangeJar = new Jar ("Orange", "Jam", true, OneOfTheShelves, "Jar");
		Paper Map = new Paper (LongWall, "Geographic Map");
		Paper Picture = new Paper (LongWall, "Picture");
		Human.Author Author = new Human.Author ("Lewis Caroll");
		/*try {
			Wall.class.getInterfaces()[0].getMethod("Suck").invoke(new Wall(BigWell, "Lol"));
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Alisa.LookAt(Dir.down);
		Alisa.Understand(", where she was flyuing to.");
		System.out.println("But it was too " + BigWell.Atmosphere + " in the " + BigWell.Name + ".");
		Alisa.Explore(BigWell, LongWall);
		Alisa.Match(AnotherShelf, Cupboard, LongWall, Map, Picture);
		try{
		Alisa.Take(OrangeJar, OneOfTheShelves);
		}
		catch (JarException a){
			System.out.println("No Jars Avaiable:(");
		}
		Jar.IAmEmpty(OrangeJar);
		try{
			Alisa.Take(OrangeJar, OneOfTheShelves);
			}
			catch (JarException a){
				System.out.println("No Jars Avaiable:(");
			}
		Alisa.Mood = "Sad";
		System.out.print(Alisa.Name + " was " + Alisa.Mood + " and wondered if ");
		Alisa.Throw(OrangeJar);
		System.out.print("But " + Alisa.Name + " was " + Alisa.Mood + ", so ");
		Alisa.Put(OrangeJar, AnotherShelf);
		Scanner in = new Scanner(System.in);
		System.out.println("������� � ����� ���������� ����������?");
		String l = in.nextLine();
		if (l.equals("��") || l.equals("Yes") || l.equals("��") || l.equals("yes")) {
			//log.fine("����� ���������� ���������� �����������");
			System.out.println("��������� �������");
			System.out.println("import {String path}: �������� � ��������� ��� ������ �� �����\n" +
			"load: ���������� ��������� �� �����\n" +
            "add{element}: �������� ����� ������� � ���������\n" +
			"add_if_max {element}: �������� ����� ������� � ���������, ���� ��� �������� ��������� �������� ����������� �������� ���� ���������");*/
			boolean ter = true;
			try{
				while (ter) {
					String ini = msg;
					if (ini.equals("terminate")) {
						System.exit(0);
					}
					z.Analyze(ini);
					//
					for (Jar i:JarDeque){
						System.out.println(i.Name);
					}
			}}catch(NoSuchElementException h){System.exit(0);};
		} else {
			System.out.println ("��� � �������� �����!");
		}
		ServerSocket srvSocket = null;
        try {
            try {
                int i = 0; // ������� �����������
                // ����������� ������ � localhost
                InetAddress ia = InetAddress.getByName("localhost");
                srvSocket = new ServerSocket(6666, 0, ia);

                System.out.println("Server started\n\n");

                while(true) {
                    // �������� �����������
                    Socket socket = srvSocket.accept();
                    System.err.println("Client accepted");
                    // �������� ��������� ������� � ��������� ������
                    new Server().setSocket(i++, socket);
                }
            } catch(Exception e) {
                System.out.println("Exception : " + e);
            }
        } finally {
            try {
                if (srvSocket != null)
                    srvSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

}
