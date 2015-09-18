package logic.Grafo;



import dataAccess.ReadProperties;
import logic.Dispositivo.Conection;
import logic.Dispositivo.Core;
import logic.Dispositivo.TipoDeDispositivo;
import logic.EstructurasDeDatosSimples.Cola;
import logic.EstructurasDeDatosSimples.List;
import logic.EstructurasDeDatosSimples.Node;

/**
 * Cada nodo tiene un identificador y un valor dentro del nodo
 */
public class NodoGrafo<k> {
    /**
     * El identificador es un string que se utiliza para poder encontrar al 
     * nodo de manera rapida en la lista de nodos.
     */
    private String _iD;
    private String _IP;
    public Cola<String> _messages;  //TODO
    private List<NodoGrafo<k>> listaConexiones;
    private Core _core;
    private Conection _conection;
    private int _puertoDispositivo;
    private List<ConexionLogica> listasDePrecios;
    

  
    

	/**
     * El constructos de un nodo de un grafo. Se necesita obligatoriamente un
     * nombre de nodo (identificador) que NO se encuentre en el grafo ya.
     * @param identificador
     * @param pDato
	 * @param pPuerto 
     */
    public NodoGrafo(String pIdentificador, TipoDeDispositivo pTipo, int pPuerto)
    {
    	this._iD = pIdentificador;
    	this._messages = new Cola<String>();
    	_puertoDispositivo = pPuerto;
    	
    	this.set_puertoDispositivo(pPuerto);
    	
        this.listaConexiones = new List<NodoGrafo<k>>();
        this.listasDePrecios = new List<ConexionLogica>();
        
        this._core = new Core(this.listaConexiones, pTipo, this._iD);
        this._conection = new Conection();
        //_conection.pasarMensajes(_core);
        _core.start();
    }
    
    public void sendMessage(String pFromID, String pToID, int pToPuerto, String pMensaje)
	{
    	//TODO LLAMAR A FACADE
   
	}


    

   
    

    public List<NodoGrafo<k>> getConectionsList() {return listaConexiones;}

    /**
  	 * @return _messages: la cola de los mensajes de cada dispositivo
  	 */
  	public Cola<String> getQueueMessages() {
  		return _messages;
  	}

  	/**
  	 * @param _messages: se inserta el mensaje a una cola de mensajes
  	 */
  	public void insertMessage(String pMessage) {
  		if (ReadProperties.file.getSetting("debbug").equals("true"))
			System.out.println("STEP 3: Inserta el mensaje en la lista mensajes del Nodo");
  		_messages.enQueue(pMessage);
  	}

  	
  	
  	
  	
  	/**
  	 * @return listaConexiones: la lista con todas las conexiones del nodo
  	 */
  	public List<NodoGrafo<k>> getListaConexiones() {return listaConexiones;}

  	/**
  	 * @param listaConexiones the listaConexiones to set
  	 */
  	public void setListaConexiones(List<NodoGrafo<k>> listaConexiones) {
  		this.listaConexiones = listaConexiones;}

  	/**
  	 * @return _core: la variable core perteniciente al nodo
  	 */
  	public Core get_core() {
  		return _core;
  	}

  	/**
  	 * @return _conection: la variable conection del dispositivo
  	 */
  	public Conection get_conection() {
  		return _conection;
  	}

  	/**
  	 * @param pConection: establece la conection.
  	 */
  	public void set_conection(Conection pConection) {this._conection = pConection;}
  	
  	/**
  	 * @param id: el id que se le desea asignar al nodo
  	 */
  	public void setId(String pId) {
  		this._iD = pId;
  	}

    /**
     * Inserta una conexion en el nodo, pero primero ve si la conexion no se 
     * encuentra ya en la lista de conexiones.
     * @param objNodoDestino El objeto nodo de destino
     */
    public void setConectionWithDispositivo(NodoGrafo<k> objNodoDestino,int pPrecioDeConexion) {
    	
        Node<NodoGrafo<k>> temp = listaConexiones.getHead();
        boolean flag=true;
        
        for (int i=0; i<listaConexiones.length();i++) {
            if (temp.getData() == objNodoDestino)
            {
            	flag=false;
            	return;
            }
                
            temp = temp.getNext();
        }
        if (flag)
        {
        	ConexionLogica _precioConexion = new ConexionLogica(_iD, objNodoDestino.getId(), pPrecioDeConexion);
        	listasDePrecios.insertPointer(_precioConexion);
        }
        
        listaConexiones.insertPointer(objNodoDestino);
    }
	
    //GETTERS
    public String getId() { return _iD;}

    public String getMessage() { return _messages.deQueue();}
    
    public String getIP(){ return _IP ;}
	
    public List<ConexionLogica> getListasDePrecios(){
    	return listasDePrecios;
    }
	/**
	 * @return the _puertoDispositivo
	 */
	public int get_puertoDispositivo() 
	{return _puertoDispositivo;}
	
	//SETTERS
	
	/**
	 * @param _puertoDispositivo the _puertoDispositivo to set
	 */
	public void set_puertoDispositivo(int _puertoDispositivo){
		this._puertoDispositivo = _puertoDispositivo;
	}
	
	public void setConectionsList(List<NodoGrafo<k>> pNewConectionList) {
		this.listaConexiones = pNewConectionList;
	}
	
	public void setIP(String ip){
		_IP = ip;
	}
	
	
}


