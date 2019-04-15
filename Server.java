package lab5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.google.gson.JsonSyntaxException;

import executeIt.MonoThreadClientHandler;
public class Server {
	static ExecutorService executeIt = Executors.newFixedThreadPool(10);
    public static ConcurrentLinkedDeque<Jar> JarDeque = new <Jar>ConcurrentLinkedDeque();
    private static final String password = "120399";
    static Manager z = new Manager();
    public static boolean modified;
 
    public Server(int port) throws SocketException, IOException {
        z.setColl(JarDeque);  
    }
    
    private static class ServerGUI extends JFrame{
    	private ServerGUI(){
    		super ("Collection Management");
    		login (this);
    	}
    	private void login(JFrame current){
            JFrame login = new JFrame("Вход");
            login.setDefaultCloseOperation(EXIT_ON_CLOSE);
            login.setSize(400, 100);
            login.setResizable(false);
            login.setLocationRelativeTo(null);
            
            JLabel invit = new JLabel("Введите пароль:");
            JLabel error = new JLabel("Неверный пароль");
            error.setForeground(Color.RED);
            error.setVisible(false);
            JPasswordField pwField = new JPasswordField();
            pwField.setEchoChar('•');
            pwField.addFocusListener(new FocusListener(){
            	public void focusGained(FocusEvent e) { 
            		error.setVisible(false);
            	}

				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}
            	
            });
            JRadioButton showPW = new JRadioButton("Показать пароль");
            showPW.addActionListener((event)-> pwField.setEchoChar(showPW.isSelected()?0:'•'));
            JButton agree = new JButton("Подтвердить");
            agree.addActionListener((event)->
            {
                if(Arrays.equals(pwField.getPassword(), password.toCharArray())){
                    login.dispose();
                    openDaWay();
                    
                }
                else {
                	error.setVisible(true);
                	pwField.setText("");
                }
            });

            GroupLayout gl = new GroupLayout(login.getContentPane());
            login.getContentPane().setLayout(gl);
            gl.setAutoCreateGaps(true);
            gl.setAutoCreateContainerGaps(true);
            gl.setHorizontalGroup(
                    gl.createSequentialGroup()
                            .addComponent(invit)
                            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(pwField)
                                    .addComponent(showPW)
                            )
                            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(agree)
                                    .addComponent(error)
                            )
            );
            gl.setVerticalGroup(
                    gl.createSequentialGroup()
                            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(invit)
                                    .addComponent(pwField)
                                    .addComponent(agree)
                            )
                            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(showPW)
                                    .addComponent(error)
                            )
            );
            login.getRootPane().setDefaultButton(agree);
            login.setVisible(true);
            current.setEnabled(false);
        }
    	
    	private  void customization(Jar Jar, JTree My,DefaultMutableTreeNode selectedNode){
            JFrame customize = new JFrame("Объект");
            setEnabled(false);
            customize.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            customize.setSize(285, 300);
            customize.setResizable(false);
            customize.setLocationRelativeTo(null);
            JLabel[] charact = new JLabel[6];
            for(int i = 0; i < 6; i++){
                charact[i] = new JLabel();
            }
            charact[0].setText("Имя");
            charact[1].setText("Размер");
            charact[2].setText("Пустая");
            charact[3].setText("Цвет");
            charact[4].setText("X");
            charact[5].setText("Y");

            JTextField name = new JTextField(Jar.Name);
            JTextField size = new JTextField(Integer.toString(Jar.Size));
            JCheckBox isEmpty = new JCheckBox();
            isEmpty.setSelected(Jar.IsEmpty);
            JTextField color = new JTextField(Jar.Color);
            JTextField xCoord = new JTextField(Integer.toString(Jar.Point.x));
            JTextField yCoord = new JTextField(Integer.toString(Jar.Point.y));  

            JButton cancel = new JButton("Отмена");
            
            cancel.setPreferredSize(new Dimension(100,20));
            cancel.addActionListener((event)-> {
                    customize.dispose();
                    setVisible(true);
                    setEnabled(true);
            });

            JButton accept = new JButton("Принять");
            accept.addActionListener((event)->
            {
                try {
                    Jar.Name = name.getText();
                    Jar.IsEmpty = isEmpty.isSelected();
                    Jar.Size = Integer.valueOf(size.getText());
                    Jar.Point.x = Integer.valueOf(xCoord.getText());
                    Jar.Point.y = Integer.valueOf(yCoord.getText());
                    Jar.Color = color.getText();
                    Jar.Date = new Date();
                    JarDeque.add(Jar);
                    ((DefaultMutableTreeNode) selectedNode.getParent()).add(new DefaultMutableTreeNode(Jar.Name));
                    selectedNode.removeFromParent();
                    My.updateUI();
                    customize.dispose();
                    setVisible(true);
                    setEnabled(true);
                } catch (ArrayIndexOutOfBoundsException aiofbe) {
                    JOptionPane.showMessageDialog(customize, "Неверный формат кода цвета.\n Формат [r,g,b], где буква число от 0 до 255", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(customize, "Неверный формат числового поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e){
                    JOptionPane.showMessageDialog(customize, "Невреный формат поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    //e.printStackTrace();
                }
            });

            cancel.setSize(accept.getSize());
            cancel.setPreferredSize(accept.getPreferredSize());
            GroupLayout gl1 = new GroupLayout(customize.getContentPane());
            customize.getContentPane().setLayout(gl1);
            gl1.setAutoCreateGaps(true);
            gl1.setAutoCreateContainerGaps(true);
            gl1.setHorizontalGroup(
                    gl1.createSequentialGroup()
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(charact[0]).addComponent(charact[1]).addComponent(charact[2]).addComponent(charact[3])
                                    .addComponent(charact[4]).addComponent(charact[5]).addComponent(accept)
                            )
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.CENTER)
                                    .addComponent(name).addComponent(size).addComponent(isEmpty).addComponent(color).addComponent(xCoord)
                                    .addComponent(yCoord).addComponent(cancel)
                            )
            );
            gl1.setVerticalGroup(
                    gl1.createSequentialGroup()
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(charact[0])
                                    .addComponent(name)
                            )
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(charact[1])
                                    .addComponent(size)
                            )
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(charact[2])
                                    .addComponent(isEmpty)
                            )
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(charact[3])
                                    .addComponent(color)
                            )
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(charact[4])
                                    .addComponent(xCoord)
                            )
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(charact[5])
                                    .addComponent(yCoord)
                            )
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(accept)
                                    .addComponent(cancel)
                            )
            );
            customize.pack();
            customize.setVisible(true);

        }
    	
    	private  void add(JTree My,DefaultMutableTreeNode selectedNode, boolean full){
    		Jar Jar = new Jar(0, new Point(0,0), true, null, "", null );
            JFrame customize = new JFrame("Объект");
            setEnabled(false);
            customize.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            customize.setSize(285, 300);
            customize.setResizable(false);
            customize.setLocationRelativeTo(null);
            JLabel[] charact = new JLabel[6];
            for(int i = 0; i < 6; i++){
                charact[i] = new JLabel();
            }
            charact[0].setText("Имя");
            charact[1].setText("Размер");
            charact[2].setText("Пустая");
            charact[3].setText("Цвет");
            charact[4].setText("X");
            charact[5].setText("Y");

            JTextField name = new JTextField();
            JTextField size = new JTextField();
            JCheckBox isEmpty = new JCheckBox();
            if (full == true){
            	isEmpty.setSelected(false);
            }
            else {
            	isEmpty.setSelected(true);
            }
            isEmpty.setEnabled(false);
            JTextField color = new JTextField();
            JTextField xCoord = new JTextField();
            JTextField yCoord = new JTextField();  

            JButton cancel = new JButton("Отмена");
            
            cancel.setPreferredSize(new Dimension(100,20));
            cancel.addActionListener((event)-> {
                    customize.dispose();
                    setVisible(true);
                    setEnabled(true);
            });

            JButton accept = new JButton("Принять");
            accept.addActionListener((event)->{
                try {
                    Jar.Name = name.getText();
                    Jar.IsEmpty = isEmpty.isSelected();
                    Jar.Size = Integer.valueOf(size.getText());
                    Jar.Point.x = Integer.valueOf(xCoord.getText());
                    Jar.Point.y = Integer.valueOf(yCoord.getText());
                    Jar.Color = color.getText();
                    Jar.Date = new Date();
                    JarDeque.add(Jar);
                    selectedNode.add(new DefaultMutableTreeNode(Jar.Name));
                    My.updateUI();
                    customize.dispose();
                    setVisible(true);
                    setEnabled(true);
                } catch (ArrayIndexOutOfBoundsException aiofbe) {
                    JOptionPane.showMessageDialog(customize, "Неверный формат кода цвета.\n Формат [r,g,b], где буква число от 0 до 255", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(customize, "Неверный формат числового поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e){
                    JOptionPane.showMessageDialog(customize, "Невреный формат поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    //e.printStackTrace();
                }
            });

            cancel.setSize(accept.getSize());
            cancel.setPreferredSize(accept.getPreferredSize());
            GroupLayout gl1 = new GroupLayout(customize.getContentPane());
            customize.getContentPane().setLayout(gl1);
            gl1.setAutoCreateGaps(true);
            gl1.setAutoCreateContainerGaps(true);
            gl1.setHorizontalGroup(
                    gl1.createSequentialGroup()
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(charact[0]).addComponent(charact[1]).addComponent(charact[2]).addComponent(charact[3])
                                    .addComponent(charact[4]).addComponent(charact[5]).addComponent(accept)
                            )
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.CENTER)
                                    .addComponent(name).addComponent(size).addComponent(isEmpty).addComponent(color).addComponent(xCoord)
                                    .addComponent(yCoord).addComponent(cancel)
                            )
            );
            gl1.setVerticalGroup(
                    gl1.createSequentialGroup()
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(charact[0])
                                    .addComponent(name)
                            )
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(charact[1])
                                    .addComponent(size)
                            )
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(charact[2])
                                    .addComponent(isEmpty)
                            )
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(charact[3])
                                    .addComponent(color)
                            )
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(charact[4])
                                    .addComponent(xCoord)
                            )
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(charact[5])
                                    .addComponent(yCoord)
                            )
                            .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(accept)
                                    .addComponent(cancel)
                            ));
            customize.pack();
            customize.setVisible(true);
        }
    	
    	private void openDaWay(){
    		this.setPreferredSize(new Dimension(520,520));
            this.setMinimumSize(new Dimension(560,500));
            TreeDemo td = new TreeDemo(JarDeque, "Jars", "Empty","NotEmpty");
            JTree MyTree = td.getTree();
            MyTree.setMinimumSize(new Dimension(100, 200));
            DefaultTreeModel model = (DefaultTreeModel)MyTree.getModel();

            JScrollPane panel_ofTree = new JScrollPane(MyTree);
            panel_ofTree.setPreferredSize(new Dimension(100, 200));

            JLabel selectedLabel = new JLabel(" ");
            JLabel timeLabel = new JLabel("Добро пожаловать!");
            
            /*MyTree.getSelectionModel().addTreeSelectionListener((event)-> {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) MyTree.getLastSelectedPathComponent();
                try {
                    selectedLabel.setText(((Jar) selectedNode.getUserObject()).getFullInfo());
                    timeLabel.setText("Загружено на сервер: " + ((Jar) selectedNode.getUserObject()).getDate().toString());
                } catch (NullPointerException e1) {
                    selectedLabel.setText("Дерево свернуто");
                    timeLabel.setText("");
                } catch (ClassCastException e2) {
                    selectedLabel.setText(selectedNode.getUserObject().toString());
                    timeLabel.setText("");
                }
            });*/
            
            JMenuBar menuBar = new JMenuBar();
            JMenu mainMenu = new JMenu("Коллекция");
            JMenu DBMenu = new JMenu ("База данных");
            
            DBMenu.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
            	
            });
            
            JMenuItem DBSave = new JMenuItem ("Сохранить");
            DBMenu.add(DBSave);
            DBSave.addActionListener((event) ->{
            	z.save();
            });
            JMenuItem DBClear = new JMenuItem ("Очистить");
            DBMenu.add(DBClear);
            DBClear.addActionListener((event) ->{
            	z.createDBTable();
            });
            
           mainMenu.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				selectedLabel.setText(" ");
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			} 
           });
            
            JMenuItem loadItem = new JMenuItem("Загрузить");
            mainMenu.add(loadItem);
            loadItem.addActionListener((event)-> {
                try {
					z.download();
				} catch (JsonSyntaxException e) {
					selectedLabel.setForeground(Color.RED);
					selectedLabel.setText("Неверный формат файла");
				}
                catch (Exception e) {

				}
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
                DefaultMutableTreeNode Empty = (DefaultMutableTreeNode) root.getFirstChild();
                DefaultMutableTreeNode NotEmpty = (DefaultMutableTreeNode) root.getLastChild();
                Empty.removeAllChildren();
                NotEmpty.removeAllChildren();
                td.createJar(JarDeque, root);
                MyTree.updateUI();
                panel_ofTree.setPreferredSize(MyTree.getSize());
                panel_ofTree.revalidate();
            });
            
            JMenuItem updateItem = new JMenuItem("Перечитать");
            mainMenu.add(updateItem);
            updateItem.addActionListener((event)-> {
            	JarDeque.clear();
                try {
					z.download();
				} catch (Exception e) {
					selectedLabel.setForeground(Color.RED);
					selectedLabel.setText("Неверный формат файла");
				}
                
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
                DefaultMutableTreeNode Empty = (DefaultMutableTreeNode) root.getFirstChild();
                DefaultMutableTreeNode NotEmpty = (DefaultMutableTreeNode) root.getLastChild();
                Empty.removeAllChildren();
                NotEmpty.removeAllChildren();
                td.createJar(JarDeque, root);
                MyTree.updateUI();
                panel_ofTree.setPreferredSize(MyTree.getSize());
                panel_ofTree.revalidate();
            });
            
            JMenuItem saveItem = new JMenuItem("Сохранить");
            mainMenu.add(saveItem);
            saveItem.addActionListener((event)-> {
                try {
					z.ex_port();
				} catch (Exception e) {
					selectedLabel.setForeground(Color.RED);
					selectedLabel.setText("Сохранение не удалось");
				}
            });
            
            JMenuItem deleteItem = new JMenuItem("Очистить");
            mainMenu.add(deleteItem);
            deleteItem.addActionListener((event)-> {
            	JarDeque.clear();                
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
                DefaultMutableTreeNode Empty = (DefaultMutableTreeNode) root.getFirstChild();
                DefaultMutableTreeNode NotEmpty = (DefaultMutableTreeNode) root.getLastChild();
                Empty.removeAllChildren();
                NotEmpty.removeAllChildren();
                td.createJar(JarDeque, root);
                MyTree.updateUI();
                panel_ofTree.setPreferredSize(MyTree.getSize());
                panel_ofTree.revalidate();
            });
            
            
            JPanel main_panel = new JPanel();
            GroupLayout gl_main = new GroupLayout(main_panel);
            main_panel.setLayout(gl_main);
            
            mainMenu.addSeparator();
            menuBar.add(mainMenu);
            menuBar.add(DBMenu);
            setJMenuBar(menuBar);
            
            JButton deleteButton = new JButton("Удалить");
            deleteButton.addActionListener((event)->{
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) MyTree.getLastSelectedPathComponent();
                try{
                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode)selectedNode.getParent();
                    parent.remove(parent.getIndex(selectedNode));
                    String s = (String) selectedNode.getUserObject();
                    JarDeque.forEach((item) -> {
                    	if (item.Name.equals(s)){
                    		Jar Jar = item;
                    		JarDeque.remove(Jar);
                    	}
                    });
                    MyTree.updateUI();
                    panel_ofTree.revalidate();
                } catch (ClassCastException e2) {
                    JOptionPane.showMessageDialog(null,"Выбрана не одежда","Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (NullPointerException e){
                    JOptionPane.showMessageDialog(null,"Элемент не выбран","Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            });

            JButton customizeButton = new JButton("Изменить");
            customizeButton.addActionListener((event)->{
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) MyTree.getLastSelectedPathComponent();
                try{
                	String s = (String) selectedNode.getUserObject();
                    JarDeque.forEach((item) -> {
                    	if (item.Name.equals(s)){
                    		Jar Jar = item;
                    		customization(Jar, MyTree,selectedNode);
                    	}
                    });
                    MyTree.updateUI();
                    panel_ofTree.revalidate();
                } catch (ClassCastException e2) {
                    JOptionPane.showMessageDialog(null,"Выбрана не одежда","Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (NullPointerException e){
                    JOptionPane.showMessageDialog(null,"Элемент не выбран","Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            });

            JButton createButton = new JButton("Добавить");
            createButton.addActionListener((event)->{
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) MyTree.getLastSelectedPathComponent();
                /*try{
                	String s = (String) selectedNode.getUserObject();
                    JarDeque.forEach((item) -> {
                    	if (item.Name.equals(s)){
                    		Jar Jar = item;
                    		if (Jar != null) {
                                JOptionPane.showMessageDialog(null, "Нельзя добавить банку в банку!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                            }
                    	}
                    });
                } /*catch (ClassCastException e2) {
                    if(!selectedNode.isRoot()) {
                    	System.out.println("dfd");
                    	//if (((DefaultMutableTreeNode) model.getRoot()).getFirstChild() != selectedNode){
                    		add(MyTree, selectedNode, true);
                    	/*}
                    	else add(MyTree, selectedNode, false);
                        panel_ofTree.revalidate();
                    }
                    else JOptionPane.showMessageDialog(null,"Элементы нельзя добавлять в корневую папку!","Ошибка", JOptionPane.INFORMATION_MESSAGE);
                } catch (NullPointerException e){
                    JOptionPane.showMessageDialog(null,"Элемент не выбран","Ошибка", JOptionPane.ERROR_MESSAGE);
                }*/
                try {
                	/*String s = (String) selectedNode.getUserObject();
                    JarDeque.forEach((item) -> {
                    	if (item.Name.equals(s)){
                    		Jar Jar = item;
                    		if (Jar != null) {
                                JOptionPane.showMessageDialog(null, "Нельзя добавить банку в банку!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                            }
                    	}
                    });*/
                	if (((DefaultMutableTreeNode) model.getRoot()).getFirstChild() == selectedNode){
                		add(MyTree, selectedNode, false);
                	}
                	if (((DefaultMutableTreeNode) model.getRoot()).getLastChild() == selectedNode){
                		add(MyTree, selectedNode, true);
                	}
                	if (selectedNode.isRoot()){
                		JOptionPane.showMessageDialog(null,"Элементы нельзя добавлять в корневую папку!","Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    }
                	String s = (String) selectedNode.getUserObject();
                	JarDeque.forEach((item) -> {
                    	if (item.Name.equals(s)){
                    		Jar Jar = item;
                    		if (Jar != null) {
                                JOptionPane.showMessageDialog(null, "Нельзя добавить банку в банку!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                            }
                    	}
                    });
                        panel_ofTree.revalidate();
                    }
                catch (NullPointerException e){
                    JOptionPane.showMessageDialog(null,"Элемент не выбран","Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            gl_main.setAutoCreateGaps(true);
            gl_main.setAutoCreateContainerGaps(true);
            gl_main.setHorizontalGroup(
                    gl_main.createSequentialGroup()
                            .addGroup(gl_main.createParallelGroup(GroupLayout.Alignment.CENTER)
                                    .addGroup(gl_main.createSequentialGroup()
                                            .addComponent(createButton)
                                            .addComponent(customizeButton)
                                            .addComponent(deleteButton)
                                    )
                                    .addComponent(selectedLabel, GroupLayout.Alignment.CENTER)
                                    .addComponent(timeLabel, GroupLayout.Alignment.CENTER)

                            )
            );
            gl_main.setVerticalGroup(
                    gl_main.createSequentialGroup()
                            .addGroup(gl_main.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(createButton).addComponent(customizeButton).addComponent(deleteButton)
                            )
                            .addGroup(gl_main.createParallelGroup()
                                    .addComponent(selectedLabel)
                            )
                            .addGroup(gl_main.createParallelGroup()
                                .addComponent(timeLabel)
                            )
            );

            GroupLayout fin = new GroupLayout(this.getContentPane());

            fin.setAutoCreateGaps(true);
            fin.setAutoCreateContainerGaps(true);
            fin.setVerticalGroup(
                    fin.createSequentialGroup()
                            .addComponent(panel_ofTree)
                            .addComponent(main_panel)
            );
            fin.setHorizontalGroup(fin.createSequentialGroup()
                    .addGroup(
                        fin.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(panel_ofTree, GroupLayout.Alignment.CENTER)
                            .addComponent(main_panel, GroupLayout.Alignment.CENTER)
                )
            );
            this.getContentPane().setLayout(fin);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);
            this.setEnabled(true);
            this.setVisible(true);
        }
    }
    /*public void listen() throws Exception {
    	//counter++;
        System.out.println("Ready to listen " + InetAddress.getLocalHost() + "--");
        byte[] buf = new byte[256];
        DatagramPacket input = new DatagramPacket(buf, buf.length);
        // blocks until a packet is received
	    udpSocket.receive(input);
	    Address = input.getAddress();
	    Port = input.getPort();
	    System.out.println("Client" + input.getAddress().getHostAddress() + " is connected");
	    //executeIt.execute(new Client(udpSocket));
	    if (counter > 0){
	    	 send(JarDeque);
	    	 counter = 0;
	    }
	   
    }
    
    public void notif (String s) throws IOException{
    	//counter +=1;
    	DatagramPacket output = new DatagramPacket(s.getBytes(), s.getBytes().length, Address, Port);
        udpSocket.send(output); 
    }
    public static void send (ConcurrentLinkedDeque<Jar> x) throws IOException{
    	ByteArrayOutputStream BO = new ByteArrayOutputStream();
		ObjectOutputStream O = new ObjectOutputStream(BO);
		O.writeObject(x);
    	DatagramPacket output = new DatagramPacket(BO.toByteArray(), BO.toByteArray().length, Address, Port);
        udpSocket.send(output);
    }*/
    
    
    
    
    public static void main(String[] args) throws Exception {
    	ServerGUI SG = new ServerGUI();
        //Server server = new Server(6666);
        /*class ShutdownHook extends Thread {
            public void run() {
                    try {
						//z.ex_port();
						server.notif("Сервер отключен");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
                    System.out.println("Завершение работы");
            }
        }
        ShutdownHook shutdownHook = new ShutdownHook();
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        while (true){
        	server.listen();
        }*/
        
        try (ServerSocket s = new ServerSocket(6666)){
        	z.setColl(JarDeque);
            while (!s.isClosed()) {
                Socket client = s.accept();
                MonoThreadClientHandler M = new MonoThreadClientHandler(client);
                executeIt.execute(M);
            }
            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
		