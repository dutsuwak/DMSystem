package logic.EstructurasDeDatosSimples;
import javax.swing.JTable; 
import javax.swing.JScrollPane; 
import javax.swing.JFrame; 
import javax.swing.JTable;

import java.awt.*; 
import java.awt.event.*;

import logic.Grafo.NodoGrafo;

public class SimpleTable1 extends JFrame {
	
	private List<List<Array>> listaMetodo;
	private String[] nombreColumnas;
	private int columnaSalida;
	private JTable table; 
	
	public SimpleTable1(List<List<Array>> listaMetodos, String Nombre,
						List<NodoGrafo<NodoGrafo<String>>> listanodos, String imprimir) {
		super(Nombre);
		
		this.columnaSalida = listaMetodos.length();
		this.listaMetodo = listaMetodos;
		
		this.nombreColumnas = new String [listaMetodo.length()+1];
		nombreColumnas[0] = "  ";
		for(int i = 1; i < listanodos.length()+1; i++)
			nombreColumnas[i] = listanodos.obtener(i-1).getId();
			
		
		//System.out.println(listaMetodo.length());
		//Array bidimensional de objetos con los datos de la tabla 
		Object[][] matriz = new Object[listaMetodo.length()][listaMetodo.length()+1];
		for (int j = 0; j < listaMetodo.length(); j++){
			matriz[j][0] = listanodos.obtener(j).getId();
			for(int k = 1; k < listaMetodo.length()+1; k++){
				System.out.println(listaMetodo.obtener(j).obtener(k-1).get_Value(1));
				if(imprimir.equals("padres")){
					
						matriz[j][k] = listaMetodo.obtener(j).obtener(k-1).get_Value(2);
				}
				else
					matriz[j][k] = listaMetodo.obtener(j).obtener(k-1).get_Value(1);
			}
		}
	
	
	
	
	//Array de String con los titulos de las columnas 
	
	
	
	//Creacion de la tabla 
	table = new JTable(matriz, nombreColumnas); 
	table.setPreferredScrollableViewportSize(new Dimension(1000, 100));
	
	//Creamos un scrollpanel y se lo agregamos a la tabla 
	JScrollPane scrollpane = new JScrollPane(table);
	
	//Agregamos el scrollpanel al contenedor 
	getContentPane().add(scrollpane, BorderLayout.CENTER);
	
	//manejamos la salida 
	addWindowListener(new WindowAdapter() {
	
	public void windowClosing(WindowEvent e) { 
	System.exit(0); 
	} 
	}); 
	}
	
	/*public static void main(String ar[]) { 
	SimpleTable1 frame = new SimpleTable1(); 
	frame.pack(); 
	frame.setVisible(true); 
	} */
}
