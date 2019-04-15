package lab5;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class DataBases {
    private ConcurrentLinkedDeque<Jar> JarDeque;
    private Statement statement;
    private Connection studsConnection;
    private Session session;
    
    private JFrame dataBases = new JFrame("Учебная база данных");
    private JButton updateCOllection = new JButton("Загрузить коллекцию");
    private JButton dropCollection = new JButton("Удалить коллекицию");
    private boolean drop;
    private JLabel select = new JLabel("select");
    private JComboBox smth = new JComboBox();
    private JLabel fromPeople = new JLabel("from people");
    private JButton getResult = new JButton("Получить запрос");
    private JPanel mySelect = new JPanel();
    private JLabel nextLine = new JLabel();

	DataBases(ConcurrentLinkedDeque<Jar> JarDeque){this.JarDeque=JarDeque;}
	
	private void createSSHTunnel() {
        String SSHHost = "se.ifmo.ru";
        String SSHUser = "s242411";
        String SSHPassword = "your";
        int SSHPort = 2222;
        String remoteHost = "pg";
        int localPort = 5432;
        int remotePort = 5432;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(SSHUser, SSHHost, SSHPort);
            session.setPassword(SSHPassword);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Соединение установлено...");
            session.connect();
            int assignedPort = session.setPortForwardingL(localPort, remoteHost, remotePort);
            System.out.println("localhost:" + assignedPort + " -> " + remoteHost + ":" + remotePort);
        } catch (Exception e) {
            System.err.print(e);
        }
    }
	void createConnection(){
        createSSHTunnel();
        try {
            Class.forName("org.postgresql.Driver");
            studsConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", "s242411", "hhj117");
            System.out.println("Подключение к базе данных: успешно");
            statement = studsConnection.createStatement();
            createFrame();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    private void createFrame() {
        getResult.setEnabled(false);
        dataBases.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try{
                    if (!drop) statement.execute("drop table people");
                    if (studsConnection!=null) studsConnection.close();
                    session.disconnect();
                }catch (SQLException eSQL){}
            }
        });
        
        updateCOllection.addMouseListener(new MouseAdapter() {
            //@Override
            /*public void mouseClicked(MouseEvent e) {
                try {
                    updateCOllection.setEnabled(false);dropCollection.setEnabled(true);getResult.setEnabled(true);
                    statement.execute("create table people(name varchar(10) primary key,age int, hairColor varchar(7), number int)");
                    for(Jar jar:JarDeque){
                        String newGeroy = "insert into people(name,age,hairColor,number) " +
                                "values(\'"+geroy.name+"\',"+String.valueOf(geroy.age)+",\'"+geroy.hairColor+"\',"+String.valueOf(geroy.tableNumber)+")";
                        statement.execute(newGeroy);
                        drop=false;
                    }
                    //System.out.println("В базу данных загружено "+people.size()+" геров.");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }*/
        });
// удаление коллекции
        dropCollection.setEnabled(false);
        dropCollection.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    dropCollection.setEnabled(false);updateCOllection.setEnabled(true);
                    statement.executeUpdate("drop table people");
                    drop=true;
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
// формируем запрос к базе
        smth.addItem("name");smth.addItem("age");smth.addItem("hairColor");smth.addItem("number");
        //smth.setMaximumSize(new Dimension(10,30));
        smth.setBackground(Color.white);
        getResult.setAlignmentX(Component.CENTER_ALIGNMENT);
        mySelect.setLayout(new BoxLayout(mySelect,BoxLayout.Y_AXIS));
        getResult.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    String s="<html> ";
                    ResultSet rs = statement.executeQuery("select "+smth.getSelectedItem()+" "+fromPeople.getText());
                        while (rs.next()) {
                            s = s +  rs.getString((String) smth.getSelectedItem()) + " <p> ";
                            System.out.println(rs.getString((String) smth.getSelectedItem()));
                        }
                        nextLine.setText(s+ "</html");
                        mySelect.add(nextLine);
                        dataBases.setVisible(true);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        
        JPanel boxFirst = new JPanel();
        boxFirst.setLayout(new BoxLayout(boxFirst,BoxLayout.X_AXIS));
        boxFirst.add(Box.createRigidArea(new Dimension(10,0)));boxFirst.add(updateCOllection);
        boxFirst.add(Box.createRigidArea(new Dimension(20,0)));boxFirst.add(dropCollection);
        JPanel boxSecond = new JPanel();boxSecond.setLayout(new BoxLayout(boxSecond,BoxLayout.X_AXIS));
        boxSecond.add(Box.createRigidArea(new Dimension(5,0)));boxSecond.add(select);
        boxSecond.add(Box.createRigidArea(new Dimension(10,0)));boxSecond.add(smth);
        boxSecond.add(Box.createRigidArea(new Dimension(10,0)));boxSecond.add(fromPeople);
        JPanel box = new JPanel();box.setLayout(new BoxLayout(box,BoxLayout.Y_AXIS));
        box.add(Box.createRigidArea(new Dimension(0,20)));box.add(boxFirst);
        box.add(Box.createRigidArea(new Dimension(0,20)));box.add(boxSecond);
        box.add(Box.createRigidArea(new Dimension(0,10)));box.add(getResult);
        box.add(Box.createRigidArea(new Dimension(0,20)));box.add(mySelect);
        JPanel finish = new JPanel();finish.add(box);
        dataBases.add(finish);
        dataBases.setVisible(true);
        dataBases.setBounds(700,300,650,450);
    }
}
