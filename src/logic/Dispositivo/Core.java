package logic.Dispositivo;

import java.util.concurrent.locks.Lock;

import dataAccess.ReadProperties;
import logic.EstructurasDeDatosSimples.Cola;
import logic.EstructurasDeDatosSimples.List;
import logic.Grafo.NodoGrafo;

@SuppressWarnings({"unused"})
public class Core extends Thread{
	
	private Cola<String> queueIN;
	private Cola<String> queueOUT;
	private Cola<String> listaMensajes;
	@SuppressWarnings("rawtypes")
	private Dispositivo _dispositivo;
	private Lock lock;
	private String _iD;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <E> Core(List<NodoGrafo<E>> listaConexiones, TipoDeDispositivo pTipoDispositivo,String pID)
	{
		queueIN = new Cola<>();
		queueOUT = new Cola<>();
		listaMensajes = new Cola<>();
		_iD= pID;
		
		_dispositivo = new Dispositivo(pTipoDispositivo, listaConexiones,_iD );
	}
	
	
	
	
	
	/**
	 * @return the _dispositivo
	 */
	@SuppressWarnings("rawtypes")
	public Dispositivo get_dispositivo() 
	{return _dispositivo;}

	
	/**
	 * @param _dispositivo the _dispositivo to set
	 */
	@SuppressWarnings("rawtypes")
	public void set_dispositivo(Dispositivo _dispositivo) 
	{this._dispositivo = _dispositivo;}

	/**
	 * 
	 * @param pMensajeEntrante
	 */
	public void AddMenssages(String pMensajeEntrante)
	{
		if (ReadProperties.file.getSetting("debbug").equals("true"))
			System.out.println("STEP 6: Ingresa mensaje a lista de mensajes del Core");
		queueIN.enQueue(pMensajeEntrante);
		
	}
	
	/**
	 * @param targetPort 
	 * 
	 */
	public void sendMenssageIN(String pToID, String pFromID, 
							   String targetIP, String sourceIP, int targetPort)
	{
		if (ReadProperties.file.getSetting("debbug").equals("true"))
			System.out.println("STEP 7: Envia el mensaje al dispositivo para ser procesado");
		
		String mensajeParaEnviar = queueIN.deQueue();
		_dispositivo.sendMessage(mensajeParaEnviar, pToID,pFromID,targetIP,sourceIP,targetPort);
	}
	
	/*public void sentMessageOUT(String pToID)
	{
		String mensajeRecibido = queueOUT.deQueue();
		_dispositivo.sendMessageOUT(mensajeRecibido, pToID);
	}*/
	
	public String getCoreID(){
		return _iD;
	}
}
 