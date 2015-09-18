package dataAccess.MessageChannel;

import org.apache.commons.net.telnet.TelnetClient;

import dataAccess.ReadProperties;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.NoRouteToHostException;

import javax.swing.JOptionPane; 
/**
 * Clase que permite la instanciacion de un ClienteTipoTelnet mediante el cual se puede enviar mensajes 
 * a un IP via un puerto específico.
 * 
 * @author Fabian Solano Madriz
 *
 */
public class MessageChannel {
    private static TelnetClient telnet = new TelnetClient();
    private static InputStream in;
    private static PrintStream out;
    @SuppressWarnings("unused")
	private static String prompt = "%";
    /**
     * 
     * @param target_id: Recibe un IP de conexión. Formato: xxx.xxx.xxx.xxx
     * @param message: Mensaje en formato string que se desea enviar
     * @param target_port: Puerto en formato Integer, por el cual se desea enviar el mensaje
     */
    //public MessageChannel(String target_id, String message, String waitUntilString, int target_port) {
    public static void sendMessage(String sourceID, String sourceIP, String targetIP, String message,String waitUntilString, 
    							   int targetPort) {
    	//if (ReadProperties.file.getSetting("msgRouteTracking").equals("true"))
    		//ystem.out.println("LLEGA 2");
    	try {
    		
    		//if (ReadProperties.file.getSetting("msgRouteTracking").equals("true"))
        		//System.out.println("LLEGA 3");
    		telnet.connect(targetIP, targetPort); 			//Conexion al target por el puerto específico
    		in = telnet.getInputStream();						//Obtiene la referencia de datos de entrada
    		out = new PrintStream(telnet.getOutputStream());	//Obtiene la referencia de datos de salida

    		//readUntil(waitUntilString);							//Espera hasta leer el mensaje requerido
    															//por defecto para este proyecto es: "Message "
    		write(sourceID,sourceIP,targetIP,message);			//Escribe el mensaje requerido						
    	} 
    	catch (NoRouteToHostException e) {
    		JOptionPane.showMessageDialog(null, "Cliente no esta en linea. El cliente recibira su " + "\n"
    				+ "mensaje cuando se reconecte","Atencion", JOptionPane.WARNING_MESSAGE);
    	}
    	catch(IOException e){
    		/*JOptionPane.showMessageDialog(null, "Cliente no esta en linea. El cliente recibira su " + "\n"
    				+ "mensaje cuando se reconecte","Atencion", JOptionPane.WARNING_MESSAGE);*/
    	}
    }
    
    /**
     * Metodo que permite la escritura de un mensaje de salida.
     * 
     * @param value: Valor de escritura
     */
    public static void write(String pSourceID,String pSource, String pTarget, 
    						 String message) {
    	//if (ReadProperties.file.getSetting("msgRouteTracking").equals("true"))
    		//System.out.println("LLEGA 4");
    	try {
    		//if (ReadProperties.file.getSetting("msgRouteTracking").equals("true"))
        		//System.out.println("LLEGA 5");
    		out.println(message + "#" + pSourceID);
    		out.flush();
    		
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    /**
     * 
     * @param referencia: String de referencia para la comparación
     * @return retorna el String que se necesitaba encontrar
     */
    public static String readUntil(String referencia) {
    	try {
    		char lastChar = referencia.charAt(referencia.length() - 1);
    		StringBuffer sb = new StringBuffer();
    		char ch = (char) in.read();
    		while (true) {
    			sb.append(ch);
    			if (ch == lastChar) {
    				if (sb.toString().endsWith(referencia)) {
    					return sb.toString();
    				}
    			}
    			ch = (char) in.read();
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    
    /**
     * 
     * @param command: Comando a ejecutar
     * @return Retorna string hasta que espera un prompt
     */
   /* public String sendCommand(String command) {
    	try {
    		write(command);
    		return readUntil(prompt + " ");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }*/
    
    /**
     * Método que desconecta el cliente Telnet
     */
    public void disconnect() {
    	try {
    		telnet.disconnect();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    
    //#########################################################################################################
    //#######################################   TEST METHOD  ##################################################
    //#########################################################################################################
    public static void main(String[] args) {
    	try {
    		System.out.println("Got Connection...");
    		//@SuppressWarnings("unused")
			MessageChannel telnet = new MessageChannel();
			telnet.sendMessage("192.168.1.108", "192.168.1.108", "192.168.1.108", 
								"hola todo bien?", "", 9090);
    		//System.out.println("DONE");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}