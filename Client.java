package lab5;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.lang.reflect.Field;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class Client{
    static Timer timer;
    static boolean block = false;
    private static Locale[] supportedLang = {
        new Locale.Builder().setLanguage("ru").setRegion("RU").build(),
        new Locale.Builder().setLanguage("pt").setRegion("PT").build(),
        new Locale.Builder().setLanguage("lt").setRegion("LT").build(),
        new Locale.Builder().setLanguage("es").setRegion("CO").build()
    };
    private static Locale currentLang = supportedLang[0];
    private static ResourceBundle resources = ResourceBundle.getBundle("lab5.Messages", currentLang);
    private static ConcurrentLinkedDeque<Jar> JarDeque = new ConcurrentLinkedDeque();
	static ConcurrentLinkedDeque<Jar> JDCopy = new ConcurrentLinkedDeque();
    private static class ClientGUI extends JFrame{
    	private JMenu mainMenu;
    	private JCheckBox checkGreen;
    	private JCheckBox checkRed;
    	private JCheckBox checkYellow;
    	private JCheckBox checkBlack;
    	private JCheckBox checkBlue;
    	private JCheckBox checkWhite;
    	private JRadioButton both;
    	private JRadioButton empty;
    	private JRadioButton full;
    	private Box sliderbox;
    	private HintTextField name;
    	private JButton stop;
    	private JToggleButton animation;
    	private Box checkbox;
    	private ConcurrentLinkedDeque <JarButton> buttons = new ConcurrentLinkedDeque();
    	public ClientGUI() throws IOException, InterruptedException, ClassNotFoundException{
    		super ("Коллекция");
    		this.setPreferredSize(new Dimension(520,520));
            this.setMinimumSize(new Dimension(520,520));
    		setDefaultCloseOperation(EXIT_ON_CLOSE);
    		CustomPanel panel = new CustomPanel();
    		animation = new JToggleButton ("Старт");
    		stop = new JButton("Стоп");
    		
    		ArrayDeque <JCheckBox> JCB = new ArrayDeque();
    		checkbox = Box.createVerticalBox();
    		checkbox.setBounds(1800, 5, 100, 190);
    		checkbox.setVisible(true);
    		checkbox.setBorder(new TitledBorder("Цвет"));
    		checkGreen = new JCheckBox("Зелёный");
    		checkRed = new JCheckBox("Красный");
    		checkYellow = new JCheckBox("Жёлтый");
    		checkBlack = new JCheckBox("Чёрный");
    		checkBlue = new JCheckBox("Синий");
    		checkWhite = new JCheckBox("Белый");
    		JCB.add(checkGreen);
    		JCB.add(checkRed);
    		JCB.add(checkYellow);
    		JCB.add(checkBlack);
    		JCB.add(checkBlue);
    		JCB.add(checkWhite);
    		checkbox.add(checkGreen);
    		checkbox.add(checkRed);
    		checkbox.add(checkYellow);
    		checkbox.add(checkBlack);
    		checkbox.add(checkBlue);
    		checkbox.add(checkWhite);
    		
    		ButtonGroup radio = new ButtonGroup();
    		both = new JRadioButton("Любой");
    		both.setBounds(1330, 55, 90, 20);
    		both.setSelected(true);
    		radio.add(both);
    		empty = new JRadioButton("Пустой");
    		empty.setBounds(1330, 75, 90, 20);
    		radio.add(empty);
    		full = new JRadioButton("Не пустой");
    		full.setBounds(1330, 95, 90, 20);
    		radio.add(full);
    		JRadioButton [] JRB = {both, empty, full};
    		
    		sliderbox = Box.createVerticalBox();
    		sliderbox.setBounds(1480, 5, 300, 115);
    		JSlider minslider = new JSlider(0, 1000, 100);
    		minslider.setMajorTickSpacing(200);
    		minslider.setMinorTickSpacing(50);
    		minslider.setPaintTicks(true);
    		minslider.setPaintLabels(true);
    		JSlider maxslider = new JSlider(0, 1000, 900);
    		maxslider.setMajorTickSpacing(200);
    		maxslider.setMinorTickSpacing(50);
    		maxslider.setPaintTicks(true);
    		maxslider.setPaintLabels(true);
    		sliderbox.add(minslider);
    		sliderbox.add(maxslider);
    		sliderbox.setBorder(new TitledBorder("Размер"));
    		
            name = new HintTextField ("Введите имя...");
            name.setBounds(1330, 15, 130, 25);
            
    		stop.setBounds(1200, 15, 100, 30);
    		stop.setBackground(Color.RED);
    		stop.setVisible(false);
    		animation.setBackground(Color.GREEN);
    		animation.setBounds(1200, 15, 100, 30);
    		animation.addActionListener((event) -> {
    			block = true;
    			HashMap <JarButton, Double> hashMap = new HashMap <JarButton, Double>();
    			buttons.forEach((item) ->{
    				item.Selected = false;
    				item.check(JCB, minslider, maxslider, name, JRB);
    				if (item.Selected == true){
    					hashMap.put(item, item.size);
    				}
    			});
	    		 timer = new Timer(10, new ActionListener(){
	    			int counter = 0;
	                public void actionPerformed(ActionEvent e) {
	                	if (hashMap.size() == 0){
	                		timer.stop();
	                		animation.setSelected(false);
	                	}
	                	else{
		                	animation.setVisible(false);
		                	stop.setVisible(true);
		                	if ((counter < 3000) && (animation.isSelected() == true)){
		                		hashMap.keySet().forEach((item) -> {
			                		item.size -= (item.size)/273;
			                		repaint();
			                        item.reBounds();
			                        item.reBorder();
			                	});
		                		counter+=timer.getDelay();
		                	} else if ((counter < 7000) && (animation.isSelected() == true)){
		                		hashMap.keySet().forEach((item) -> {
			                		item.size += (item.size)/365;
			                		repaint();
			                        item.reBounds();
			                        item.reBorder();
			                	});
		                		counter+=timer.getDelay();
		                	} else {
		                		block = false;
		                		timer.stop();
		                		stop.setVisible(false);
		                		animation.setSelected(false);
		                		animation.setVisible(true);
		                		Set<Entry<JarButton, Double>> set = hashMap.entrySet();
		                		for (HashMap.Entry<JarButton, Double> me :set) {
		                		    me.getKey().size = me.getValue();
		                		    repaint();
		                		    me.getKey().reBounds();
		                		    me.getKey().reBorder();
		                		}
		                		hashMap.clear();
		                	}
	                	}
	                }
	    		});
	    		timer.start();
    		});
    		stop.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent e){
    				animation.setSelected(false);
    				block = false;
    			}
    		});
    		
    		JMenuBar lang = new JMenuBar();
    		mainMenu = new JMenu(resources.getString("Language"));
            JMenuItem ru = new JMenuItem("Русский");
            JMenuItem pt = new JMenuItem("Português");
            JMenuItem lt = new JMenuItem("Lietuviškai");
            JMenuItem es = new JMenuItem("Español");
            mainMenu.add(ru);
            mainMenu.add(pt);
            mainMenu.add(lt);
            mainMenu.add(es);
            ru.addActionListener((event)->{
                currentLang = supportedLang[0];
                resources = ResourceBundle.getBundle("lab5.Messages", currentLang);
                changeLanguage();
            });
            pt.addActionListener((event)->{
                currentLang = supportedLang[1];
                resources = ResourceBundle.getBundle("lab5.Messages", currentLang);
                changeLanguage();
            });
            lt.addActionListener((event)->{
                currentLang = supportedLang[2];
                resources = ResourceBundle.getBundle("lab5.Messages", currentLang);
                changeLanguage();
            });
            es.addActionListener((event)->{
                currentLang = supportedLang[3];
                resources = ResourceBundle.getBundle("lab5.Messages", currentLang);
                changeLanguage();
            });
    		lang.add(mainMenu);
            this.setJMenuBar(lang);
    		panel.add(checkbox);
    		panel.add(both);
    		panel.add(empty);
    		panel.add(full);
    		panel.add(sliderbox);
    		panel.add(name);
    		panel.add(stop);
    		panel.add(animation);
    		panel.setLayout(null);
			panel.repaint();
    		setContentPane(panel);
    		this.setVisible(true);
    		this.pack();
    		update(panel);
    	}
    	
    	private void changeLanguage(){
            this.setTitle(resources.getString("Frame"));
            mainMenu.setText(resources.getString("Language"));
            checkGreen.setText(resources.getString("Color_green"));
            checkRed.setText(resources.getString("Color_red"));
            checkYellow.setText(resources.getString("Color_yellow"));
            checkBlack.setText(resources.getString("Color_black"));
            checkBlue.setText(resources.getString("Color_blue"));
            checkWhite.setText(resources.getString("Color_white"));
            animation.setText(resources.getString("Buttons_start"));
            stop.setText(resources.getString("Buttons_stop"));
            both.setText(resources.getString("Check_both"));
            empty.setText(resources.getString("Check_empty"));
            full.setText(resources.getString("Check_not_empty"));
            sliderbox.setBorder(new TitledBorder(resources.getString("Size")));
            name.setText(resources.getString("Name"));
            checkbox.setBorder(new TitledBorder(resources.getString("Color")));
        }
    	
    	private void initButtons(){
            buttons.clear();
            JarDeque.forEach((item)->
                buttons.add(new JarButton(item))
            );
            
        }
    	
    	private void update(CustomPanel panel) throws InterruptedException, ClassNotFoundException{
    		//exec.execute(new Client());
    		
    		try(Socket socket = new Socket("127.0.0.1", 6666);  
                    DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream()); )
            {                
                while(!socket.isOutputShutdown()){                   
	                Thread.sleep(1000);
	                oos.writeUTF("Kek");
	                oos.flush();
	                Thread.sleep(1000); 
	                JarDeque = (ConcurrentLinkedDeque<Jar>) ois.readObject();
	                if (!block){
	                	 buttons.forEach((item)->{
	 	    				panel.remove(item);
	 	    				repaint();
	 	    			});
	 	    			initButtons();
	 	        		buttons.forEach((item)->{
	 	        			panel.add(item);
	 	        			repaint();
	 	        		});
	                }
                }
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
            	e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                update(panel);
            }
    	}
    	
    	public class HintTextField extends JTextField implements FocusListener { 

    	    private final String hint; 
    	    private boolean showingHint; 

    	    public HintTextField(final String hint) { 
    	    super(hint); 
    	    this.hint = hint; 
    	    this.showingHint = true; 
    	    super.addFocusListener(this); 
    	    } 

    	    @Override 
    	    public void focusGained(FocusEvent e) { 
    	    if(this.getText().isEmpty()) { 
    	     super.setText(""); 
    	     showingHint = false; 
    	    } 
    	    } 
    	    @Override 
    	    public void focusLost(FocusEvent e) { 
    	    if(this.getText().isEmpty()) { 
    	     super.setText(hint); 
    	     showingHint = true; 
    	    } 
    	    } 

    	    @Override 
    	    public String getText() { 
    	    return showingHint ? "" : super.getText(); 
    	    } 
    	} 
    	
    	public class CustomPanel extends JPanel {
    	     protected void paintComponent(Graphics g) {
    	    	 	
    	     }
    	 
    	}
    	
    	class JarButton extends JButton {
            private double size;
            private Jar Jar;
            private boolean Selected;
            private String name;
			private Boolean isEmpty;

            class ClothesBorder implements Border{
                    private int side;
                    private ClothesBorder(int side){
                            this.side = side;
                    }
                    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                            g.fillRect(x , y, width, height);
                            g.drawRect(x , y, width, height);
                    }
                    public Insets getBorderInsets(Component c) {
                        return new Insets(this.side + 1, this.side +1, this.side+1, this.side+1);
                    }
                    public boolean isBorderOpaque() {
                        return true;
                    }
            }

            @SuppressWarnings("deprecation")
			private JarButton(Jar Jar){
                super("");
                this.Jar = Jar;
                this.size = Jar.Size;
                this.name = Jar.Name;
                this.Selected = false;
                this.isEmpty = Jar.IsEmpty;
                setBackground(Color.GRAY);
                Color color;
                try {
                	Field field = Color.class.getField(Jar.Color);
                	color = (Color)field.get(null);
                } catch (Exception e) {
			    color = null; // Not defined
                }
                setForeground(color);
                setBounds((int)(Jar.Point.x - Math.round(this.size/ 2)), (int)(Jar.Point.y - Math.round(this.size/2)), (int)this.size, (int)this.size);
                setBorder(new ClothesBorder((int)this.size));
                //DateTimeFormatter f = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL ).withLocale(currentLang);
                SimpleDateFormat s = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", currentLang);
                setToolTipText(s.format(Jar.Date));
                setOpaque(false);
                setEnabled(false);
            }
            private void reBounds() {
                setBounds((int)(Jar.Point.x - Math.round(this.size/ 2)), (int)(Jar.Point.y - Math.round(this.size/2)), (int)this.size, (int)this.size);
            }

            private void reBorder() {
                setBorder(new ClothesBorder((int)size));
            }
            private void check(ArrayDeque <JCheckBox> JCB, JSlider minslider, JSlider maxslider, HintTextField name, JRadioButton [] JRB ){
            	JCB.forEach((item) -> {
            		Color color;
                    try {
                    	Field field = Color.class.getField(item.getText());
                    	color = (Color)field.get(null);
                    } catch (Exception e) {
    			    color = null; // Not defined
                    }
            		if((item.isSelected() == true) && (color == this.getForeground())){
            			this.Selected = true;
            		}
            	});
            	if ((this.size > maxslider.getValue()) || (this.size < minslider.getValue())){
            		this.Selected = false;
            	}
            	if (!(name.getText().isEmpty()) && !(this.name.equals(name.getText()))){
            		this.Selected = false;
            	}
            	if (JRB [1].isSelected()){
            		if (!(this.isEmpty)){
            			this.Selected = false;
            		}
            	}
            	
            	if (JRB [2].isSelected()){
            		if (this.isEmpty){
            			this.Selected = false;
            		}
            	}
            }
            @Override
            public void paintComponent(Graphics g) {
            	Color color;
                try {
                	Field field = Color.class.getField(Jar.Color);
                	color = (Color)field.get(null);
                } catch (Exception e) {
			    color = null; // Not defined
                }
                g.setColor(color);
                g.fillRect((int)(Jar.Point.x - Math.round(this.size/ 2)), (int)(Jar.Point.y - Math.round(this.size/2)), (int)this.size, (int)this.size);
            }
        }
    }
    public static void main(String[] args) throws NumberFormatException, IOException, InterruptedException, ClassNotFoundException {
        System.out.println("-- Running UDP Client at " + InetAddress.getLocalHost() + " --");
        ClientGUI CG = new ClientGUI ();
    }
    /*private static void start() throws IOException, InterruptedException, ClassNotFoundException {
    	try(Socket socket = new Socket("127.0.0.1", 6666);  
                DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream()); )
        {

            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = oos & reading channel = ois initialized.");                     
            while(!socket.isOutputShutdown()){                   
            System.out.println("Client start writing in channel...");
            Thread.sleep(1000);
            oos.writeUTF(" ");
            oos.flush();
            System.out.println("Clien sent message to server.");
            Thread.sleep(1000);           
            System.out.println("Client sent message & start waiting for data from server...");     
            JarDeque = (ConcurrentLinkedDeque<Jar>) ois.readObject();
            System.out.println(JarDeque.getFirst().Name);
            Thread.sleep(2000);
            System.out.println("Something");
            /*if(ois.read() > -1){                        
            	System.out.println("reading...");
            	System.out.println("Closing connections & channels on clentSide - DONE.");
            }
            System.out.println("Where");
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    		/*String in = "Дай мне коллекцию";
            Thread.sleep(1000);
            		serverAddress = InetAddress.getByName("127.0.0.1");
    	            DatagramPacket output = new DatagramPacket(in.getBytes(), in.getBytes().length, serverAddress, port);
    	            udpSocket = new DatagramSocket();
    	            udpSocket.send(output); 
    	            //получение
    	            byte [] buf = new byte [3000];
    	            DatagramPacket input = new DatagramPacket(buf, buf.length);
    	            udpSocket.setSoTimeout(3000);
    	            try{
    	            	udpSocket.receive(input);
    	            	ByteArrayInputStream BO = new ByteArrayInputStream(input.getData());
    	        		ObjectInputStream O = new ObjectInputStream(BO);
    					JarDeque = (ConcurrentLinkedDeque<Jar>) O.readObject();
    	            }
    	            catch (SocketTimeoutException | ClassNotFoundException e){
    	            	start();*/
    	
    	            	
      	//}
  	}
        