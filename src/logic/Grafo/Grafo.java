package logic.Grafo;

import java.util.Hashtable;
import java.util.Observable;

import dataAccess.Constants;
import dataAccess.ReadProperties;
import dataAccess.XPATH.conectionXML;
import dataAccess.XPATH.mensajeXML;
import dataAccess.XPATH.seConectaUPDATExml;
import logic.Dispositivo.TipoDeDispositivo;
import logic.EstructurasDeDatosSimples.List;

public class Grafo<k> extends Observable implements Constants {

    private List<String> listaDeConexiones = new List<>();;
	private Hashtable<String, NodoGrafo<k>> nodos;
    private List<Integer> _puertosUtilizados;
    private int _iD_DispositivoTemporal;
    private List<String> listaIds;
    
    private conectionXML nuevaConectionXML;
    private mensajeXML XMLmessage;
    

    public Grafo() {
        nodos = new Hashtable<String, NodoGrafo<k>>();
        _puertosUtilizados = new List<>();
        //_iD_DispositivoTemporal = 10000000;
        _iD_DispositivoTemporal = 0;
        listaIds = new List<>();
    }

    public NodoGrafo<k> getNodosArbol(String pIdNodoBuscado) {
        return nodos.get(pIdNodoBuscado);
    }
    
    /**
     * Crea un nuevo nodo en el grafo.
     * @param id Identificador del nodo
     * @param pTipoDispositivo El tipo de dispositivo
     * @param pPuerto El puerto de cada dispositivo
     */
    public void nuevoDispositivo(TipoDeDispositivo pTipoDispositivo, int pPuerto) {
    	
    	//TODO VERIFICAR QUE NO SE REPITAN LOS PUERTOS.
    	
    	String temp = Constants.PC_ID + _iD_DispositivoTemporal;
    	_iD_DispositivoTemporal++;
    	
    	NodoGrafo<k> objNodo = new NodoGrafo<k>(temp, pTipoDispositivo,pPuerto);
        nodos.put(temp, objNodo);
        
        listaIds.insertPointer(objNodo.getId());
        _puertosUtilizados.insertPointer(pPuerto);
        

    }
    public void nuevoDispositivo(TipoDeDispositivo pTipoDispositivo, int pPuerto, String pID) {
    	String temp = pID;
    	
    	
    	NodoGrafo<k> objNodo = new NodoGrafo<k>(temp, pTipoDispositivo,pPuerto);
        nodos.put(temp, objNodo);
        
        listaIds.insertPointer(objNodo.getId());
        _puertosUtilizados.insertPointer(pPuerto);
    
    }
    
    
    /**
     * Se envia un mensaje de un Dispositivo a otro
     * @param pFromID: El Id del dispositivo del que proviene el mensaje
     * @param pToID: El Id del dispositivo al que se dirige el mensaje
     * @param pToPuerto: El puerto del dispositivo al que se dirige el mensaje
     * @param pMensaje: El mensaje qeu se desea enviar
     */
	public void sendMessage(String sourceID, String targetID, int targetPort, String pMensaje)
	{
		System.out.println("puertoDE LLEGADA " + targetPort);
    	if (ReadProperties.file.getSetting("debbug").equals("true"))
			System.out.println("STEP 2: metodo para ingresar el mensaje a la lista de mensajes del nodo");
    	
    	try{
    		NodoGrafo<k> sourceNodo = nodos.get(sourceID);
    		NodoGrafo<k> targetNodo = nodos.get(targetID);
        	
        	sourceNodo.insertMessage(pMensaje);

        	String msj = pMensaje;
        	msj += "#"+targetID+"#"+sourceID;
        	sourceNodo.get_conection().ingresarMensajes(msj, sourceNodo.get_core(), 
        												targetNodo.getIP(), sourceNodo.getIP(),targetPort);
        	
        	XMLmessage = new mensajeXML("mensaje",  "source",  "target",
        			sourceID,  targetID,  sourceID, pMensaje, pMensaje, "1");
        	
    	}
    	catch(NullPointerException e){
    		NodoGrafo<k> sourceNodo = nodos.get(Constants.PC_ID + 4);
    		NodoGrafo<k> targetNodo = nodos.get(targetID);
        	
        	sourceNodo.insertMessage(pMensaje);

        	String msj = pMensaje;
        	msj += "#"+targetID+"#"+sourceID;
        	sourceNodo.get_conection().ingresarMensajes(msj, sourceNodo.get_core(), 
        												targetNodo.getIP(), sourceNodo.getIP(),targetPort);
    	}    	
	}
    

    /**
     * Crea una arista entre los dos nodos.
     * @param SourceID El identifcador del nodo origen
     * @param TargetID El identificador del nodo destino
     */
    public void arista(String SourceID, String TargetID,
    				   String SourceIP, String TargetIP,
    				   int pPort, int precio) {
    	
    	NodoGrafo<k> objNodoOrigen = nodos.get(SourceID);
        NodoGrafo<k> objNodoDestino = nodos.get(TargetID);
        
        
        objNodoOrigen.setIP(SourceIP);
        objNodoDestino.setIP(TargetIP);
        
        objNodoOrigen.setConectionWithDispositivo(objNodoDestino, precio);
        
        nuevaConectionXML = new conectionXML("connection", "source", "target", SourceID, TargetID, SourceID);
        
        

        this.setChanged();
        this.notifyObservers();
        
    }
    
    public void addConection(String TargetIP){
    	listaDeConexiones.insertPointer(TargetIP);
    }
    
    public NodoGrafo<k> getNodoGrafo(String pID){
    	return nodos.get(pID);
    }
    
    public List<NodoGrafo<k>> getNodosGrafos(){
    	List<NodoGrafo<k>> listanodos = new List<>();
    	for(int i = 0; i < listaIds.length(); i++){
    		listanodos.insertPointer(nodos.get(listaIds.obtener(i)));
    	}
    	return listanodos;
    }
    
    public List<String> getListaID(){
    	return listaIds;
    }

	public List<String> getListaConexiones() {
		return listaDeConexiones;
	}
}
