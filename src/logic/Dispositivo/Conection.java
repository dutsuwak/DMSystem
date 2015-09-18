package logic.Dispositivo;

import dataAccess.ReadProperties;
import logic.EstructurasDeDatosSimples.Cola;

public class Conection{
	
	public Cola<String> listaMensajes;
	public Cola<String> listaMensajesBandejaDeEntrada;
	
	public Conection()
	{listaMensajes = new Cola<>();}
	
	public void ingresarMensajes(String pMensaje, Core pCoreSource, 
								 String targetIP, String SourceIP, int targetPort)
	{
		//System.out.println("PCORE ID: " +  pMensaje);
		if (ReadProperties.file.getSetting("debbug").equals("true"))
			System.out.println("STEP 4: Ingresa mensaje a la lista de mensajes de la conexion");
		
		listaMensajes.enQueue(pMensaje);
		pasarMensajes(pCoreSource, targetIP, SourceIP,targetPort);
	}
	
	public void pasarMensajes(Core pCore, String targetIP, String sourceIP, int targetPort)
	{
		if (ReadProperties.file.getSetting("debbug").equals("true"))
			System.out.println("STEP 5: Decodifica el mensaje original en Mensaje , ID");
		String msj = listaMensajes.deQueue();
		String[] mensajeAEnviar = msj.split("#");
		
		
		pCore.AddMenssages(mensajeAEnviar[0]);
		pCore.sendMenssageIN(mensajeAEnviar[1], mensajeAEnviar[2],targetIP, sourceIP,targetPort);
		
	}
	
	public void ingresarMensajeBandejaDeEntrada(String pMensaje){
		listaMensajesBandejaDeEntrada.enQueue(pMensaje);
	}
	
	

}
