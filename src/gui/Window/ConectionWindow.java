package gui.Window;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import dataAccess.Constants;
import dataAccess.ReadProperties;
import dataAccess.Facade.Facade;
import dataAccess.Facade.MessageChannelFacade;


/**
 * Clase que permite instanciar una nueva ventana de Chat.
 * 
 * @author Fabian Solano Madriz
 * @version 1.0
 *
 */
@SuppressWarnings({ "unused","serial" })
public class ConectionWindow extends JPanel implements ActionListener,Constants,KeyListener,Runnable {
	
    protected JTextField targetIDField,targetIPField,targetPortField,targetPriceField;
    protected JButton buttonSend;								
    protected Font FontA,FontB,FontC;							
    protected JLabel textID,textIP,textPort;					
    private JMenu Archivo;										
    private JMenuBar bar;										
	private JMenuItem salir;									
    private static JFrame frame;								
    private MessageChannelFacade msgChannel;
    private Facade facade;
	private String local_ID,target_ID,target_IP;				
	private static String local_IP;
	private int target_PORT;
    
    /**
     * Constructor de la clase
     * 
     * @param pID: Identificador correspondiente al ID único del Usuario Local Actual
     * @param ptarget_IP: Identificador correspondient al ID único del Usuario Destiono
     * @param ptarget_PORT: Puerto del Usuario Destiono
     */
    public ConectionWindow(JFrame frame,MessageChannelFacade pmsgChannel,Facade pfacade,
    							String pLocal_IP,String pLocal_ID,String ptarget_IP, int ptarget_PORT) {
        super(new GridBagLayout());
        
        local_ID = pLocal_ID;
        local_IP = pLocal_IP;
        target_ID = ptarget_IP;
        target_PORT = ptarget_PORT;
        msgChannel= pmsgChannel;
        facade = pfacade;
        
        
        this.setBackground(new Color(255,150,0));
        SpringLayout bgLayout = new SpringLayout();
        this.setLayout(bgLayout);
        
        //#################################### BARRA DE OPCIONES  #############################################
		Archivo = new JMenu("Archivo");				//Instancia Opcion archivo
		salir = new JMenuItem("Salir");
		salir.addActionListener(this);
		Archivo.add(new JSeparator());
		Archivo.add(salir);
		
		bar = new JMenuBar();
		bar.add(Archivo);
		frame.setJMenuBar(bar);
		//######################################################################################################
        
        FontA = new Font("Century Gothic", Font.BOLD, 12);
		FontB = new Font("Century Gothic", Font.BOLD, 18);
		FontC = new Font("Century Gothic", Font.BOLD, 25);
		
		
		textID = new JLabel();
		textID.setText("Target ID:");
		textID.setFont(FontA);
		
		textIP = new JLabel();
		textIP.setText("Target IP:");
		textIP.setFont(FontA);
		
		textPort = new JLabel();
		textPort.setText("Target Port:");
		textPort.setFont(FontA);
		
		targetIDField = new JTextField();
		targetIDField.setColumns(9);
		targetIDField.setPreferredSize(new Dimension(100,25));
		
		targetIPField = new JTextField();
		targetIPField.setColumns(9);
		targetIPField.setPreferredSize(new Dimension(100,25));
		
		targetPortField = new JTextField();
		targetPortField.setColumns(9);
		targetPortField.setPreferredSize(new Dimension(100,25));
		
		
		buttonSend = new JButton();
		buttonSend.setPreferredSize(new Dimension(90,30));
		buttonSend.setBackground(new Color(50,100,198));
		buttonSend.setText("Send");
		buttonSend.setActionCommand("send");
		buttonSend.setMnemonic(10);
		buttonSend.addActionListener(this);
		
        add(targetIDField);
        add(targetIPField);
        add(targetPortField);
        add(buttonSend);
        add(textID);
        add(textIP);
        add(textPort);

        bgLayout.putConstraint(SpringLayout.NORTH, textID, 5, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, textID, 100, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, targetIDField, 25, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, targetIDField, 100, SpringLayout.WEST, this);	
		
		bgLayout.putConstraint(SpringLayout.NORTH, textIP, 55, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, textIP, 100, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, targetIPField, 75, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, targetIPField, 100, SpringLayout.WEST, this);
		
		bgLayout.putConstraint(SpringLayout.NORTH, textPort, 105, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, textPort, 100, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, targetPortField, 125, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, targetPortField, 100, SpringLayout.WEST, this);
		
		
		bgLayout.putConstraint(SpringLayout.NORTH, buttonSend, 200, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, buttonSend, 108, SpringLayout.WEST, this);
		
        frame.add(this);
        
        
    }
    
    
    /**
     * 
     * Método inicializar de la ventana correspondiente al chat
     * 
     * @param m
     * @param pPort
     */
    public static void createAndShowGUI(MessageChannelFacade pMsgFacade,String local_ID, 
    										String target_ID,int pPort, Facade pfacade) {
        //Create and set up the window.
        frame = new JFrame("Running Configuration");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Dimension d = new Dimension(); //Objeto para obtener tamaño de la pantalla
		//ImageIcon im = new ImageIcon("src");
		//ventanaM.setIconImage(im.getImage());
        frame.setResizable(false);
        frame.setSize(Constants.WIDTH_chat-300,Constants.HEIGHT_chat);
        frame.setLocation((int)((d.getWidth()+2)+550),205);
        ConectionWindow p = new ConectionWindow(frame,pMsgFacade,pfacade,"","","",6060);
        frame.add(p);
        frame.setVisible(true);
    }
    
    public void instanceChatWindow(String pLocal_IP,String pLocal_ID, 
    								   String pTarget, int pPort, 
    								   MessageChannelFacade msgFacade, Facade facade){
    	JFrame frame = new JFrame("CEDMS Messenger");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Dimension d = new Dimension();
        frame.setResizable(false);
        frame.setLocation((int)((d.getWidth()+2)+450),15);
        ChatWindow chat_window = new ChatWindow(frame,pLocal_IP,pLocal_ID,pTarget,pPort,facade,false);
        frame.add(chat_window);
        frame.setSize(Constants.WIDTH_chat,Constants.HEIGHT_chat+75);
        frame.setVisible(true);
        msgFacade.addChatWindow(chat_window);
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
            
    		String targetID 	= targetIDField.getText();
    		String targetIP 	= targetIPField.getText();
    		int targetPort		= Integer.parseInt(targetPortField.getText());
    		
    		int precio = (int) Math.floor(Math.random()* (0-15) + (15));
    		
    		System.out.println("LocalID: " + local_ID);
    		System.out.println("LocalIP: " + local_IP);
    		System.out.println("TargetID: " + targetID);
    		System.out.println("TargetIP: " + targetIP);
    		System.out.println("TargetPort: " + targetPort);
    		System.out.println("Precio: " + precio);
    		
    		facade.setConnection(local_ID, targetID, local_IP, targetIP, targetPort, precio);
    	}
    	else if(evt.getSource().equals(salir)){
    		System.exit(0);
    	}
    }
	
	//##############################################################################
	//## TEST MAIN #################################################################
	//##############################################################################
	
	public static void main(String[] args) {
		ReadProperties.getInstance();
		MessageChannelFacade f = new MessageChannelFacade();
		Facade f2 = new Facade();
		createAndShowGUI(f,ReadProperties.file.getSetting("local_ip"), "172.26.108.188",6060,f2);
        
		
    }

	
}