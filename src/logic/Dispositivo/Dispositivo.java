package logic.Dispositivo;

import gui.Window.ChatWindow;

import java.util.concurrent.locks.Lock;

import dataAccess.ReadProperties;
import dataAccess.Facade.Facade;
import dataAccess.Facade.MessageChannelFacade;
import dataAccess.MessageChannel.MessageChannel;
import dataAccess.XPATH.mensajeXML;
import dataAccess.XPATH.seConectaUPDATExml;
import logic.EstructurasDeDatosSimples.Cola;
import logic.EstructurasDeDatosSimples.List;
import logic.EstructurasDeDatosSimples.Node;
import logic.Grafo.NodoGrafo;

@SuppressWarnings({"unused"})
public class Dispositivo<k> extends Thread {
	
	private List<String> _listaDeMensajesRecibidos;
	private Cola<NodoGrafo<String>> closeStations;
	private TipoDeDispositivo _mytipo;
	private List<NodoGrafo<k>> _conexiones;
	private String _iD;
	private seConectaUPDATExml grafoActualizado;
	private Lock lock;
	
	
	public Dispositivo(TipoDeDispositivo pTipoCliente, List<NodoGrafo<k>> listaConexiones, String pID )
	{
		_listaDeMensajesRecibidos = new Cola<>();
		_mytipo = pTipoCliente;
		closeStations = new Cola<NodoGrafo<String>>();
		_conexiones = listaConexiones;
		_iD= pID;
		
	}
	
	
	public void operacionDispositivo(String pMessage, String targetIP, String sourceIP, int targetPort)
	{
		if (ReadProperties.file.getSetting("debbug").equals("true"))
			System.out.println("STEP 9: Verifica el tipo de Dispotivo para ejecutar su funcion");
		
		if(_mytipo == TipoDeDispositivo.CLIENT)
		{
			operacionCliente(pMessage, targetIP, sourceIP,targetPort);
		}
		
		if(_mytipo == TipoDeDispositivo.HUB)
		{
			operacionHub(pMessage, targetIP, sourceIP,targetPort);
		}
		
		if (_mytipo == TipoDeDispositivo.BASE)
		{
			operacionBase(pMessage, targetIP, sourceIP,targetPort);
		}
	}
	
	private void operacionBase(String pMessage, String targetIP, String sourceIP, int targetPort){
		if (ReadProperties.file.getSetting("debbug").equals("true"))
			System.out.println("STEP 10: Operacion para el tipo de Dispositivo BASE");
		
		String[] MsgForMe = pMessage.split("#");
		
		grafoActualizado = new seConectaUPDATExml(MsgForMe[2], MsgForMe[1]);
		
	}
	
	private void operacionHub(String pMsgForMe, String targetIP, String sourceIP, int targetPort) 
	{
		if (ReadProperties.file.getSetting("debbug").equals("true"))
			System.out.println("STEP 10: Operacion para el tipo de Dispositivo HUB");
		difundirMessage(pMsgForMe, targetIP, sourceIP,targetPort);
	}


	public void operacionCliente(String pMessage, String targetIP, String sourceIP, int targetPort)
	{
		if (ReadProperties.file.getSetting("debbug").equals("true"))
			System.out.println("STEP 10: Operacion para el tipo de Dispositivo CLIENTE");
		
		String[] MsgForMe = pMessage.split("#");
		
		
		try{ 
			System.out.println("----_TARGET_-----" + MsgForMe[1]);
			System.out.println("----Current-----" + _iD);
			if (MsgForMe[1].equals(_iD)){
				Node<String> temporalMensajes = _listaDeMensajesRecibidos.getHead();
				for(int indice =0;indice< _listaDeMensajesRecibidos.length();indice++)
				{
					if(temporalMensajes.getData().equals(MsgForMe[0]))
					{
						return;
					}
					temporalMensajes = temporalMensajes.getNext();
				}
				
				_listaDeMensajesRecibidos.insertPointer(MsgForMe[0]);
				_listaDeMensajesRecibidos.showData();
				ChatWindow targetWindow = MessageChannelFacade.getChatWindow(_iD);
				targetWindow.receiveMessage(MsgForMe[2], MsgForMe[0]);
				
				
			}
			
			else{
				if(MsgForMe[2].equals(_iD)){
					difundirMessage(pMessage, targetIP, sourceIP,targetPort);
				}
					
			}
		} 
		
		catch (NullPointerException e) {
			System.out.println("Null Pointer Exception");
		}
		
	}



	public void difundirMessage(String pMsg, String targetIP, String sourceIP, int targetPort)
	{
		Node<NodoGrafo<k>>temporalConexiones = _conexiones.getHead();
		
			// mensaje#TargetID#SourceID
		String[] inf = pMsg.split("#");
		
		for(int i =0;i< _conexiones.length();i++)
		{
			temporalConexiones.getData().get_conection().ingresarMensajes(
					pMsg,temporalConexiones.getData().get_core(),targetIP,sourceIP,targetPort);
			MessageChannel.sendMessage(inf[1],sourceIP,targetIP,pMsg,"Message ",targetPort);
			temporalConexiones = temporalConexiones.getNext();
		}
	}
	
	/**
	 * @return the _listaDeMensajesRecibidos
	 */
	public List<String> get_listaDeMensajesRecibidos() {
		return _listaDeMensajesRecibidos;
	}
	
	public void sendMessage(String pMensaje,String TargetID, String SourceID, 
							String TargetIP, String SourceIP, int targetPort)
	{
		if (ReadProperties.file.getSetting("debbug").equals("true"))
			System.out.println("STEP 8: Encripta el mensaje de la forma; mensaje#idTarget");
		String Msg = pMensaje;
		Msg+= "#"+TargetID+"#"+SourceID;
		operacionDispositivo(Msg,TargetIP,SourceIP,targetPort);
	}
	
	public NodoGrafo<k> verificaConection(String pToID)
	{
		Node<NodoGrafo<k>> temp =this._conexiones.getHead();
		for(int i =0; i< _conexiones.length(); i++)
		{
			if(temp.getData().getId() == pToID)
			{
				return temp.getData();
			}
		
			temp = temp.getNext();
		}
		return null;
		
	}


	/*public void sendMessageOUT(String pMensaje,String pToID) 
	{
		String Msg = pMensaje;
		Msg+= "#"+pToID;
		operacionDispositivo(Msg);
	}	*/
}
