package dataAccess.Facade;

import dataAccess.Constants;
import dataAccess.ReadProperties;
import dataAccess.MessageChannel.MessageChannel;
import dataAccess.XPATH.ReadXml;
import logic.Dispositivo.TipoDeDispositivo;
import logic.EstructurasDeDatosSimples.List;
import logic.EstructurasDeDatosSimples.Node;
import logic.Grafo.Grafo;
import logic.Grafo.NodoGrafo;

public class Facade implements Constants {
	
	private static Grafo<NodoGrafo<String>> _grafo;
	private static final String grID = Constants.PC_ID;
	private static final String crIP = Constants.PC_IP;
	private List<String> dispositivosXML;
	private List<String> conexionesXML;
	
	public Facade()
	{
		/*
		ReadXml grafo = new ReadXml("grafo.xml");
		dispositivosXML = grafo.getDispositivo();
		conexionesXML = grafo.getConexiones();
		
		_grafo = new Grafo<>();
		Node<String> dispositivoTemp = dispositivosXML.getHead();
		for(int i=0; i<dispositivosXML.length();i++)
		{
			String[] atributosDispositivo = dispositivoTemp.getData().split("#");
			System.out.println(atributosDispositivo[0]);
			int puerto = Integer.parseInt(atributosDispositivo[0]);
			
			
			if(atributosDispositivo[1].equals("cliente"))
			{
				_grafo.nuevoDispositivo(TipoDeDispositivo.CLIENT,puerto,atributosDispositivo[2]); //0	
			}
			else if(atributosDispositivo[1].equals("hub"))
			{
				_grafo.nuevoDispositivo(TipoDeDispositivo.HUB,puerto,atributosDispositivo[2]); //0	
			}
			else if(atributosDispositivo[1].equals("base"))
			{
				
				_grafo.nuevoDispositivo(TipoDeDispositivo.BASE,puerto,atributosDispositivo[2]); //0	
			}
			dispositivoTemp = dispositivoTemp.getNext();
		}
		
		Node<String> conexionesTemp = conexionesXML.getHead();
		
		for (int i=0; i< conexionesXML.length();i++)
		{
			String[] atributosConexion = conexionesTemp.getData().split("#");
			int precioConexion = Integer.parseInt(atributosConexion[2]);
			
			_grafo.arista(atributosConexion[0], atributosConexion[1],crIP,crIP,6060,precioConexion);
			
			conexionesTemp = conexionesTemp.getNext();
		}
		*/
		
		_grafo = new Grafo<>();
		_grafo.nuevoDispositivo(TipoDeDispositivo.CLIENT,6060); //0
		_grafo.nuevoDispositivo(TipoDeDispositivo.CLIENT,7070); //1
		_grafo.nuevoDispositivo(TipoDeDispositivo.HUB,78765);	//2
		_grafo.nuevoDispositivo(TipoDeDispositivo.CLIENT,6060); //3
		_grafo.nuevoDispositivo(TipoDeDispositivo.HUB,9090); 	//4
		_grafo.arista(grID+0, grID+2,crIP,crIP,6060,1);
		_grafo.arista(grID+1, grID+2,crIP,crIP,7070,1);
		_grafo.arista(grID+2, grID+1,crIP,crIP,7070,1);
		_grafo.arista(grID+2, grID+4,crIP,crIP,7070,1);
		_grafo.arista(grID+4, grID+0,crIP,crIP,6060,1);
		_grafo.arista(grID+2, grID+0,crIP,crIP,7070,1);
	}
	
	public void setConnection(String sourceID,String targetID, 
							  String sourceIP,String targetIP, 
							  int pPort, int precio){
		
		
		_grafo.arista(sourceID, targetID,sourceIP,targetIP,pPort,precio);
	}

	
	public void sendMessageThroughGrafo(String sourceID, String targetID, String pMensaje)
	{
		if (ReadProperties.file.getSetting("debbug").equals("true"))
			System.out.println("STEP 1: Method sendMessageThroughGrafo");
		NodoGrafo<NodoGrafo<String>> temp = _grafo.getNodoGrafo(targetID);
		try{
			System.out.println("SOURCE: " + sourceID);
			System.out.println("TARGET: " + targetID);

			_grafo.sendMessage(sourceID, targetID, temp.get_puertoDispositivo(), pMensaje);
			
		}
		catch(NullPointerException e){
			/*JOptionPane.showMessageDialog(null, "Usuario No Conectado",
					"Atencion", JOptionPane.WARNING_MESSAGE);*/
			List<String> lista = _grafo.getListaConexiones();
			Node<String> tmp = lista.getHead();
			for(int i = 0; i < lista.length(); i++){
				MessageChannel.sendMessage(
						sourceID,Constants.PC_IP,tmp.getData(),
						pMensaje+"#"+targetID,"Message ", 9090);
				tmp = tmp.getNext();
			}
			
		}
		
		
	}
	
	public void addConnectionToGraph(String pTargetIP){
		_grafo.addConection(pTargetIP);
	}
	
	public Grafo<NodoGrafo<String>> getGrafo(){
		return _grafo;
	}
}
