package logic.Grafo;

import dataAccess.ReadProperties;
import dataAccess.Facade.Facade;

public class MainTest {

	public static Facade facade;
	
	public static void main(String[] args) {
		
		ReadProperties.getInstance();
		
		facade = new Facade();
		Grafo<NodoGrafo<String>> tempGrafo = facade.getGrafo();
		facade.sendMessageThroughGrafo("fabian0", "fabian1","Esto es un msj de prueba");
		System.out.println("Run Program: ");
		if (ReadProperties.file.getSetting("debbug").equals("true")){
			System.out.println("-----------------------------------");
			tempGrafo.getNodosArbol("fabian1").get_core().
					get_dispositivo().get_listaDeMensajesRecibidos().showData();
		}
	}

}
