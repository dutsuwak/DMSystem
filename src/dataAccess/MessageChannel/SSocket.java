package dataAccess.MessageChannel;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;

import dataAccess.ReadProperties;
import dataAccess.Facade.Facade;
import dataAccess.Facade.MessageChannelFacade;



public class SSocket extends Thread{
	
	int puerto;
	private ServerSocket server;
	private Socket socket;
	public DataOutputStream salida;
	private String msjRecibido;
	private BufferedReader entrada;
	private MessageChannelFacade msgFacade;
	private Facade facade;
	private String local_ID;
	public Lock lock;
	int counter = 0;
	private boolean running = false;
	
	/**
	 * Método para inicializar el socket.
	 * @param pPuerto: Número entero correspondiente al número de puerto que se desea abrir.
	 */
	public SSocket(MessageChannelFacade pMsjFacade,String pId, int pPuerto,Facade pFacade){
		puerto = pPuerto;
		msgFacade=pMsjFacade;
		facade = pFacade;
		local_ID = pId;
		running = true;
		initServer();
	}
	
	public void initServer(){
		
		try
		{
			server = new ServerSocket(puerto);
			socket = new Socket();
			
			if(ReadProperties.file.getSetting("debbug").equals("true")) 
				System.out.println("$$ServerMsg$$ Esperando Conexión... $$ServerMsg$$");
			run();
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void run(){
		while(running){
			try{
				socket = server.accept();
				entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				salida = new DataOutputStream(socket.getOutputStream());
				salida.writeUTF("Message ");
				
				
				msjRecibido = entrada.readLine();
				System.out.println("msj: " + msjRecibido);
				
				if(ReadProperties.file.getSetting("msgRouteTracking").equals("true")) 
					System.out.println(msjRecibido);
				//TODO VERIFICAR COMO OBTENER EL ID DE DESTINO ->, ACTUAL CON FINES DE PRUEBA
				
				String[] msg_and_ID = msjRecibido.split("#");
				
				if(puerto == 9090){
					System.out.println("msg_and_ID[0]"+ msg_and_ID[0]);
					System.out.println("msg_and_ID[1]" + msg_and_ID[1]);
					System.out.println("msg_and_ID[2]" + msg_and_ID[2]);
					
					facade.sendMessageThroughGrafo(msg_and_ID[2], msg_and_ID[1], msg_and_ID[0]);
					salida.close();
				}
				else{
					//MANDA EL MSJ DE MANERA DE MANERA DIRECTA
					msgFacade.sendMessage(msg_and_ID[2],local_ID,msg_and_ID[1],msg_and_ID[0]);
					//facade.sendMessageToNode();
					salida.close();

				}
				
				
			}
			catch(Exception e){
				
			}
		}
	}
	
	/*public void waitConection(){
		try{
			if(counter == 0){
				socket = server.accept();
				if (ReadProperties.file.getSetting("debbug").equals("true"))
					System.out.println("$$ServerMsg$$ ¡Un cliente se ha conectado! $$ServerMsg$$");
				entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				salida = new DataOutputStream(socket.getOutputStream());
				salida.writeUTF("Envía un mensaje: ");
				counter++;
				
				printMessage();
			}
			else{
				salida.writeUTF("Conexion exitosa----> envía un msj!");
				salida.write(13);
				socket = server.accept();
				entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				salida = new DataOutputStream(socket.getOutputStream());
				
				counter++;
				
				printMessage();
			}
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	
	public void printMessage() throws IOException{
		msjRecibido = entrada.readLine();
		System.out.println(msjRecibido);
		waitConection();
	}*/
	
	public static void main(String[] args) {
    	try {
    		ReadProperties.getInstance();
    		@SuppressWarnings("unused")
			SSocket p = new SSocket(new MessageChannelFacade(),"192.168.1.105", 
									9090, new Facade());

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
	

}
