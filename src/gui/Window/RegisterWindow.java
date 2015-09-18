package gui.Window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import dataAccess.Constants;
import dataAccess.ReadProperties;
import dataAccess.ReadUserInformation;
import dataAccess.SaveUserInformation;
import dataAccess.SerialAccess;
import dataAccess.Facade.Facade;
import dataAccess.Facade.MessageChannelFacade;
import dataAccess.MessageChannel.SSocket;

@SuppressWarnings({"unused","serial"})
public class RegisterWindow extends JPanel implements ActionListener,Constants,Runnable {
	
    protected JTextField nameField;
    protected JPasswordField passwordField,passwordField2;
    protected JTextArea textArea;
    protected JButton buttonRegister;
    protected Font FontA,FontB,FontC;
    protected JLabel nameText,passText,passText2,currentMode;
    private JMenu Archivo;
    private JMenuBar bar;
	private JMenuItem salir,signUpItem,signInItem;
	private static SSocket socket;
	public static MessageChannelFacade msgFacade;
    static int mode;
    private static JFrame frame;
    private final static String newline = "\n";
    public static Facade facade;
    private SerialAccess input;

    public RegisterWindow() {
        super(new GridBagLayout());
        this.setBackground(new Color(255,150,0));
        SpringLayout bgLayout = new SpringLayout();
        this.setLayout(bgLayout);
        msgFacade = new MessageChannelFacade();
        //input = new SerialAccess();
    	//input.run("COM4", facade);
      //#################################### BARRA DE OPCIONES  #############################################
		//************************ FILE **************************
		Archivo = new JMenu("Archivo");
		
		signUpItem = new JMenuItem("Sign Up");
		signUpItem.addActionListener(this);
		
		signInItem = new JMenuItem("Sign In");
		signInItem.addActionListener(this);
		
		salir = new JMenuItem("Salir");
		salir.addActionListener(this);
		
		
		Archivo.add(signUpItem);
		Archivo.add(signInItem);
		Archivo.add(new JSeparator());
		Archivo.add(salir);
	
		
		bar = new JMenuBar();
		bar.add(Archivo);
		frame.setJMenuBar(bar);
		//***********************************************************************************
        
        FontA = new Font("Century Gothic", Font.BOLD, 12);
		FontB = new Font("Century Gothic", Font.BOLD, 18);
		FontC = new Font("Century Gothic", Font.BOLD, 25);
		
		nameText = new JLabel("Enter Username: ");
		nameText.setText("Enter Username: ");
		nameText.setFont(FontA);
		passText = new JLabel("Enter password");
		passText.setFont(FontA);
		passText2= new JLabel("Confirm password");
		passText2.setFont(FontA);
    
		currentMode= new JLabel();
		currentMode.setText("Sign in");
		currentMode.setFont(FontC);
		
        buttonRegister = new JButton();
        buttonRegister.setPreferredSize(new Dimension(115,30));
		buttonRegister.setBackground(new Color(135,100,198));
		buttonRegister.setText("Continuar");
		buttonRegister.setActionCommand("go");
		buttonRegister.addActionListener(this);

        nameField = new JTextField(25);
        nameField.addActionListener(this);
        nameField.setFont(FontB);
        nameField.setColumns(8);
        nameField.setToolTipText("Enter Username");
        
        passwordField = new JPasswordField();
		passwordField.setColumns(8);
		passwordField.setFont(FontB);
		passwordField.setToolTipText("Enter password");
		
		passwordField2 = new JPasswordField();
		passwordField2.setColumns(8);
		passwordField2.setFont(FontB);
		passwordField2.setToolTipText("Confirm your password");
        
		mode=1;
		passwordField2.setVisible(false);
		passText2.setVisible(false);
        
        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        textArea.setBounds(50, 150, 150, 60);
        
        add(nameField);
        add(passwordField);
        add(passwordField2);
        add(buttonRegister);
        add(nameText);
        add(passText);
        add(passText2);
        add(currentMode);
		
		bgLayout.putConstraint(SpringLayout.NORTH, nameField, 230, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, nameField, 107, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, passwordField, 300, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, passwordField, 107, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, passwordField2, 350, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, passwordField2, 107, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, buttonRegister, 395, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, buttonRegister, 125, SpringLayout.WEST, this);
		
		bgLayout.putConstraint(SpringLayout.NORTH, nameText, 210, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, nameText, 107, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, passText, 280, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, passText, 107, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, passText2, 330, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, passText2, 107, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, currentMode, 165, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, currentMode, 122, SpringLayout.WEST, this);
        
        frame.add(this);
    }

    public void actionPerformed(ActionEvent evt) {
        String pUser = nameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String password2 = String.valueOf(passwordField2.getPassword());
    	if("go".equals(evt.getActionCommand())){
    		if(mode == 0){
    			if(verifyPasswords(password,password2) == true){
        			SaveUserInformation.SaveInfo(pUser,password);
        			JOptionPane.showMessageDialog(null,"Usuario Creado", "",  JOptionPane.INFORMATION_MESSAGE);
        			currentMode.setText("Sign in");
        			mode = 1;
        			passwordField2.setVisible(false);
        			passText2.setVisible(false);
        			
        			frame.dispose();
        			//instanceChatWindow(ReadProperties.file.getSetting("local_ip"),pUser, "",6060);
        			//TODO CAMBIAR 6060 por puertos aleatorios
    				instanceConfigurationWindow(ReadProperties.file.getSetting("local_ip"), pUser, "", 6060, msgFacade, facade);
        		}
        		else{
        			JOptionPane.showMessageDialog(null,"Passwords are different", "Atencion",  JOptionPane.WARNING_MESSAGE);
        		}
    		}
    		
    		else if(mode == 1){
    			if(password.equals(ReadUserInformation.file.getSetting(pUser))){
    				//instanceChatWindow(ReadProperties.file.getSetting("local_ip"),pUser, "172.26.108.188",6060);
    				instanceConfigurationWindow(ReadProperties.file.getSetting("local_ip"), pUser, "", 6060, msgFacade, facade);
    				frame.dispose();
    			}
    			else{
    				JOptionPane.showMessageDialog(null,
							"User or password are not valid", "Atencion",
							JOptionPane.WARNING_MESSAGE);
    			}
    			
    			
    	        
    			
    			
    		}
    	}
    	else if(evt.getSource().equals(signInItem)){
    		currentMode.setText("Sign in");
			mode = 1;
			passwordField2.setVisible(false);
			passText2.setVisible(false);
    	}
    	else if(evt.getSource().equals(signUpItem)){
    		currentMode.setText("Sign up");
			mode = 0;
			passwordField2.setVisible(true);
			passText2.setVisible(true);
    	}
    	else if(evt.getSource().equals(salir)){
    		System.exit(0);
    	}
    }
    
    
    public void instanceConfigurationWindow(String pLocal_IP,String pLocal_ID, 
			   String pTarget, int pPort, 
			   MessageChannelFacade msgFacade, Facade facade){
		JFrame frame = new JFrame("CEDMS Messenger");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Dimension d = new Dimension();
		frame.setResizable(false);
		frame.setLocation((int)((d.getWidth()+2)+450),15);
		ConfigurationWindow chat_window = new ConfigurationWindow(frame,msgFacade,facade,
															      pLocal_IP,pLocal_ID,pTarget, pPort);
		frame.add(chat_window);
		frame.setSize(Constants.WIDTH_chat-300,Constants.HEIGHT_chat);
		frame.setVisible(true);
	}
    
    
    public Runnable instanceChatWindow(String pLocal_IP,String pLocal_ID, String pTarget, int pPort){
    	JFrame frame = new JFrame("CEDMS Messenger");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Dimension d = new Dimension();
        frame.setResizable(false);
        frame.setLocation((int)((d.getWidth()+2)+450),15);
        ChatWindow chat_window = new ChatWindow(frame,pLocal_IP,pLocal_ID,pTarget,pPort,facade,false);
        frame.add(chat_window);
        frame.setSize(Constants.WIDTH_chat,Constants.HEIGHT_chat);
        frame.setVisible(true);
        msgFacade.addChatWindow(chat_window);
		return socket;
    }
    
    private boolean verifyPasswords(String p1, String p2){
    	if(p1.equals(p2)){
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    
    static Runnable createAndShowGUI(int m) {
    	mode = m;
    	ReadProperties.getInstance();
        //Create and set up the window.
        frame = new JFrame("CEDMS Messenger");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension d = new Dimension(); //Objeto para obtener tamaño de la pantalla
        frame.setResizable(false);
        frame.setLocation((int)((d.getWidth()+2)+450),15);
        frame.add(new RegisterWindow());
        frame.setSize(Constants.WIDTH,Constants.HEIGHT);
        frame.setVisible(true);
		return socket;
        
        
    }

    public static void main(String[] args) {
        ReadProperties.getInstance();
        ReadUserInformation.getInstance();
		msgFacade = new MessageChannelFacade();
		facade = new Facade();
		
		
		createAndShowGUI(0);
        //Thread t2 = new Thread(socket = new SSocket(msgFacade,ReadProperties.file.getSetting("local_ip"),7070,facade));
        //t2.start();
        Thread t3 = new Thread(socket = new SSocket(msgFacade,ReadProperties.file.getSetting("local_ip"),9090,facade));
        t3.start();


        
        
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
	}
}