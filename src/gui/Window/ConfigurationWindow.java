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
import javax.swing.JCheckBox;
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
public class ConfigurationWindow extends JPanel implements ActionListener,Constants,KeyListener,Runnable {
	
    protected JTextField targetIPfield,targetIPField; //Variables de campo de texto
    protected JButton buttonSend;								//Botón de Enviar
    protected Font FontA,FontB,FontC;							//Fonts utilizados en la ventana
    protected JLabel targetText,textID,textIP;					//Texto que identifica a targetIpField en GUI
    private JMenu Archivo;										//Menu
    private JMenuBar bar;										//Barra de Menú
	private JMenuItem salir;									//Items de barra de menú
    private static JFrame frame;								//Ventan
    private MessageChannelFacade msgChannel;
    private Facade facade;
	private String local_ID,target_ID,target_IP;				//Variables del ID de Target y ID de usuario actual
	private static String local_IP;
	private int target_PORT;
	private JCheckBox arduino;
    
    /**
     * Constructor de la clase
     * 
     * @param pID: Identificador correspondiente al ID único del Usuario Local Actual
     * @param ptarget_IP: Identificador correspondient al ID único del Usuario Destiono
     * @param ptarget_PORT: Puerto del Usuario Destiono
     */
    public ConfigurationWindow(JFrame frame,MessageChannelFacade pmsgChannel,Facade pfacade,
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
		FontB = new Font("Century Gothic", Font.BOLD, 14);
		FontC = new Font("Century Gothic", Font.BOLD, 25);
		
		
		textID = new JLabel();
		textID.setText("Server IP:");
		textID.setFont(FontA);
		
		targetIPfield = new JTextField();
		targetIPfield.setColumns(9);
		targetIPfield.setPreferredSize(new Dimension(100,30));
		targetIPfield.setText("");
		
		arduino = new JCheckBox("Arduino Client");
		arduino.setMnemonic(KeyEvent.VK_A); 
		arduino.setSelected(false);
		arduino.setFont(FontB);
		arduino.setBackground(new Color(255,150,0));
		
		
		buttonSend = new JButton();
		buttonSend.setPreferredSize(new Dimension(90,30));
		buttonSend.setBackground(new Color(50,100,198));
		buttonSend.setText("Send");
		buttonSend.setActionCommand("send");
		buttonSend.setMnemonic(10);
		buttonSend.addActionListener(this);
		
        add(targetIPfield);
        add(buttonSend);
        add(textID);
        add(arduino);

		
		bgLayout.putConstraint(SpringLayout.NORTH, targetIPfield, 50, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, targetIPfield, 100, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, textID, 35, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, textID, 100, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, arduino, 130, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, arduino, 90, SpringLayout.WEST, this);
		
		bgLayout.putConstraint(SpringLayout.NORTH, buttonSend, 200, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, buttonSend, 100, SpringLayout.WEST, this);
		
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
        ConfigurationWindow p = new ConfigurationWindow(frame,pMsgFacade,pfacade,"","","",6060);
        frame.add(p);
        frame.setVisible(true);
    }
    
    public void instanceChatWindow(String pLocal_IP,String pLocal_ID, 
    								   String pTarget, int pPort, 
    								   MessageChannelFacade msgFacade, Facade facade, boolean pArduino){
    	JFrame frame = new JFrame("CEDMS Messenger");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
        Dimension d = new Dimension();
        frame.setResizable(false);
        frame.setLocation((int)((d.getWidth()+2)+450),15);
        ChatWindow chat_window = new ChatWindow(frame,pLocal_IP,pLocal_ID,pTarget,pPort,facade,pArduino);
        frame.add(chat_window);
        frame.setSize(Constants.WIDTH_chat,Constants.HEIGHT_chat+30);
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
    		if(targetIPfield.getText().equals("")){
    			if(arduino.isSelected() == true){
    				System.out.println("TRUE");
    				instanceChatWindow(ReadProperties.file.getSetting("local_ip"),local_ID, "",6060, msgChannel, facade, true);
    			}
    			else{
    				instanceChatWindow(ReadProperties.file.getSetting("local_ip"),local_ID, "",6060, msgChannel, facade, false);
    			}
    		}
    		else{
    			facade.addConnectionToGraph(targetIPfield.getText());
    			if(arduino.isSelected() == true){
    				instanceChatWindow(ReadProperties.file.getSetting("local_ip"),local_ID, "",6060, msgChannel, facade, true);
    			}
    			else{
    				instanceChatWindow(ReadProperties.file.getSetting("local_ip"),local_ID, "",6060, msgChannel, facade, false);
    			}
    			
    		}
    		
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