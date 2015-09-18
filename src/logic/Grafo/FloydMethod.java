package logic.Grafo;

import dataAccess.ReadProperties;
import logic.Dispositivo.TipoDeDispositivo;
import logic.EstructurasDeDatosSimples.List;
import logic.EstructurasDeDatosSimples.Array;
import logic.EstructurasDeDatosSimples.Cola;
import logic.EstructurasDeDatosSimples.SimpleTable1;

public class FloydMethod<k> {
	
	private List<List<Array>> listaMetodo;
	private List<List<Array>> listaMetodo2;
	private int numVertices;
	private List<NodoGrafo<k>> listanodos; 
	private static final  String infinito = "-1";
	
	public FloydMethod( List<NodoGrafo<k>> listanodos){
		this.numVertices = listanodos.length();
		this.listanodos = listanodos;
		this.listaMetodo = new List<>();
		this.listaMetodo2 = new List<>();
	}
	
	private void insertTabla(){
		
		for(int i = 0; i < this.numVertices; i++){
			listaMetodo.insertPointer(new List<Array>());
			for(int j = 0; j < this.numVertices; j++){
				Array array = new Array(3);
				array.insertData(listanodos.obtener(j).getId());
				array.insertData(infinito);
				array.insertData(listanodos.obtener(i).getId());
				
				listaMetodo.obtener(i).insertPointer(array);
			}
		}
	}
	
	private void insertPrecio(){
		
		for(int i = 0; i < this.numVertices; i++){

			for(int j = 0; j < this.numVertices; j++){
				
				if(i == j)
					listaMetodo.obtener(i).obtener(j).set_Data("0", 1);
				
				else {
					for(int k = 0; k < listanodos.obtener(i).getListasDePrecios().length(); k++){
						if(listanodos.obtener(i).getListasDePrecios().obtener(k).get_toID().equals(listanodos.obtener(j).getId()))
							listaMetodo.obtener(i).obtener(j).set_Data("" + listanodos.obtener(i).getListasDePrecios().obtener(k)._precio, 1);
					}
				}	
			}
		}
		System.out.println(listaMetodo.length());
	}
	
	public void calcularCaminoMasCorto(){
		//insertTabla();
		//insertPrecio();
		
		List<List<Array>> listatemp = listaMetodo;
		for(int i = 0; i < this.numVertices; i++){
			
			for(int j = 0; j < this.numVertices; j++){
				
				for(int k = 0; k < this.numVertices; k++){
					
					
					int X = Integer.parseInt(listatemp.obtener(i).obtener(k).get_Value(1));
					
					int W = Integer.parseInt(listatemp.obtener(i).obtener(j).get_Value(1));
					
					int Y = Integer.parseInt(listatemp.obtener(j).obtener(k).get_Value(1));
					
					if(k ==1)
						System.out.println("X " + X + " W " + W + " Y " + Y);
					if(W != -1 && Y != -1){
						if(X > W + Y || X == -1){
							X = W +Y;
							listatemp.obtener(i).obtener(k).set_Data("" + X, 1);
							listatemp.obtener(i).obtener(k).set_Data(listatemp.obtener(i).obtener(j).get_Value(0), 2);
						}
					}
					
				}
			}
		}
		
		this.listaMetodo2 = listatemp;
	}
	
	
	public Cola<NodoGrafo<String>> retornarCaminoMasCorto(String iD_From, String iD_to){
		calcularCaminoMasCorto();
		Cola<NodoGrafo<String>> colaCamino = new Cola<>();
		
		return colaCamino;
	}
	
	public static void main(String[] args){
		
		ReadProperties.getInstance();
		
		Grafo<NodoGrafo<String>> _grafo = new Grafo<>();
		//_grafo.nuevoDispositivo(TipoDeDispositivo.CLIENT,4488); //0
		_grafo.nuevoDispositivo(TipoDeDispositivo.CLIENT,4567); //1
		_grafo.nuevoDispositivo(TipoDeDispositivo.HUB,78765); //2
		_grafo.nuevoDispositivo(TipoDeDispositivo.CLIENT,4488); //3
		_grafo.nuevoDispositivo(TipoDeDispositivo.HUB,78765); //4
		_grafo.nuevoDispositivo(TipoDeDispositivo.CLIENT,4488); //5
		_grafo.nuevoDispositivo(TipoDeDispositivo.HUB,4488); //6
		_grafo.nuevoDispositivo(TipoDeDispositivo.CLIENT,4488); //7
		_grafo.nuevoDispositivo(TipoDeDispositivo.CLIENT,4488); //8
		_grafo.nuevoDispositivo(TipoDeDispositivo.CLIENT,4488); //9
		_grafo.arista("fabian8", "fabian1","192.168.1.106","192.168.1.106",4567,7);
		_grafo.arista("fabian1", "fabian0","192.168.1.106","192.168.1.106",78765,8);
		_grafo.arista("fabian1", "fabian2","192.168.1.106","192.168.1.106",4488,6);
		_grafo.arista("fabian1", "fabian3","192.168.1.106","192.168.1.106",78765,3);
		_grafo.arista("fabian3", "fabian4","192.168.1.106","192.168.1.106",4488,6);
		_grafo.arista("fabian3", "fabian1","192.168.1.106","192.168.1.106",4488,4);
		_grafo.arista("fabian0", "fabian5","192.168.1.106","192.168.1.106",4488,2);
		_grafo.arista("fabian5", "fabian6","192.168.1.106","192.168.1.106",4488,3);
		_grafo.arista("fabian7", "fabian5","192.168.1.106","192.168.1.106",4488,2);
		_grafo.arista("fabian5", "fabian2","192.168.1.106","192.168.1.106",4488,1);
		_grafo.arista("fabian0", "fabian2","192.168.1.106","192.168.1.106",4488,7);
		_grafo.arista("fabian2", "fabian5","192.168.1.106","192.168.1.106",4488,1);
		_grafo.arista("fabian7", "fabian6","192.168.1.106","192.168.1.106",4488,4);
		/*_grafo.arista("1234567810000000", "1234567810000001", 2);
		_grafo.arista("1234567810000001", "1234567810000002", 1);
		_grafo.arista("1234567810000001", "1234567810000003", 3);
		_grafo.arista("1234567810000002", "1234567810000003", 1);
		_grafo.arista("1234567810000003", "1234567810000004", 2);*/
		
		System.out.println(_grafo.getNodosGrafos().length() + "nodos g");
		FloydMethod<NodoGrafo<String>> floyd = new FloydMethod<>(_grafo.getNodosGrafos());
		floyd.insertTabla();
		floyd.insertPrecio();
		
		SimpleTable1 sp = new SimpleTable1(floyd.listaMetodo,"Sin calcular camino",
										   floyd.listanodos,"paes");
		sp.pack();
		sp.setVisible(true);
		
		floyd.calcularCaminoMasCorto();
		
		SimpleTable1 sp2 = new SimpleTable1(floyd.listaMetodo2, "Camino mas corto",
										    floyd.listanodos, "paes");
		sp2.pack();
		sp2.setVisible(true);
	}
	
}
