package logic.EstructurasDeDatosSimples;

import java.util.ArrayList;
import java.util.Arrays;

public class Array {
	public String[] arreglo=null;
	public int _tamano;
	protected int _tail=-1;
	protected int _count=0;
	protected int _head=-1;
	
	public Array(int ptamano)
	{	
		_tamano = ptamano;
		arreglo= new String [_tamano];
	}
	
	public void insertData(String pData)
	{
		if (_tail+1==_tamano)
		{
			
		}
		arreglo[++ _tail]=pData;
		_count++;
		
		if(_count==1)
			_head=0;	
	}
	
	public void showData(){
		for(int i = 0; i <_tamano; i ++){
			System.out.println(arreglo[i]);
		}
	}
	
	public void deleteData(int pIndice)
	{
		arreglo[pIndice]="";
		_count--;
	}
	
	public void swapDato(int pIndice1,int pIndice2)
	{
		String temp = arreglo[pIndice1];
	}


	public int get_length() {
		return _tamano;
	}

	public void set_tamano(int _tamano) {
		this._tamano = _tamano;
	}
	
	 public void set_Data(String _dato, int i){
		 arreglo[i] = _dato;
	}
	public String get_Value(int i){
		 return arreglo[i];
	}
	
	
}
