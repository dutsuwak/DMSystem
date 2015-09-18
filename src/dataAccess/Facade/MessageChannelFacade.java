package dataAccess.Facade;

import javax.swing.JOptionPane;
import dataAccess.ReadProperties;
import gui.Window.ChatWindow;
import logic.EstructurasDeDatosSimples.List;
import logic.EstructurasDeDatosSimples.Node;
import java.util.Hashtable;


public class MessageChannelFacade {

	List<ChatWindow> ChatWindowList = new List<>();
	private static Hashtable<String,ChatWindow> TableChatWindow = new Hashtable<String, ChatWindow>();
	
	
	
	public void sendMessage(String pSource, String pTarget_IP, String pTarget_ID, String message) {
		if (ReadProperties.file.getSetting("msgRouteTracking").equals("true")){
    		System.out.println("LLEGA 6");
		}
		
		try{
			if (ReadProperties.file.getSetting("msgRouteTracking").equals("true"))
	    		System.out.println("LLEGA 7");
			
			
			//TODO
			System.out.println("pSource: " + pSource);
			System.out.println("pTargetIP" + pTarget_IP);
			System.out.println("pTargetID: " + pTarget_ID);
			System.out.println("Message: " + message);
			
			
			ChatWindow targetWindow = getChatWindow(pTarget_ID);
			targetWindow.receiveMessage(pSource, message);
			/*ChatWindow targetWindow = searchTargetChatWindow(pTarget_IP);
			targetWindow.receiveMessage(pSource, message);*/
			
		}
		catch(NullPointerException excep){
			JOptionPane.showMessageDialog(null, "Objetivo no encontrado en la red CEDMS",
					"Atencion", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	public void addChatWindow(ChatWindow newChatWindow){
		//ChatWindowList.insertPointer(newChatWindow);
		TableChatWindow.put(newChatWindow.getLocalID(), newChatWindow);
	}
	
	public static ChatWindow getChatWindow(String pTargetID){
		try{
			
			return TableChatWindow.get(pTargetID);
		}
		catch(NullPointerException e){
			JOptionPane.showMessageDialog(null, "No se encuentra conectado. Su mensaje " + "\n"
					+ "sera enviado mas tarde", "Atencion", JOptionPane.WARNING_MESSAGE);
		}
		return null;
		
		
	}
	
	public ChatWindow searchTargetChatWindow(String pTarget){
		System.out.println("TARGET " + pTarget);
		Node<ChatWindow> temp = ChatWindowList.getHead();
		for(int i = 0; i < ChatWindowList.length(); i++){
			System.out.println("ACTUAL : " + temp.getData().getLocalID());
			if(temp.getData().getLocalID().equals(pTarget))
				return temp.getData();
			else{
				temp = temp.getNext();
			}
		}
		return null;
	}

	public void sendMessageToNode() {
		
		
	}
	
	
	
	
}
