package gui.Window;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import dataAccess.Constants;
import dataAccess.ReadProperties;
import dataAccess.Facade.Facade;
import dataAccess.Facade.MessageChannelFacade;
import dataAccess.MessageChannel.SSocket;

/**
 * Clase que permite instanciar una nueva ventana de Chat.
 * 
 * @author Fabian Solano Madriz
 * @version 1.0
 *
 */
@SuppressWarnings({ "unused", "serial" })
public class ChatWindow extends JPanel implements ActionListener,Constants,KeyListener,Runnable {
	
    protected static JTextField writeField; //Variables de campo de texto
	protected JTextField targetIDField;
	protected JTextField targetIPField;
    protected JTextArea textArea;								//Area de recepcion y envio de mensajes
    protected static JButton buttonSend;								//Botón de Enviar
    protected Font FontA,FontB,FontC;							//Fonts utilizados en la ventana
    protected JLabel targetText,textID,textIP;		//Texto que identifica a targetIpField en GUI
	protected static JLabel textLetter;
    private JMenu Archivo;										//Menu
    private JMenuBar bar;										//Barra de Menú
	private JMenuItem salir,conexion,nuevoNodo;					//Items de barra de menú
	public static SSocket socket;								//Socket correspondiente al usuario actual
    private static JFrame frame;								//Ventana
    private final static String newline = "\n";					//Variables correspondiente a un "Enter"
	private String local_ID,target_ID,target_IP;				//Variables del ID de Target y ID de usuario actual
	private static String local_IP;
    private int target_PORT;									//Variable de puerto de Target
    public static MessageChannelFacade msgFacade;
    public static Facade facade;
    private boolean ARDUINO;
    private static String[] abc = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    private static int indiceABC = 0;
    
    /**
     * Constructor de la clase
     * 
     * @param pID: Identificador correspondiente al ID único del Usuario Local Actual
     * @param ptarget_IP: Identificador correspondient al ID único del Usuario Destiono
     * @param ptarget_PORT: Puerto del Usuario Destiono
     */
    public ChatWindow(JFrame frame,String pLocal_IP,String pLocal_ID,String ptarget_IP, 
    				  int ptarget_PORT, Facade pFacade, boolean pArduino) {
        super(new GridBagLayout());
        local_ID = pLocal_ID;
        local_IP = pLocal_IP;
        target_ID = ptarget_IP;
        target_PORT = ptarget_PORT;
        msgFacade = new MessageChannelFacade();
        facade = pFacade;
        ARDUINO = pArduino;
        
        this.setBackground(new Color(255,150,0));
        SpringLayout bgLayout = new SpringLayout();
        this.setLayout(bgLayout);
        
        //#################################### BARRA DE OPCIONES  #############################################
		Archivo = new JMenu("Archivo");				//Instancia Opcion archivo

		conexion = new JMenuItem("Establecer nueva conexión");
		conexion.addActionListener(this);
		nuevoNodo = new JMenuItem("Nuevo Nodo");
		nuevoNodo.addActionListener(this);
		Archivo.add(conexion);
		Archivo.add(nuevoNodo);
		salir = new JMenuItem("Salir");
		salir.addActionListener(this);
		Archivo.add(new JSeparator());
		Archivo.add(salir);
		
		
		bar = new JMenuBar();
		bar.add(Archivo);
		frame.setJMenuBar(bar);
		//######################################################################################################
        
        FontA = new Font("Century Gothic", Font.BOLD, 12);
		FontB = new Font("Century Gothic", Font.BOLD, 70);
		FontC = new Font("Century Gothic", Font.BOLD, 25);
		
		targetText = new JLabel();
		targetText.setText("Send message to: ");
		targetText.setFont(FontA);
		
		textID = new JLabel();
		textID.setText("ID:");
		textID.setFont(FontA);
		
		textIP = new JLabel();
		textIP.setText("IP:");
		textIP.setFont(FontA);
		textIP.setVisible(false);
		
        textLetter = new JLabel();
        textLetter.setText(abc[indiceABC]);
        textLetter.setFont(FontB);
		
		writeField = new JTextField();
		writeField.setColumns(35);
		writeField.setPreferredSize(new Dimension(200,30));
		writeField.addKeyListener(this);
		
		targetIDField = new JTextField();
		targetIDField.setColumns(9);
		targetIDField.setPreferredSize(new Dimension(100,30));
		targetIDField.setText("");
		
		targetIPField = new JTextField();
		targetIPField.setColumns(9);
		targetIPField.setPreferredSize(new Dimension(100,30));
		targetIPField.setText(target_ID);
		targetIPField.setVisible(false);
		
		buttonSend = new JButton();
		buttonSend.setPreferredSize(new Dimension(90,30));
		buttonSend.setBackground(new Color(50,100,198));
		buttonSend.setText("Send");
		buttonSend.setActionCommand("send");
		buttonSend.setMnemonic(10);
		buttonSend.addActionListener(this);
		
        
        JPanel inPanel = new JPanel();
        inPanel.setLayout(new GridBagLayout());
        inPanel.setPreferredSize(new Dimension(390,210));
        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        //Layout for the scrollPane and the textArea
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        inPanel.add(scrollPane, c);
        
        
        add(writeField);
        add(targetIDField);
        add(targetIPField);
        add(buttonSend);
        add(targetText);
        add(textID);
        add(textIP);
        if(ARDUINO == true){
        	add(textLetter);
        }
        add(inPanel);
        
		
		bgLayout.putConstraint(SpringLayout.NORTH, writeField, 238, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, writeField, 10, SpringLayout.WEST, this);
		
		bgLayout.putConstraint(SpringLayout.NORTH, targetIDField, 50, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, targetIDField, 450, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, textID, 55, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, textID, 428, SpringLayout.WEST, this);

		bgLayout.putConstraint(SpringLayout.NORTH, targetIPField, 82, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, targetIPField, 450, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, textIP, 87, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, textIP, 428, SpringLayout.WEST, this);
		
		if(ARDUINO == true){
			bgLayout.putConstraint(SpringLayout.NORTH, textLetter, 120, SpringLayout.NORTH, this);
			bgLayout.putConstraint(SpringLayout.WEST, textLetter, 480, SpringLayout.WEST, this);
        }
		
		bgLayout.putConstraint(SpringLayout.NORTH, buttonSend, 238, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, buttonSend, 420, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, inPanel, 10, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, inPanel, 10, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, scrollPane, 550, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, targetText, 32, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, targetText, 442, SpringLayout.WEST, this);
		
		writeField.requestFocus();
        frame.add(this);
        msgFacade.addChatWindow(this);
        frame.setTitle("CEDMS Messenger " + " - " + " IP: " + local_IP + " ID: "  + local_ID);
        
        
    }
    
    /**
     * Método local, que permite enviar el mensaje al usuario destino marcado en el JTextField targetIpField
     * 
     */
    private void sendMessage(){
    	System.out.println("SNED");
    	target_ID = targetIDField.getText();
    	target_IP = targetIPField.getText();
    	String message =writeField.getText();
    	if (ReadProperties.file.getSetting("msgRouteTracking").equals("true"))
    		System.out.println("LLEGA 1");
    	
		if(!message.equals("") && !target_ID.equals("")){
			textArea.append("Me: " + newline);
	        textArea.append("  " + message + newline);
	        writeField.setText("");
	        if (ReadProperties.file.getSetting("debbug").equals("true")){
	        	System.out.println("SOURCE: " + local_IP);
	        	System.out.println("TARGET: " + target_ID);
	        }
	        //MessageChannel.sendMessage(local_ID,local_IP,target_IP, message + "#" + target_ID, "Message ", target_PORT);
	        facade.sendMessageThroughGrafo(local_ID, target_ID,message);
	        writeField.requestFocus();
    	}
    	
    	
    }
    
    /**
     * Metodo publico que permite la recepción de mensajes en el cuadro de texto
     * 
     * @param source: Identificador único del remitente del mensaje
     * @param incomingMessage: Mensaje en formato String
     */
    public void receiveMessage(String source, String incomingMessage){
    	if (ReadProperties.file.getSetting("msgRouteTracking").equals("true"))
    		System.out.println("LLEGA 8");
    	textArea.append(source +": " + newline);
        textArea.append("  " + incomingMessage + newline);
    }
    
    /**
     * Retorna el ID unico del Usuario Actual
     * 
     * @return String asosciado al ID del Usuario Actual
     */
    public String getLocalIP(){
    	return local_IP;
    }
    public String getLocalID(){
    	return local_ID;
    }
    
    /**
     * 
     * Método inicializar de la ventana correspondiente al chat
     * 
     * @param m
     * @param pPort
     * @return
     */
    public static Runnable createAndShowGUI(MessageChannelFacade pFacade,String local_ID, 
    										String target_ID,int pPort, boolean pArduino) {
    	msgFacade = pFacade;
        //Create and set up the window.
        frame = new JFrame("CEDMS Messenger");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Dimension d = new Dimension(); //Objeto para obtener tamaño de la pantalla
		//ImageIcon im = new ImageIcon("src");
		//ventanaM.setIconImage(im.getImage());
        frame.setResizable(true);
        frame.setLocation((int)((d.getWidth()+2)+450),15);
        ChatWindow p = new ChatWindow(frame,local_ID,"Usuario",target_ID,pPort,facade,pArduino);
        frame.add(p);
        frame.setSize(Constants.WIDTH_chat,Constants.HEIGHT_chat);
        frame.setVisible(true);
        
        msgFacade.addChatWindow(p);
        
        /*Thread t2 = new Thread(socket = new SSocket(pFacade,local_ID,6060));
        t2.start();*/
		return socket;
    }
    
    public void instanceConectionWindow(String pLocal_IP,String pLocal_ID, 
			   String pTarget, int pPort, 
			   MessageChannelFacade msgFacade, Facade facade){
		JFrame frame = new JFrame("");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Dimension d = new Dimension();
		frame.setResizable(false);
		frame.setLocation((int)((d.getWidth()+2)+450),15);
		ConectionWindow window = new ConectionWindow(frame,msgFacade,facade,
													 pLocal_IP,pLocal_ID,pTarget, pPort);
		frame.add(window);
		frame.setSize(Constants.WIDTH_chat-300,Constants.HEIGHT_chat);
		frame.setVisible(true);
	}

    //#########################################################################################################
    //#########################################################################################################
    //#########################################################################################################
    @Override
	public void run() {
		this.repaint();
		
	}
    
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == 10){
			sendMessage();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	public void actionPerformed(ActionEvent evt) {
    	if("send".equals(evt.getActionCommand())){
    		
    	}
    	if (evt.getSource()==buttonSend ) {
            sendMessage();
    	}
    	if (evt.getSource()==conexion){
    		instanceConectionWindow(local_IP,local_ID,target_ID,target_PORT,msgFacade,facade);
    	}
    	if (evt.getSource()==nuevoNodo){
    		RegisterWindow.createAndShowGUI(0);
    	}
    	else if(evt.getSource().equals(salir)){
    		System.exit(0);
    	}
    }
	
	public static void abcUP(){
		if(indiceABC == abc.length){
			indiceABC=0;
		}
		else{
			indiceABC++;
		}
		textLetter.setText(abc[indiceABC]);
		
	}
	public static void abcDOWN(){
		if(indiceABC <= 0){
			indiceABC=abc.length;
		}
		else{
			indiceABC--;
		}
		textLetter.setText(abc[indiceABC]);
	}
	
	public static void setEnter(){
		String temp = writeField.getText();
		temp+=textLetter.getText();
		writeField.setText(temp);
	}
	
	public static void setSend(){
		buttonSend.doClick();
	}
	
	//##############################################################################
	//## TEST MAIN #################################################################
	//##############################################################################
	
	public static void main(String[] args) {
		ReadProperties.getInstance();
		msgFacade = new MessageChannelFacade();
		Thread t4 = new Thread(createAndShowGUI(msgFacade,ReadProperties.file.getSetting("local_ip"), "172.26.108.188",6060,false));
        t4.start();
        Thread t2 = new Thread(socket = new SSocket(msgFacade,local_IP,6060,facade));
        t2.start();
        
		
    }

	
}