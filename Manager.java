package lab5;

import com.google.gson.*;


import java.awt.Color;
import java.awt.Point;
import java.io.*;
import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Date;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentLinkedDeque;

import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JLabel;

public class Manager {
	String fi="";
	JarComparator JC = new JarComparator();
	ConcurrentLinkedDeque<Jar> x = new ConcurrentLinkedDeque<Jar>();
	Date dat = new Date();
	String file = "coll.txt";
	
	private static final String url = "jdbc:postgresql://pg/studs";
    private static final String user = "s242411";
    private static final String password = "hhj117";
    
    private void initDBConnect(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getFieldNames(Jar jar){
        Class c = jar.getClass();
        ArrayList<String> fieldNames = new ArrayList<>();
        /*for (Field item: c.getSuperclass().getDeclaredFields()){
            if (!item.getName().equals("serialVersionUID"))
                fieldNames.add(item.getName());
        }*/
        for (Field item: c.getDeclaredFields()){
            if (!item.getName().equals("serialVersionUID"))
                fieldNames.add(item.getName());
        }
        fieldNames.remove("IsDrawn");
        fieldNames.remove("Shelf");
        fieldNames.remove("Point");
        return fieldNames;
    }

    public void createDBTable(){
        Jar jar = new Jar(200, new Point(100,100), true, null, "Demo", "RED");
        String createQuery = jar.getClass().getName().replaceAll("lab5.", "").toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append("drop table ")
        .append(createQuery)
        .append(";");
        sb.append("create table ")
                .append(createQuery)
                .append("(");
        ArrayList<String> fieldNames = getFieldNames(jar);
        sb.append("id integer PRIMARY KEY, ");
        sb.append(fieldNames.get(0)).append(" integer,");
        sb.append(fieldNames.get(1)).append(" boolean,");
        sb.append(fieldNames.get(2)).append(" text,");
        sb.append(fieldNames.get(3)).append(" text");
        sb.append(")").append(";");
        //sb.append("CREATE TABLE users (id INTEGER PRIMARY KEY,login CHAR(64),password CHAR(64));");
        try {
            DriverManager.getConnection(url, user, password).createStatement().execute(sb.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void insertDB(int id, Jar jar){
        String p = ", ";
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(jar.getClass().getName().replaceAll("lab5.", "").toLowerCase()).append("(");
        ArrayList<String> fieldNames = getFieldNames(jar);
        sb.append("id").append(p)
                .append(fieldNames.get(0)).append(p);
        sb.append(fieldNames.get(1)).append(p);
        sb.append(fieldNames.get(2)).append(p);
        sb.append(fieldNames.get(3));
                sb.append(") VALUES (");
        sb.append(id).append(p)
                .append(jar.Size).append(p)
                .append(jar.IsEmpty).append(p)
                .append("\'").append(jar.Name).append("\'").append(p)
                .append("\'").append(jar.Color).append("\'")
                .append(");");
        //System.out.println(sb.toString());
        try {
            DriverManager.getConnection(url, user, password).createStatement().executeUpdate(sb.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public void save (){
        createDBTable();
    	AtomicInteger counter = new AtomicInteger(0);
    	x.forEach((j) ->{
    		insertDB(counter.intValue(),j);
    		counter.getAndIncrement();
    	});
    }

    public void clearDB(){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ").append(Jar.class.getName().replaceAll("lab5", "").toLowerCase()).append(";");

        try {
            DriverManager.getConnection(url, user, password).createStatement().executeUpdate(sb.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /*public int find(Jar jar){
    	int i = 0;
    	x.forEach((item)->{
    		if(item.equals(jar)){
    			i+=1;
    		}
    	});
        return 0;
    }

    public void deleteDB(Jar jar){
        int id = find(temp);
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ").append(Jar.class.getName().replaceAll("lab5", "").toLowerCase())
                .append(" where id = ").append(id).append(";");

        try {
            DriverManager.getConnection(url, user, password).createStatement().executeUpdate(sb.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }

    }*/
	
	public void setColl(ConcurrentLinkedDeque E) {
		x = E;
	}
	
	public void im_port(String f, Server S) throws IOException {
	    Parser m = new Parser();
	    file = f;
	    m.parse(x, f, S);
	}

    public void ex_port() throws IOException {
    	File ff = new File (file);
        String jsonstr = new Gson().toJson(x);
        ff.setWritable(true);
        ff.setReadable(true);
        ff.setExecutable(true);
        try (FileOutputStream out = new FileOutputStream(ff);
             BufferedOutputStream bos = new BufferedOutputStream(out)) {
            byte[] buffer = jsonstr.getBytes();
            bos.write(buffer);
        } catch (IOException ex) {
            
        }
    }
    public void add(String s) {
    	Gson gs = new Gson();
		x.add(gs.fromJson(s, Jar.class));
    }
    public void load(Server S) throws IOException {
		x.clear();
    	Parser m = new Parser();
    		m.parse(x, file, S);
	}
    public void addMax(String h) {
    	Gson gs = new Gson();
    	Jar possible = gs.fromJson(h, Jar.class);
    	Jar t = x.stream().max(JC).get();
    	if (JC.compare(possible, t) > 0){
    		System.out.println(possible.Size);
    		x.add(possible);
    	}
    	else System.out.println(t.Size);
    }
    
    public void Analyze(String com, Server S) throws IOException  {
        String[] z = com.split(" ");
        if (z[0].equals("import")) {
            Remover kl = new Remover();
            String h=kl.removefrom(z[1]);
            this.fi=h;
            im_port(h, S);
        }
        if (z[0].equals("load")) {
        	load(S);
        }
        if (z[0].equals("add")) {
        	String h = com.substring(4);
            this.fi=h;
            add(h);
        }
        /*if (z[0].equals("info")) {
        	info();
        }*/
        if (z[0].equals("add_if_max")) {
            String h = com.substring(11);
            this.fi=h;
            addMax (h);
        }
        if (z[0].equals("terminate")) {
        	ex_port();
        }
    }
    
    //3лаба
    
    public void download () throws FileNotFoundException, IOException{
    	
    	String line;
		String in;
    	BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			line=reader.readLine();
			while (line != null){
				Gson gs = new Gson();
				x.add(gs.fromJson(line, Jar.class));
				x.getLast().Date = new Date();
				line=reader.readLine();
			}
    }
}
